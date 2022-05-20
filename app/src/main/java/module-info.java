import pl.edu.pwr.tkubik.ex.api.AnalysisService;

module app {
    requires api;
    uses AnalysisService;
    requires com.opencsv;
    requires java.desktop;
}