package com.drozd.analyzer.app;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import pl.edu.pwr.tkubik.ex.api.AnalysisException;
import pl.edu.pwr.tkubik.ex.api.AnalysisService;
import pl.edu.pwr.tkubik.ex.api.ClusteringException;
import pl.edu.pwr.tkubik.ex.api.DataSet;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class Main {
    private static Map<String, AnalysisService> services = new HashMap<>();
    private static AnalysisService chosenService = null;
    private static Path file = null;
    private static DataSet dataSet = null;

    public static void main(String[] args) {
        ServiceLoader<AnalysisService> serviceLoader = ServiceLoader.load(AnalysisService.class);
        for (AnalysisService service : serviceLoader) services.put(service.getName(), service);
        boolean run = true;
        showMenu();
        while (run) {
            switch (getChoice(0, 6)) {
                case 0:
                    showMenu();
                    break;
                case 1:
                    chooseFile();
                    break;
                case 2:
                    if (dataSet != null && dataSet.getHeader() != null && dataSet.getData() != null) {
                        dataSet.print();
                    }
                    break;
                case 3:
                    chooseService();
                    break;
                case 4:
                    if (chosenService != null && dataSet != null && dataSet.getHeader() != null && dataSet.getData() != null) {
                        try {
                            chosenService.submit(dataSet);
                        } catch (AnalysisException e) {
                            pt("Obecnie jest przetwarzane inne zadanie.");
                            //e.printStackTrace();
                        }
                    }
                    break;
                case 5:
                    if (chosenService != null) {
                        try {
                            DataSet result = chosenService.retrieve(false);
                            if (result == null) {
                                pt("Przetwarzanie jeszcze trwa, albo nie przekazano danych do przetwarzania.");
                                break;
                            } else {
                                pt("Wynik przetwarzania: ");
                                result.print();
                            }
                        } catch (ClusteringException e) {
                            pt("Podczas przetwarzania wystąpił błąd.");
                            e.printStackTrace();
                        }
                    }
                    break;
                case 6:
                    run = false;
                    break;
                default:
                    showMenu();
            }
        }
    }

    private static void showMenu() {
        pt("========= MENU =========" +
                "\n0. Wyświetl menu." +
                "\n1. Wybierz plik (*.csv)");
        if (file != null && file.getFileName().toString().contains(".csv")) {
            pt("2. Wyświetl zawartość." +
                    "\n3. Wybierz algorytm." +
                    "\n4. Przetwarzaj." +
                    "\n5. Wyświetl wynik." +
                    "\n6. Wyjdź.");
        }
    }

    private static void readFile() {
        if (file == null) {
            pt("Nie wybrano pliku.");
            return;
        }
        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader(file.toFile()));
            List<String[]> lines = reader.readAll();
            dataSet = new DataSet();
            if (lines.size() > 1) {
                dataSet.setHeader(lines.get(0));
                List<String[]> rows = lines.subList(1, lines.size());
                String[][] data = new String[rows.size()][];
                for (int i = 0; i < rows.size(); i++) {
                    data[i] = rows.get(i);
                }
                dataSet.setData(data);
            } else {
                System.out.println("Brak rekordów z danymi.");
                file = null;
                showMenu();
            }

        } catch (FileNotFoundException e) {
            System.out.println("Nie znaleziono pliku. " + file.toString());
            file = null;
            showMenu();
        } catch (CsvException e) {
            System.out.println("Plik ma niewłaściwą strukturę. " + file.toString());
            file = null;
            showMenu();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void chooseService() {
        pt("========= Wybierz serwis =========");
        int i = 1;
        List<String> serviceNames = new LinkedList<>();
        for (Map.Entry<String, AnalysisService> entry : services.entrySet()) {
            serviceNames.add(entry.getKey());
            pt(i++ + " " + entry.getKey());
        }
        pt(i + " Anuluj.");
        int input = getChoice(1, i);
        if (input == i) return;
        String key = serviceNames.get(input - 1);
        chosenService = services.get(key);
        pt("Wybrany serwis: " + chosenService.getName());
    }

    private static void chooseFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Wybierz plik z rozszerzeniem *.csv");
        chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        chooser.addChoosableFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.getName().contains(".csv");
            }

            @Override
            public String getDescription() {
                return "Comma Separated Value | *.csv";
            }
        });
        JFrame parent = new JFrame("Wybierz plik.");
        parent.setVisible(true);
        int result = chooser.showOpenDialog(parent);
        parent.setVisible(false);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            if (selectedFile.getName().contains(".csv")) {
                file = selectedFile.toPath();
                System.out.println("Wybrany plik: " + file.toString());
                readFile();
            } else pt("Wybrano plik o niewłaściwym rozszerzeniu.");
            showMenu();
        }
    }

    private static int getChoice(int min, int max) {
        int input = -100;
        do {
            System.out.print("Wybór: ");
            try {
                Scanner scanner = new Scanner(System.in);
                input = scanner.nextInt();
            } catch (Exception e) {
                pt("Podaj liczbę całkowitą z przedziału <" + min + ";" + max + ">.");
            }
        } while (input < min || input > max);
        return input;
    }

    private static void pt(String s) {
        System.out.println(s);
    }
}
