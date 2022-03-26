package winx.bitirme.chat.client.model;

import javax.validation.constraints.Size;
import java.time.OffsetDateTime;

public class ChatReportModel {
    private long id;

    private String reporter;

    private String reported;

    private EnumChartReport enumChartReport;

    @Size(max = 250)
    private String explanation;

    private OffsetDateTime reportDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public String getReported() {
        return reported;
    }

    public void setReported(String reported) {
        this.reported = reported;
    }

    public EnumChartReport getEnumChartReport() {
        return enumChartReport;
    }

    public void setEnumChartReport(EnumChartReport enumChartReport) {
        this.enumChartReport = enumChartReport;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public OffsetDateTime getReportDate() {
        return reportDate;
    }

    public void setReportDate(OffsetDateTime reportDate) {
        this.reportDate = reportDate;
    }
}
