package com.zemoso.ztalent.payload;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshTokenResponse {
    private String refreshToken;
    private String tokenType = "Bearer";

    public RefreshTokenResponse(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
