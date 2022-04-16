package winx.bitirme.auth.client.model;

public class FormCompleteResponse {
    boolean formCompleted;

    public FormCompleteResponse(boolean formCompleted) {
        this.formCompleted = formCompleted;
    }

    public boolean isFormCompleted() {
        return formCompleted;
    }

    public void setFormCompleted(boolean formCompleted) {
        this.formCompleted = formCompleted;
    }
}
