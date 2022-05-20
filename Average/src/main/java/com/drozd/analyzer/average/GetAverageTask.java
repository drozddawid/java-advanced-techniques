package com.drozd.analyzer.average;

import pl.edu.pwr.tkubik.ex.api.DataSet;

import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class GetAverageTask implements Callable<DataSet> {
    private final Average caller;
    private final DataSet dataSet;

    public GetAverageTask(Average caller, DataSet dataSet) {
        this.caller = caller;
        this.dataSet = dataSet;
    }

    @Override
    public DataSet call() throws Exception {
        DataSet result = new DataSet();
        result.setHeader(dataSet.getHeader());
        String[][] results = new String[dataSet.getData().length][];
        String[][] data = dataSet.getData();
        for (int i = 0; i < data.length; i++) {
            results[i] = getRowResult(data[i]);
        }
        Thread.sleep(5000);
        result.setData(results);
        caller.setResult(result);
        return result;
    }

    private String[] getRowResult(String[] row) {
        return new String[]{row[0], "Average", getAverage(row)};
    }

    private String getAverage(String[] row) {
        try {
            String[] numericData = new String[row.length - 2];
            System.arraycopy(row, 2, numericData, 0, row.length - 2);
            var parsedData = Arrays.stream(numericData).map(Double::parseDouble).collect(Collectors.toList());
            AtomicReference<Double> sum = new AtomicReference<>(0.0);
            parsedData.forEach(el -> sum.updateAndGet(v -> v + el));
            int size = parsedData.size();
            return String.valueOf(sum.get() / size);
        } catch (Exception e) {
            caller.setWentWrong(true);
            e.printStackTrace();
            return "Calculations went wrong.";
        }
    }
}
