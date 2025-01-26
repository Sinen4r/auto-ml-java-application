package dataAnalysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

import smile.data.DataFrame;

public class analysis extends functionality{
    // Method to calculate correlation between two columns of a DataFrame

	public static double calculateCorrelation(DataFrame df, String column1, String column2) {
		
		String[] data = df.column(column1).toStringArray();
        String[] filteredData = Arrays.copyOfRange(data, 1, data.length);
        double[] data1 = convertStringArrayToDouble(filteredData);
        
        String[] dat = df.column(column2).toStringArray();
        String[] filteredDat = Arrays.copyOfRange(dat, 1, dat.length);
        double[] data2 = convertStringArrayToDouble(filteredDat);
		//double[] data1 = df.column(column1).toDoubleArray();
        //double[] data2 = df.column(column2).toDoubleArray();
        
        PearsonsCorrelation correlation = new PearsonsCorrelation();
        return correlation.correlation(data1, data2);
    }
    // TimeSeries Analysis

	public static List<Double> calculateMovingAverage(DataFrame df, String column, int windowSize) {
        //List<Double> data = new ArrayList<>();
        
        //double[] columnData = df.column(column).toDoubleArray();
		String[] data = df.column(column).toStringArray();
        String[] filteredData = Arrays.copyOfRange(data, 1, data.length);
        double[] data1 = convertStringArrayToDouble(filteredData);
    
        
        List<Double> movingAverages = new ArrayList<>();
        
        for (int i = 0; i <= data1.length - windowSize; i++) {
            double sum = 0.0;
            for (int j = i; j < i + windowSize; j++) {
                sum += data1[j];
            }
            movingAverages.add(sum / windowSize);
        }
        
        return movingAverages;
    }

}
