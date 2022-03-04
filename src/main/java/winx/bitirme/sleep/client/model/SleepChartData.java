package winx.bitirme.sleep.client.model;

import java.time.OffsetDateTime;

public class SleepChartData {

    OffsetDateTime startDate;

    OffsetDateTime endDate;

    public OffsetDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(OffsetDateTime startDate) {
        this.startDate = startDate;
    }

    public OffsetDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(OffsetDateTime endDate) {
        this.endDate = endDate;
    }
}
