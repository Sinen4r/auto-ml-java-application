package org.machine.mavensample1;
import org.tribuo.*;
import org.tribuo.evaluation.TrainTestSplitter;
import org.tribuo.data.csv.CSVLoader;
import org.tribuo.classification.*;
import org.tribuo.classification.evaluation.*;
import org.tribuo.classification.sgd.linear.LogisticRegressionTrainer;

import org.tribuo.*;
import org.tribuo.classification.Label;
import org.tribuo.classification.dtree.CARTClassificationTrainer;
import org.tribuo.classification.dtree.impurity.*;
import org.tribuo.datasource.*;
import org.tribuo.math.optimisers.*;
import org.tribuo.multilabel.*;
import org.tribuo.multilabel.baseline.*;
import org.tribuo.multilabel.ensemble.*;
import org.tribuo.multilabel.evaluation.*;
import org.tribuo.multilabel.sgd.linear.*;
import org.tribuo.multilabel.sgd.objectives.*;
import org.tribuo.regression.Regressor;
import org.tribuo.regression.evaluation.RegressionEvaluator;
import org.tribuo.util.Util;

import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Files;

import com.fasterxml.jackson.databind.*;
import com.oracle.labs.mlrg.olcut.provenance.ProvenanceUtil;
import com.oracle.labs.mlrg.olcut.config.json.*;

public class multiClassification extends prediction{

public void bestModel(String s) throws IOException {
	var factory = new MultiLabelFactory();
	var trainSource = new LibSVMDataSource<>(Paths.get(s),factory);
	var irisSplitter = new TrainTestSplitter<>(trainSource,0.7,1L);
	var trainingDataset = new MutableDataset<>(irisSplitter.getTrain());
	var testingDataset = new MutableDataset<>(irisSplitter.getTest());

	Model <MultiLabel> dtModel=train(trainingDataset);
	evaluate(dtModel,testingDataset);
}
@Override
public Model<MultiLabel> train(MutableDataset <MultiLabel> trainingDataset) {
	Trainer<Label> treeTrainer = new CARTClassificationTrainer(6,10,0.0f,1.0f,false,new Entropy(),1L);
	Trainer<MultiLabel> dtTrainer = new IndependentMultiLabelTrainer(treeTrainer);
	var dtStartTime = System.currentTimeMillis();
	var dtModel = dtTrainer.train(trainingDataset);
	var dtEndTime = System.currentTimeMillis();
	System.out.println();
	System.out.println("Tree model training took " + Util.formatDuration(dtStartTime,dtEndTime));
	return dtModel;
}
@Override
public void evaluate(Model <MultiLabel> model, MutableDataset  <MultiLabel> testData) {
    // Evaluate the model on the test data
	var  eval = new MultiLabelEvaluator();
	var linTStartTime = System.currentTimeMillis();
    var evaluation = eval.evaluate(model,testData);	
    var linTEndTime = System.currentTimeMillis();

    // We create a dimension here to aid pulling out the appropriate statistics.
    // You can also produce the String directly by calling "evaluation.toString()"
    System.out.println();
    System.out.println("Linear model evaluation took " + Util.formatDuration(linTStartTime,linTEndTime));
    System.out.println(evaluation);
}


}
