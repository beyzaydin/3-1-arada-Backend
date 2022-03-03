package winx.bitirme.auth.service.entity;

public enum EnumGender {
    FEMALE("F"),
    MALE("M"),
    OTHER("O");

    private String code;

    private EnumGender(String depCode) {

        this.code = depCode;
    }

    public String getCode() {

        return this.code;
    }

}
