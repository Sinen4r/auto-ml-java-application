package org.machine.mavensample1;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.tribuo.Model;
import org.tribuo.MutableDataset;
import org.tribuo.Trainer;
import org.tribuo.classification.Label;
import org.tribuo.classification.LabelFactory;
import org.tribuo.classification.evaluation.LabelEvaluator;
import org.tribuo.classification.sgd.linear.LogisticRegressionTrainer;
import org.tribuo.data.csv.CSVLoader;
import org.tribuo.evaluation.TrainTestSplitter;
public class classificatio {
	public List<String> bestModel(String s ,String typePred) throws IOException {
		var labelFactory = new LabelFactory();
		var csvLoader = new CSVLoader<>(labelFactory);
        String[] Headers = prediction.getCSVHeaders(s);
		var dataSource = csvLoader.loadDataSource(Paths.get(s),typePred,Headers);
		var dataSplitter = new TrainTestSplitter<>(dataSource,0.7,1L);
		var trainingDataset = new MutableDataset<>(dataSplitter.getTrain());
		var testingDataset = new MutableDataset<>(dataSplitter.getTest());
		System.out.println(String.format("Training data size = %d, number of features = %d, number of classes = %d",trainingDataset.size(),trainingDataset.getFeatureMap().size(),trainingDataset.getOutputInfo().size()));
		System.out.println(String.format("Testing data size = %d, number of features = %d, number of classes = %d",testingDataset.size(),testingDataset.getFeatureMap().size(),testingDataset.getOutputInfo().size()));
		var model=train(trainingDataset);
		return evaluate(model,trainingDataset);
		

	}
	public Model<Label> train(MutableDataset <Label> trainingDataset) {
		Trainer<Label> trainer = new LogisticRegressionTrainer();
		System.out.println(trainer.toString());
		Model<Label> model = trainer.train(trainingDataset);
		return model;
	}
	
	public List<String> evaluate(Model <Label> model, MutableDataset <Label> testData) {
	    List<String> result = new ArrayList<>();
		var evaluator = new LabelEvaluator();
		var evaluation = evaluator.evaluate(model,testData);
		result.add(evaluation.toString());
		return result;
	}

}
