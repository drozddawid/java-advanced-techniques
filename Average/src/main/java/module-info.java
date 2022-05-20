import com.drozd.analyzer.average.Average;
import pl.edu.pwr.tkubik.ex.api.AnalysisService;

module Average {
    requires api;
    provides AnalysisService with Average;
}