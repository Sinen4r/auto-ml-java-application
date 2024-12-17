package org.sample.EndOfSemProject;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.io.FileReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class ReportingAndDashboards {

    public static void main(String[] args) {
        String csvFilePath = "C:\\Users\\hasse\\Downloads\\Train.csv";
        visualizeData(csvFilePath);
    }

    
	public static void visualizeData(String csvFilePath) {
        try {
            // Read the CSV file
            Reader in = new FileReader(csvFilePath);
			Iterable<CSVRecord> records = CSVFormat.DEFAULT.withHeader().parse(in);
            

            // Data structures for analysis
            Map<String, Integer> flightStatusCount = new HashMap<>();
            Map<String, Integer> routeCount = new HashMap<>();
            Map<String, Double> delayByAircraftType = new HashMap<>();
            Map<Integer, Double> delayByHour = new HashMap<>();
            Map<Integer, Integer> flightCountByHour = new HashMap<>();

            // Process each record
            for (CSVRecord record : records) {
                String status = record.get("STATUS");
                String route = record.get("DEPSTN") + " to " + record.get("ARRSTN");
                String aircraftType = record.get("AC"); // Aircraft Code
                String delayStr = record.get("target"); // Delay in minutes
                String std = record.get("STD"); // Scheduled Time Departure
                int hour = Integer.parseInt(std.split(" ")[1].split(":")[0]); // Extract hour from STD
                double delay = delayStr.isEmpty() ? 0 : Double.parseDouble(delayStr);

                // Count flight statuses
                flightStatusCount.put(status, flightStatusCount.getOrDefault(status, 0) + 1);

                // Count routes
                routeCount.put(route, routeCount.getOrDefault(route, 0) + 1);

                // Average delay by aircraft type
                delayByAircraftType.put(aircraftType, delayByAircraftType.getOrDefault(aircraftType, 0.0) + delay);

                // Delay by hour
                delayByHour.put(hour, delayByHour.getOrDefault(hour, 0.0) + delay);
                flightCountByHour.put(hour, flightCountByHour.getOrDefault(hour, 0) + 1);
            }

            // Create visualizations
            createPieChart(flightStatusCount);
            createBarChart(routeCount, "Top 10 Most Common Routes", "Route", "Count");
            createAverageDelayBarChart(delayByAircraftType);
            createScatterPlot(delayByHour, flightCountByHour);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createPieChart(Map<String, Integer> flightStatusCount) {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>(); 
        flightStatusCount.forEach((status, count) -> dataset.setValue(status, count));

        JFreeChart pieChart = ChartFactory.createPieChart(
                "Flight Status Distribution",
                dataset,
                true,
                true,
                false
        );

        displayChart(pieChart);
    }

    private static void createBarChart(Map<String, Integer> routeCount, String title, String xLabel, String yLabel) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        routeCount.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue())) 
                .limit(10) 
                .forEach(entry -> dataset.addValue(entry.getValue(), "Routes", entry.getKey()));

        JFreeChart barChart = ChartFactory.createBarChart(
                title,
                xLabel,
                yLabel,
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        displayChart(barChart);
    }

    private static void createAverageDelayBarChart(Map<String, Double> delayByAircraftType) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        // Calculate average delay for each aircraft type
        for (Map.Entry<String, Double> entry : delayByAircraftType.entrySet()) {
            String aircraftType = entry.getKey();
            double totalDelay = entry.getValue();
            // Assuming you have a way to count the number of flights per aircraft type
            int flightCount = 1; // This should be replaced with the actual count of flights for that aircraft type
            double averageDelay = totalDelay / flightCount; // Calculate average delay

            dataset.addValue(averageDelay, "Average Delay", aircraftType);
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "Average Delay by Aircraft Type",
                "Aircraft Type",
                "Average Delay (minutes)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        displayChart(barChart);
    }
    
    private static void createScatterPlot(Map<Integer, Double> delayByHour, Map<Integer, Integer> flightCountByHour) {
        XYSeries series = new XYSeries("Delay vs. Flights");

        // Create a scatter plot data series
        for (Integer hour : delayByHour.keySet()) {
            double totalDelay = delayByHour.get(hour);
            int flightCount = flightCountByHour.get(hour);
            double averageDelay = flightCount > 0 ? totalDelay / flightCount : 0; // Calculate average delay per hour

            // Add (hour, average delay) to the series with explicit casting
            series.add((Number) hour, (Number) averageDelay); // Cast to Number
        }

        XYDataset dataset = new XYSeriesCollection(series);
        JFreeChart scatterPlot = ChartFactory.createScatterPlot(
                "Average Delay by Hour of Day",
                "Hour of Day",
                "Average Delay (minutes)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        displayChart(scatterPlot);
    }
    
    
    private static void displayChart(JFreeChart chart) {

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        
       
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setTitle(chart.getTitle().getText());
        frame.add(chartPanel);
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setVisible(true);
    }
}