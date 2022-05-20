package com.drozd.analyzer.median;

import pl.edu.pwr.tkubik.ex.api.DataSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GetMediansTask implements Callable<DataSet> {
    private final DataSet dataSet;
    private final Median caller;

    public GetMediansTask(Median caller, DataSet dataSet) throws NullPointerException {
        if (dataSet == null) throw new NullPointerException("Given dataset is null.");
        if (caller == null) throw new NullPointerException("Given caller is null. Should be instance of Median.");
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
        return new String[]{row[0], "Median", getMedian(row)};
    }

    private String getMedian(String[] data){
        try {
            String[] numericData = new String[data.length - 2];
            System.arraycopy(data, 2, numericData, 0, data.length - 2);
            var parsedData = Arrays.stream(numericData).map(Double::parseDouble).sorted().collect(Collectors.toCollection(ArrayList<Double>::new));
            int dataLen = parsedData.size();
            if (dataLen == 0)
                throw new IllegalArgumentException("Given data length is 0 and should be greater than 0.");
            if (dataLen % 2 == 0) {
                return String.valueOf((parsedData.get(dataLen / 2) + parsedData.get(dataLen / 2 - 1)) / 2);
            } else {
                return String.valueOf(parsedData.get(dataLen / 2));
            }
        } catch (Exception e) {
            caller.setWentWrong(true);
            e.printStackTrace();
            return "Calculations went wrong.";
        }
    }
}
