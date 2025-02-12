package org.users;

import org.dataloading.FileLoader;
import org.dataloading.ReportingAndDashboards;

import java.io.IOException;

public interface UserRole {

    void loadData(FileLoader loader, String path);
    void viewDataset(FileLoader loader);


    default void exportData(FileLoader loader){
        System.out.println("You are not eligble for this feature");
    }
    default void preprocessData(FileLoader loader) {
        System.out.println("you are not eligble for this feature");
    }
    default void train(String path) throws IOException {
        System.out.println("you are not eligble for this feature");
    }


}
