package advisor.model.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import advisor.Main;

import java.net.URI;
import java.net.http.HttpRequest;

public class ServerData {

    private static final String featuredPlaylistsUri = "featured-playlists";
    private static final String newAlbumsUri = "new-releases";
    private static final String categoriesUri = "categories";
    private static final String playlistUri = "playlists";

    private static final String categoryDoesntExistMsg = "Category doesn't exist";

    public static JsonObject getFeaturedPlaylists(String accessToken) {
        return requestUserData(accessToken, featuredPlaylistsUri);
    }

    public static JsonObject getNewAlbums(String accessToken) {
        return requestUserData(accessToken, newAlbumsUri);
    }

    public static JsonObject getCategories(String accessToken) {
        return requestUserData(accessToken, categoriesUri);
    }

    public static JsonObject getPlaylistsByCategoryName(String accessToken, String categoryName) {
        String categoryId = getCategoryIdByName(accessToken, categoryName);

        if (categoryId.isEmpty()) {
            return getJsonObjectWithError(categoryDoesntExistMsg);
        }

        return requestUserData(accessToken, categoriesUri + "/" + categoryId + "/" + playlistUri);
    }

    static JsonObject requestUserData(String accessToken, String uriString) {
        String fullUriString = Main.resource + Main.path + uriString;

        HttpRequest request = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + accessToken)
                .uri(URI.create(fullUriString))
                .GET()
                .build();

        return Requests.getJsonResponse(request);
    }

    static String getCategoryIdByName(String accessToken, String categoryName) {
        JsonObject categoriesJson = getCategories(accessToken);
        JsonArray categoriesArray = categoriesJson.getAsJsonObject("categories").getAsJsonArray("items");
        for (JsonElement category : categoriesArray) {
            JsonObject categoryObject = category.getAsJsonObject();
            String name = categoryObject.get("name").getAsString();
            if (name.equals(categoryName)) {
                return categoryObject.get("id").getAsString();
            }
        }
        return "";
    }

    /**
     * Creates a JsonObject, that contain error.
     * Error JsonObject format:
     * <pre>{@code
     * {
     *     "error": {
     *         "status": 400,
     *         "message": "Some error message"
     *     }
     * }
     * }</pre>
     *
     * @param errorMessage message about error
     * @return json object with error, that has message inside
     */
    static JsonObject getJsonObjectWithError(String errorMessage) {
        return JsonParser.parseString(String.format("{ \"error\" : { \"status\" : 400, \"message\" : \"%s\" } }", errorMessage)).getAsJsonObject();
    }
}
