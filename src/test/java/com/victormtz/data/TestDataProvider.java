package com.victormtz.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.DataProvider;
import java.io.File;
import java.util.List;
import java.util.Map;

public class TestDataProvider {

    private static final String DATA_PATH = "src/test/resources/data/users.json";

    @DataProvider(name = "loginData")
    public static Object[][] getLoginData() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, String>> users = mapper.readValue(
                new File(DATA_PATH),
                mapper.getTypeFactory().constructCollectionType(List.class, Map.class)
        );

        Object[][] data = new Object[users.size()][1];
        for (int i = 0; i < users.size(); i++) {
            data[i][0] = users.get(i);
        }
        return data;
    }
}