package advisor.model.auth;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpServer;
import advisor.model.data.Requests;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;

public class OAuthImpl {
    private final String accessServerPoint, clientID, clientSecret, redirectUri, responseType;

    private String authCode;

    public OAuthImpl(String accessServerPoint, String clientID, String clientSecret, String redirectUri, String responseType) {
        this.accessServerPoint = accessServerPoint;
        this.clientID = clientID;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
        this.responseType = responseType;
    }

    /**
     * Starting the HTTP server and listening to the result of user OAuth
     */
    public void authorize() {
        HttpServer server = null;
        CountDownLatch countDownLatch;
        try {
            // Creating count down latch to wait for the query
            countDownLatch = new CountDownLatch(1);

            // Creating and setting up HTTP server
            server = HttpServer.create();
            server.bind(new InetSocketAddress(8888), 0);
            server.createContext("/",
                    exchange -> {
                        // Get query params and extract the code from it
                        String query = exchange.getRequestURI().getQuery();
                        String message;
                        if (query == null) {
                            message = "Authorization code not found. Try again.";

                            // Send the response
                            exchange.sendResponseHeaders(200, message.length());
                            exchange.getResponseBody().write(message.getBytes());
                            exchange.getResponseBody().close();
                            return;
                        }

                        Optional<String> codeOptional = getCodeOptional(getParamsMap(query));

                        // Check if code is present
                        if (codeOptional.isPresent()) {
                            message = "Got the code. Return back to your program.";
                            authCode = codeOptional.get();
                            countDownLatch.countDown();
                        } else {
                            message = "Authorization code not found. Try again.";
                        }

                        // Send the response
                        exchange.sendResponseHeaders(200, message.length());
                        exchange.getResponseBody().write(message.getBytes());
                        exchange.getResponseBody().close();
                    }
            );

            server.start(); // Starting server
            System.out.println(buildAuthUrl(clientID, redirectUri, responseType)); // Printing the auth link
            countDownLatch.await(); // Waiting for the request
        } catch (IOException e) {
            System.err.println("Something went wrong while creating and setting up the server");
        } catch (InterruptedException e) {
            System.err.println("Something went wrong with countDownLatch awaiting");
        } finally {
            if (server != null) {
                server.stop(1);
            }
        }
    }

    /**
     * Sending the request to the authentication server to get user token by his code
     *
     * @return user auth token or empty string
     */
    public String requestToken() {
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString((clientID + ":" + clientSecret).getBytes(StandardCharsets.UTF_8)))
                .uri(URI.create(accessServerPoint + "/api/token"))
                .POST(HttpRequest.BodyPublishers.ofString(
                        "grant_type=authorization_code" +
                                "&code=" + authCode +
                                "&redirect_uri=" + redirectUri))
                .build();

        String responseBody = Requests.getStringResponse(request);
        if (responseBody.isEmpty()) {
            throw new RuntimeException("Something went wrong while sending the request");
        }

        JsonObject tokenJson = JsonParser.parseString(responseBody).getAsJsonObject();
        return tokenJson.get("access_token").getAsString();
    }

    String buildAuthUrl(String clientID, String redirectUri, String responseType) {
        return String.format("%s/authorize?client_id=%s&redirect_uri=%s&response_type=%s", accessServerPoint, clientID, redirectUri, responseType);
    }

    Optional<String> getCodeOptional(Map<String, String> paramsMap) {
        return Optional.ofNullable(paramsMap.get("code"));
    }

    Map<String, String> getParamsMap(String query) {
        Map<String, String> queryParams = new HashMap<>();

        // Iterate through the parameters
        for (String param : query.split("&")) {
            String[] paramKeyValue = param.split("=");

            if (paramKeyValue.length != 2) {
                throw new IllegalArgumentException("The query format is wrong");
            }

            // Add each into the map
            queryParams.put(paramKeyValue[0], paramKeyValue[1]);
        }

        return queryParams;
    }
}
