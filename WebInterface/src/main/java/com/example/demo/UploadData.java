package com.example.demo;

public class UploadData {
    private String name;
    private String target;
    private String analysis;
    private String filePath;
    private String task;

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getTask() { return task; }
    public void setTask(String task) { this.task = task; }
    public String getTarget() { return target; }
    public void setTarget(String target) { this.target = target; }
    public String getAnalysis() { return analysis; }
    public void setAnalysis(String analysis) { this.analysis = analysis; }
    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
}