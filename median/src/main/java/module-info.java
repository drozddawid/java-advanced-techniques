import com.drozd.analyzer.median.Median;
import pl.edu.pwr.tkubik.ex.api.AnalysisService;

module median {
    requires api;
    provides AnalysisService with Median;
}