package org.machine.mavensample1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
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
	    public static String[] getCSVHeaders(String filePath) {
	        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
	            // Read the first line (header)
	            String headerLine = br.readLine();
	            if (headerLine != null) {
	                // Split the header by comma and return as String[]
	                return headerLine.split(",");
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return null; // Return null if the file is empty or an error occurs
	    }
	   public abstract List<String> evaluate(Model <MultiLabel> model, MutableDataset  <MultiLabel> testDataa);
	   public abstract Model<MultiLabel> train(MutableDataset <MultiLabel> trainingDataset);
	   public abstract List<String> bestModel(String s,String labbel) throws IOException;
	   
}
