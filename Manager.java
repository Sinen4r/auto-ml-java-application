package org.users;
import org.dataloading.CSVPreprocessor;
import org.dataloading.FileLoader;
import org.machine.mavensample1.machine;
import org.machine.mavensample1.multiClassification;
import org.dataloading.ReportingAndDashboards;

import java.io.File;
import java.io.IOException;

public class Manager extends User implements UserRole{

    private boolean canModifyFeatures;

    multiClassification classification = new multiClassification();

    ReportingAndDashboards report = new ReportingAndDashboards();


    public Manager(int id, String name, String email, boolean canModifyFeatures){
        super(id,name,email);
        this.canModifyFeatures= canModifyFeatures;

    }

    public boolean getCanModifyFeatures() {
        return canModifyFeatures;
    }

    public void setCanModifyFeatures(boolean canModifyFeatures) {
        this.canModifyFeatures = canModifyFeatures;
    }

    @Override
    public void loadData(FileLoader loader, String path){
        loader.loadFile(path);
    }

    @Override
    public void train(String path) throws IOException {
        System.out.println("can train model");
        classification.bestModel(path);
    }

    @Override
    public void viewDataset(FileLoader loader) {

        System.out.println("Manager " + getName() + " is viewing the dataset:");
        loader.displayDataset();
    }


    @Override
    public void preprocessData(FileLoader loader){
        loader.normalizeColumn(3);
        loader.oneHotEncodeColumn(2);
        loader.handleMissingValues("NaN", 0);
    }

    @Override
    public void exportData(FileLoader loader){
        loader.exportToCSV("newdata.csv");
    }
    @Override
    public void accessFeatures() {
        System.out.println("Manager " + getName() + " has admin access to all features.");
        if (canModifyFeatures) {
            System.out.println("Manager can modify features.");
        } else {
            System.out.println("Manager cannot modify features.");
        }
    }
}
