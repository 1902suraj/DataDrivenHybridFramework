package utils;

import org.testng.annotations.DataProvider;
import java.util.*;

public class GenericDataProvider {
    @DataProvider(name = "hybridDataProvider")
    public static Object[][] hybridDataProvider() throws Exception {
        // Example: Read Excel, CSV, or JSON based on config or test
        // Here, Excel is used as a sample
        List<Map<String, String>> data = DataProviderUtil.readExcel("src/test/resources/testdata/sample.xlsx", "Sheet1");
        Object[][] result = new Object[data.size()][1];
        for (int i = 0; i < data.size(); i++) {
            result[i][0] = data.get(i);
        }
        return result;
    }
}
