package com.drozd.analyzer.average;

import pl.edu.pwr.tkubik.ex.api.AnalysisException;
import pl.edu.pwr.tkubik.ex.api.AnalysisService;
import pl.edu.pwr.tkubik.ex.api.ClusteringException;
import pl.edu.pwr.tkubik.ex.api.DataSet;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Average implements AnalysisService {
    private Future<DataSet> task = null;
    private boolean wentWrong = false;
    private DataSet result = null;

    public Average() {
    }

    @Override
    public void setOptions(String[] options) throws AnalysisException {

    }

    @Override
    public String getName() {
        return "Average";
    }

    @Override
    public void submit(DataSet ds) throws AnalysisException {
        if(task != null && !task.isDone()) throw new AnalysisException("Median service is busy now.");
        wentWrong = ds == null;
        if(!wentWrong) {
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            task = executorService.submit(new GetAverageTask(this, ds));
        }
    }

    @Override
    public DataSet retrieve(boolean clear) throws ClusteringException {
        if(task == null) return null;
        if(!task.isDone()) return null;
        if(wentWrong) throw new ClusteringException("Task went wrong.");
        return result;
    }

    public void setWentWrong(boolean wentWrong) {
        this.wentWrong = wentWrong;
    }

    public void setResult(DataSet result) {
        this.result = result;
    }
}
