import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class test {
    public static void main(String[] args) {
        String filePath = "yourfile.csv";

        try (FileReader reader = new FileReader(filePath)) {
            List<MyBean> beans = new CsvToBeanBuilder<MyBean>(reader)
                    .withType(MyBean.class) // Replace MyBean with your actual bean class
                    .build()
                    .parse();

            // Print or process the beans
            for (MyBean bean : beans) {
                System.out.println(bean);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
