package org.dataloading;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import com.opencsv.CSVWriter;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

public class FileLoader {

    private String[] headers;
    private List<String[]> data = new ArrayList<>();

    public void loadFile(String filePath) {
        String fileExtension = getFileExtension(filePath);
        switch (fileExtension) {
            case "csv":
                loadCSV(filePath);
                break;
            case "json":
                loadJSON(filePath);
                break;
            default:
                System.err.println("Unsupported file type: " + fileExtension);
        }
    }


    private String getFileExtension(String filePath) {
        int lastDotIndex = filePath.lastIndexOf(".");
        return (lastDotIndex > 0) ? filePath.substring(lastDotIndex + 1).toLowerCase() : "";
    }


    private void loadCSV(String filePath) {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> allRows = reader.readAll();
            headers = allRows.get(0);
            data.addAll(allRows.subList(1, allRows.size()));
            System.out.println("CSV file loaded successfully!");
        } catch (IOException | com.opencsv.exceptions.CsvException e) {
            System.err.println("Error loading CSV file: " + e.getMessage());
        }
    }

    private void loadJSON(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Map<String, Object>> jsonData = objectMapper.readValue(
                    new File(filePath), new TypeReference<List<Map<String, Object>>>() {});

            if (!jsonData.isEmpty()) {
                headers = jsonData.get(0).keySet().toArray(new String[0]);
            }

            for (Map<String, Object> row : jsonData) {
                String[] rowData = new String[headers.length];
                for (int i = 0; i < headers.length; i++) {
                    rowData[i] = row.get(headers[i]) != null ? row.get(headers[i]).toString() : "";
                }
                data.add(rowData);
            }

            System.out.println("JSON file loaded successfully!");
        } catch (IOException e) {
            System.err.println("Error loading JSON file: " + e.getMessage());
        }
    }
    public void displayDataset() {
        System.out.println(String.join("\t", headers)); // Print headers
        for (String[] row : data) {
            System.out.println(String.join("\t", row));
        }
    }


    public void handleMissingValues(String defaultText, double defaultNumber) {
        for (int i = 0; i < data.size(); i++) {
            String[] row = data.get(i);

            if (row.length == 0 || Arrays.stream(row).allMatch(String::isEmpty)) {
                continue;
            }

            if (row.length < headers.length) {
                System.err.println("Skipping incomplete row: " + Arrays.toString(row));
                continue;
            }

            for (int j = 0; j < row.length; j++) {
                if (row[j] == null || row[j].trim().isEmpty()) {
                    if (isNumericColumn(j)) {
                        row[j] = String.valueOf(defaultNumber);
                    } else {
                        row[j] = defaultText;
                    }
                }
            }
        }
        System.out.println("Missing values handled.");
    }

    private boolean isNumericColumn(int columnIndex) {
        for (String[] row : data) {
            if (row.length <= columnIndex) {
                continue;
            }

            try {
                if (row[columnIndex] != null && !row[columnIndex].trim().isEmpty()) {
                    Double.parseDouble(row[columnIndex]);
                    return true;
                }
            } catch (NumberFormatException ignored) {
            }
        }
        return false;
    }

    public void normalizeColumn(int columnIndex) {
        try {
            double min = Double.MAX_VALUE;
            double max = Double.MIN_VALUE;

            for (String[] row : data) {
                if (row.length <= columnIndex) {
                    continue;
                }
                try {
                    double value = Double.parseDouble(row[columnIndex]);
                    if (value < min) min = value;
                    if (value > max) max = value;
                } catch (NumberFormatException ignored) {
                }
            }

            for (String[] row : data) {
                if (row.length <= columnIndex) {
                    continue;
                }
                try {
                    double value = Double.parseDouble(row[columnIndex]);
                    row[columnIndex] = String.valueOf((value - min) / (max - min));
                } catch (NumberFormatException ignored) {
                }
            }
            System.out.println("Column " + columnIndex + " normalized.");
        } catch (Exception e) {
            System.err.println("Error normalizing column " + columnIndex + ": " + e.getMessage());
        }
    }

    public void oneHotEncodeColumn(int columnIndex) {
        Set<String> uniqueCategories = new LinkedHashSet<>();
        for (String[] row : data) {
            if (row.length > columnIndex) {
                uniqueCategories.add(row[columnIndex]);
            }
        }

        List<String> newHeaders = new ArrayList<>(Arrays.asList(headers));
        newHeaders.remove(columnIndex);
        for (String category : uniqueCategories) {
            newHeaders.add(headers[columnIndex] + "_" + category);
        }
        headers = newHeaders.toArray(new String[0]);

        List<String[]> newData = new ArrayList<>();
        for (String[] row : data) {
            if (row.length > columnIndex) {
                List<String> newRow = new ArrayList<>(Arrays.asList(row));
                String categoryValue = row[columnIndex];

                newRow.remove(columnIndex);

                for (String category : uniqueCategories) {
                    newRow.add(category.equals(categoryValue) ? "1" : "0");
                }
                newData.add(newRow.toArray(new String[0]));
            }
        }

        data = newData;
        System.out.println("One-hot encoding completed for column " + columnIndex + ".");
    }

    public void exportToCSV(String outputFilePath) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(outputFilePath))) {
            writer.writeNext(headers);

            for (String[] row : data) {
                if (row != null && row.length > 0) {
                    writer.writeNext(row);
                }
            }

            System.out.println("Dataset exported successfully to: " + outputFilePath);
        } catch (IOException e) {
            System.err.println("Error exporting dataset: " + e.getMessage());
        }
    }


}
