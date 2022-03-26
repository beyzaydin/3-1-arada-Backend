package winx.bitirme.chat.client.model;

public enum EnumChartReport {
    bullyingOrHarassment(1),
    sexuality(2),
    suicideOrSelfharm(3),
    hateSpeech(4),
    insult(5);

    private Integer code;

    private EnumChartReport(int code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
