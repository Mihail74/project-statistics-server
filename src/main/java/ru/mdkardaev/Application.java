package ru.mdkardaev;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class Application {

    public static void main(String[] args) throws JsonProcessingException {
        SpringApplication.run(Application.class, args);
    }
}
