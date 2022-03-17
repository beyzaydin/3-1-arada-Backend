package winx.bitirme.auth.service.entity;

public class VisibleUser {
    private String accessToken;
    private String email;
    private int id;
    private String tokenType;
    private String username;

    public VisibleUser() {
    }

    public VisibleUser(String accessToken, String email, int id, String tokenType, String username) {
        this.accessToken = accessToken;
        this.email = email;
        this.id = id;
        this.tokenType = tokenType;
        this.username = username;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
