package ru.dvsokolov.server;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Optional;

public class RequestBodyReader<T> {

    private final Class<T> entityClass;
    private final HttpServletRequest request;
    private static final Logger log = LoggerFactory.getLogger(RequestBodyReader.class);


    public RequestBodyReader(HttpServletRequest request, Class<T> entityClass) {
        this.request = request;
        this.entityClass = entityClass;
    }

    public T getObject()  {

        T entityFromRequest;

        try {

            BufferedReader br = request.getReader();
            String line;
            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {

                sb.append(line);
                sb.append(System.lineSeparator());
            }

            Gson g = new Gson();
            entityFromRequest = g.fromJson(String.valueOf(sb), entityClass);

        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException("Error reading request body");
        }
        return entityFromRequest;
    }
}
