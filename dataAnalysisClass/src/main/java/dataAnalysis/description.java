package dataAnalysis;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import smile.data.DataFrame;
import smile.io.Read;

public class description extends functionality{
	public Map<String, String> detectDataTypes(List<Map<String, String>> data) {
	    Map<String, String> dataTypes = new HashMap<>();
	    for (String column : data.get(0).keySet()) {
	        boolean isNumeric = true;
	        for (Map<String, String> row : data) {
	            String value = row.get(column);
	            if (value != null && !value.isEmpty()) {
	                try {
	                    Double.parseDouble(value);
	                } catch (NumberFormatException e) {
	                    isNumeric = false;
	                    break;
	                }
	            }
	        }
	        dataTypes.put(column, isNumeric ? "Numeric" : "Categorical/String");
	    }
	    return dataTypes;
	}
	public void describe(String filePath) throws IOException {
	    Reader in = new FileReader(filePath);
	    Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader().parse(in);
	 
	    DescriptiveStatistics stats = new DescriptiveStatistics();
	    for (CSVRecord record : records) {
	    	try {    
	    		double value = Double.parseDouble(record.get("radio"));

	        	stats.addValue(value);
	    	} catch (NumberFormatException e) {
	    		System.err.println("Invalid number format in record: " + record.get("radio"));
	    }
	    }

	    // Print descriptive statistics
	    System.out.println("Mean: " + stats.getMean());
	    System.out.println("Standard Deviation: " + stats.getStandardDeviation());
	    System.out.println("Min: " + stats.getMin());
	    System.out.println("Max: " + stats.getMax());
	}
	public static void kurtiosAndSkewness(DataFrame df) throws IOException, URISyntaxException {


	    for (String column : df.schema().names()) {
	        // Skip non-numeric columns (if any)
	        if (tryConvertToFloat((String) df.column(column).get(1)) ) {
	            // Extract the column as a double array
	            String[] data = df.column(column).toStringArray();
	            String[] filteredData = Arrays.copyOfRange(data, 1, data.length);

	            double[] dataframe = convertStringArrayToDouble(filteredData);
	            // Calculate skewness and kurtosis
	            double skewness = calculateSkewness(dataframe);
	            double kurtosis = calculateKurtosis(dataframe);

	            // Print results for each column
	            System.out.println("Column: " + df.column(column).get(0));
	            System.out.println("  Skewness: " + skewness);
	            System.out.println("  Kurtosis: " + kurtosis);
	        } else {
	            System.out.println("Skipping non-numeric column: " + column);
	        }
	}
}}
