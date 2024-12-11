package org.machine.mavensample1;

import java.io.IOException;
import java.util.List;

import org.tribuo.Model;
import org.tribuo.MutableDataset;
import org.tribuo.multilabel.MultiLabel;


public abstract class prediction {
	   protected static double getMaxIndex(List<Double> list) {
	       if (list == null || list.isEmpty()) {
	           throw new IllegalArgumentException("List cannot be null or empty");
	       }

	       double maxIndex = 0; // Initialize maxIndex to the first index
	       double maxValue = list.get(0); // Initialize maxValue to the first element

	       for (int i = 1; i < list.size(); i++) { // Start from the second element
	           if (list.get(i) > maxValue) {
	               maxValue = list.get(i);
	               maxIndex = i;
	           }
	       }

	       return maxIndex;
	   }
	   public abstract void evaluate(Model <MultiLabel> model, MutableDataset  <MultiLabel> testDataa);
	   public abstract Model<MultiLabel> train(MutableDataset <MultiLabel> trainingDataset);
	   public abstract void bestModel(String s) throws IOException;
	   
}
