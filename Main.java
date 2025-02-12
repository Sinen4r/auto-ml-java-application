package org.users;

import org.dataloading.FileLoader;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        FileLoader loader = new FileLoader();
        Customer hs = new Customer(1,"houssem","hs@example.com", true);

        Manager mg = new Manager(1,"hassen","h@example.com",true);
        hs.loadData(loader, "data.csv");
        hs.viewDataset(loader);
        hs.train("data.csv");


    }
}