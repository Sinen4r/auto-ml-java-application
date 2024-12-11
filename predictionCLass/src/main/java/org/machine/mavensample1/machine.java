package org.machine.mavensample1;
 
import org.tribuo.*;
import org.tribuo.data.csv.CSVLoader;
import org.tribuo.datasource.ListDataSource;
import org.tribuo.evaluation.TrainTestSplitter;
import org.tribuo.math.optimisers.*;
import org.tribuo.regression.*;
import org.tribuo.regression.evaluation.*;
import org.tribuo.regression.sgd.RegressionObjective;
import org.tribuo.regression.sgd.linear.LinearSGDTrainer;
import org.tribuo.regression.sgd.objectives.SquaredLoss;
import org.tribuo.regression.rtree.CARTRegressionTrainer;
import org.tribuo.regression.xgboost.XGBoostRegressionTrainer;
import org.tribuo.util.Util;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;


public class machine{
   public static void main(String[] args) throws IOException {
	    String typePred="label";
	    String path="c:/salem";
	    switch (typePred) {
            case "multi_label":
            	multiClassification pred=new multiClassification(s);
            	pred.bestModel(path);
            	break;

            case "regression":
            	multiClassification predReg=new multiClassification(s);
            	predReg.bestModel(path);
            	break;

            case "binary_classification":
            	multiClassification predBin=new multiClassification(s);
            	predBin.bestModel(path);
            	break;

            default:
                System.out.println("Invalid task type!");
                break;
        }
    }
    	
 }    

   
  
