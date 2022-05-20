package com.drozd.financemanager.app;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FinanceManagerApplication {
    public static void main(String[] args) {
        Application.launch(JFXFinanceManagerApplication.class, args);
    }
}
