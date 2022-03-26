package winx.bitirme.chat.service.entity;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import winx.bitirme.chat.client.model.EnumChartReport;

import javax.validation.constraints.Size;
import java.time.OffsetDateTime;

@Document(collection = "chat-room-report")
public class ChatReportEntity {
    @Transient
    public static final String SEQUENCE_NAME = "chat_room_report_sequence";

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
