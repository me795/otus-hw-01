package ru.dvsokolov.dataprocessor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.dvsokolov.model.Measurement;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ResourcesFileLoader implements ru.dvsokolov.dataprocessor.Loader {

    private final String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() throws IOException {

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classloader.getResourceAsStream(fileName);
        assert inputStream != null;

        try {
            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append('\n');
            }

            String fileContent = stringBuilder.toString();
            Gson gson = new Gson();
            Type measurementListType = new TypeToken<List<Measurement>>() {}.getType();
            return gson.fromJson(fileContent, measurementListType);

        } finally {
            inputStream.close();
        }


    }
}
