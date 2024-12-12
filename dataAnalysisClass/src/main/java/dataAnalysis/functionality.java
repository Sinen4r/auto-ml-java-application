package dataAnalysis;

import org.apache.commons.math3.stat.descriptive.moment.Kurtosis;
import org.apache.commons.math3.stat.descriptive.moment.Skewness;

public class functionality {
	public static boolean tryConvertToFloat(String value) {
	    try {
	        Float.parseFloat(value); // Attempt to parse the value as a float
	        return true; // Conversion succeeded
	    } catch (NumberFormatException e) {
	    	System.out.println(value);
	    	System.out.println("Falseeeeeeeeeee");
	        return false; // Conversion failed
	    }
	}
	 public static double[] convertStringArrayToDouble(String[] stringArray) {
	        double[] doubleArray = new double[stringArray.length];
	        
	        for (int i = 0; i < stringArray.length; i++) {
	            try {
	                doubleArray[i] = Double.parseDouble(stringArray[i]);
	            } catch (NumberFormatException e) {
	                // Handle invalid number format (e.g., set to NaN or 0)
	                doubleArray[i] = Double.NaN;  // You can also set to 0 or another value
	                System.out.println("Invalid number: " + stringArray[i]);
	            }
	        }
	        
	        return doubleArray;
	    }
	public static double calculateSkewness(double[] data) {
	    Skewness skewness = new Skewness();
	    return skewness.evaluate(data);
	}

	public static double calculateKurtosis(double[] data) {
	    Kurtosis kurtosis = new Kurtosis();
	    return kurtosis.evaluate(data);
	}
}
