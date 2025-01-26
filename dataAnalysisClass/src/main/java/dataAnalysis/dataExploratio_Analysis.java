package dataAnalysis;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.moment.Kurtosis;
import org.apache.commons.math3.stat.descriptive.moment.Skewness;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import smile.data.DataFrame;
import smile.data.vector.DoubleVector;
import smile.io.Read;

import java.io.FileReader;
import java.io.Reader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class dataExploratio_Analysis {


public static String DataExplo(String Path,String analysisType) throws IOException, URISyntaxException  {
    String filePath = Path;
    List<String> colummns = new ArrayList<>();

    DataFrame df = Read.csv(filePath);
    for (String column : df.schema().names()) {
    	colummns.add((String) df.column(column).get(0));}
    System.out.println(colummns.get(1));
    String[] columnNames=df.schema().names();
    df.column(columnNames[0]);
    if (analysisType=="correlation") {
    	return ("correlation of column"+columnNames[0]+" " +columnNames[1]+"is equal to : "+analysis.calculateCorrelation(df, columnNames[0], columnNames[1]));
    }else {
	    return ("Time Series of column"+columnNames[0]+" " +columnNames[1]+"is equal to : "+analysis.calculateMovingAverage(df, columnNames[1], 4));
}}}

