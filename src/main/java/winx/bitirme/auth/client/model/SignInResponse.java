package winx.bitirme.auth.client.model;
public class SignInResponse {
    private String token;
    private Boolean isFormCompleted;
    private String tokenType;
    public SignInResponse(String token, Boolean didUserCompleteForm,String tokenType) {
        this.token = token;
        this.isFormCompleted = didUserCompleteForm;
        this.tokenType = tokenType;
    }

    public Boolean getFormCompleted() {
        return isFormCompleted;
    }

    public void setFormCompleted(Boolean formCompleted) {
        isFormCompleted = formCompleted;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public SignInResponse() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
