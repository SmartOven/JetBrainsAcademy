package advisor.model.auth;

import advisor.Main;

public class ServerAuth {
    private static String accessToken = "";

    public static AuthResult authorizeAccess() {
        if (!accessToken.isEmpty()) {
            return AuthResult.ALREADY_AUTHENTICATED;
        }

        OAuthImpl oAuth = new OAuthImpl(
                Main.accessServerPoint,
                Main.clientId,
                Main.clientSecret,
                Main.redirectUri,
                "code"
        );

        oAuth.authorize();
        accessToken = oAuth.requestToken();

        return accessToken.isEmpty() ? AuthResult.DENIED : AuthResult.SUCCESS;
    }

    public static boolean isAccessProvided() {
        return !accessToken.isEmpty();
    }

    public static String getAccessToken() {
        return accessToken;
    }
}
