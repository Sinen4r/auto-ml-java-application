package org.users;
import org.dataloading.CSVPreprocessor;
import org.dataloading.FileLoader;
import org.dataloading.ReportingAndDashboards;


public class Customer extends User implements UserRole {
    private boolean canAccessReports;

    public Customer(int id, String name, String email, boolean canAccessReports){
        super(id, name,email);
        this.canAccessReports = canAccessReports;

    }

    public boolean isCanAccessReports() {
        return canAccessReports;
    }

    public void setCanAccessReports(boolean canAccessReports) {
        this.canAccessReports = canAccessReports;
    }

    @Override
    public void loadData(FileLoader loader, String path){
        loader.loadFile(path);
    }

    @Override
    public void viewDataset(FileLoader loader) {

        System.out.println("Customer " + getName() + " is viewing the preprocessed dataset:");
        loader.displayDataset();
    }


    @Override
    public void accessFeatures() {
        System.out.println("Customer " + getName() + " can access basic features.");
        if (canAccessReports) {
            System.out.println("Customer has access to reports.");
        } else {
            System.out.println("Customer does not have access to reports.");
        }
    }
}

