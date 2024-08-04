package ru.netology;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "data.csv";

        try {
            List<Employee> list = parseCSV(columnMapping, fileName);
            String json = listToJson(list);
            writeString(json, "data.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Employee> parseCSV(String[] columnMapping, String fileName) throws IOException {
        FileReader fileReader = new FileReader(fileName);
        ColumnPositionMappingStrategy<Employee> strategy = new ColumnPositionMappingStrategy<>();
        strategy.setType(Employee.class);
        strategy.setColumnMapping(columnMapping);

        CsvToBean<Employee> csv = new CsvToBeanBuilder<Employee>(fileReader)
                .withMappingStrategy(strategy)
                .build();

        return csv.parse();
    }

    public static String listToJson(List<Employee> list) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.setPrettyPrinting().create();
        Type listType = new TypeToken<List<Employee>>() {}.getType();
        return gson.toJson(list, listType);
    }

    public static void writeString(String jsonString, String fileName) throws IOException {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(jsonString);
        }
    }
}