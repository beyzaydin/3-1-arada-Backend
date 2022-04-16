package winx.bitirme.auth.client.model;
public class SignInResponse {
    private String token;
    private Boolean isFormCompleted;

    public SignInResponse(String token, Boolean didUserCompleteForm) {
        this.token = token;
        this.isFormCompleted = didUserCompleteForm;
    }

    public SignInResponse() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getDidUserCompleteForm() {
        return isFormCompleted;
    }

    public void setDidUserCompleteForm(Boolean didUserCompleteForm) {
        this.isFormCompleted = didUserCompleteForm;
    }
}
