package org.machine.mavensample1;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.tribuo.Dataset;
import org.tribuo.Model;
import org.tribuo.MutableDataset;
import org.tribuo.Trainer;
import org.tribuo.data.csv.CSVLoader;
import org.tribuo.evaluation.TrainTestSplitter;
import org.tribuo.math.optimisers.AdaGrad;
import org.tribuo.math.optimisers.SGD;
import org.tribuo.regression.RegressionFactory;
import org.tribuo.regression.Regressor;
import org.tribuo.regression.evaluation.RegressionEvaluator;
import org.tribuo.regression.rtree.CARTRegressionTrainer;
import org.tribuo.regression.sgd.linear.LinearSGDTrainer;
import org.tribuo.regression.sgd.objectives.SquaredLoss;
import org.tribuo.regression.xgboost.XGBoostRegressionTrainer;
import org.tribuo.util.Util;

public class regression{
	
	   public static Model<Regressor> train(String name, Trainer<Regressor> trainer, Dataset<Regressor> trainData) {
		    // Train the model
		    var startTime = System.currentTimeMillis();
		    Model<Regressor> model = trainer.train(trainData);
		    var endTime = System.currentTimeMillis();
		    System.out.println("Training " + name + " took " + Util.formatDuration(startTime,endTime));
		    // Evaluate the model on the training data
		    // This is a useful debugging tool to check the model actually learned something
		    RegressionEvaluator eval = new RegressionEvaluator();
		    var evaluation = eval.evaluate(model,trainData);
		    // We create a dimension here to aid pulling out the appropriate statistics.
		    // You can also produce the String directly by calling "evaluation.toString()"
		    var dimension = new Regressor("DIM-0",Double.NaN);
		    System.out.printf("Evaluation (train):%n  RMSE %f%n  MAE %f%n  R^2 %f%n",
		            evaluation.rmse(dimension), evaluation.mae(dimension), evaluation.r2(dimension));
		    return model;
		}
	   public static double evaluate(Model<Regressor> model, Dataset<Regressor> testData) {
		    // Evaluate the model on the test data
		    RegressionEvaluator eval = new RegressionEvaluator();
		    var evaluation = eval.evaluate(model,testData);
		    // We create a dimension here to aid pulling out the appropriate statistics.
		    // You can also produce the String directly by calling "evaluation.toString()"
		    var dimension = new Regressor("DIM-0",Double.NaN);
		    System.out.printf("Evaluation (test):%n  RMSE "
		    		+ "%n  MAE %f%n  R^2 %f%n",
		            evaluation.rmse(dimension), evaluation.mae(dimension), evaluation.r2(dimension));
		    return evaluation.r2(dimension);
		}

	   public List<String> bestModel(String s,String typePred) throws IOException {
		    
		   var regressionFactory = new RegressionFactory();
	       System.out.println(Paths.get(s));
	       var csvLoader = new CSVLoader<>(regressionFactory);
	   	   var wineSource = csvLoader.loadDataSource(Paths.get(s),typePred);
	       var splitter = new TrainTestSplitter<>(wineSource, 0.7f, 0L);
	       Dataset<Regressor> trainData = new MutableDataset<>(splitter.getTrain());
	       Dataset<Regressor> evalData = new MutableDataset<>(splitter.getTest());
	       List<Double> r2 = new ArrayList<>();
	       String[] models={"linear regression trained using linear decay SGD"," linear regression trained using SGD and AdaGrad","regression tree using the CART algorithm ","XGBoost trainer"};
		   var lrsgd = new LinearSGDTrainer(
	   		    new SquaredLoss(), // loss function
	   		    SGD.getLinearDecaySGD(0.01), // gradient descent algorithm
	   		    10,                // number of training epochs
	   		    trainData.size()/4,// logging interval
	   		    1,                 // minibatch size
	   		    1L                 // RNG seed
	   		);
		   var lrada = new LinearSGDTrainer(
	    		    new SquaredLoss(),
	    		    new AdaGrad(0.01),
	    		    10,
	    		    trainData.size()/4,
	    		    1,
	    		    1L 
	    		);
	    		var cart = new CARTRegressionTrainer(6);
	    		var xgb = new XGBoostRegressionTrainer(50);
	    		var lrsgdModel = train("Linear Regression (SGD)",lrsgd,trainData);
	    		r2.add(evaluate(lrsgdModel,evalData));
	    		//adaGrad
	    		var lradaModel = train("Linear Regression (AdaGrad)",lrada,trainData);
	    		r2.add(evaluate(lradaModel,evalData));
	    		//Trees
	    		var cartModel = train("CART",cart,trainData);
	    		r2.add(evaluate(cartModel,evalData));
	    		//regressor
	    		var xgbModel = train("XGBoost",xgb,trainData);
	    		r2.add(evaluate(xgbModel,evalData));
	            int maxIndex = (int) prediction.getMaxIndex(r2);
	            List<String> result = new ArrayList<>();
	            result.add("Best Model that fit this data is"+models[maxIndex]+"with R2 ="+r2.get(maxIndex))
;
	            

	    		return result;
	   }
}
