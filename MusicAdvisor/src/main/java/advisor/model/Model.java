package advisor.model;

import com.google.gson.JsonObject;
import advisor.model.auth.ServerAuth;
import advisor.model.data.JsonExtractor;
import advisor.model.data.ServerData;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class Model {

    public static Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }

    public String authorizeAccess() {
        switch (ServerAuth.authorizeAccess()) {
            case ALREADY_AUTHENTICATED:
                return alreadyAuthorizedMsg;
            case SUCCESS:
                return successfulAuthorizationMsg;
            case DENIED:
                return emptyTokenMsg;
            default:
                return "Can't happen";
        }
    }

    public List<String> getNewAlbums() {
        return getAndConvertDataByAccessToken(
                ServerData::getNewAlbums,
                JsonExtractor::extractNewAlbumsList,
                ServerAuth::getAccessToken
        );
    }

    public List<String> getCategories() {
        return getAndConvertDataByAccessToken(
                ServerData::getCategories,
                JsonExtractor::extractCategoriesList,
                ServerAuth::getAccessToken
        );
    }

    public List<String> getPlaylistsByCategoryName(String categoryName) {
        return getAndConvertDataByAccessTokenAndString(
                categoryName,
                ServerData::getPlaylistsByCategoryName,
                JsonExtractor::extractPlaylistsList,
                ServerAuth::getAccessToken
        );
    }

    public List<String> getFeaturedPlaylists() {
        return getAndConvertDataByAccessToken(
                ServerData::getFeaturedPlaylists,
                JsonExtractor::extractFeaturedPlaylistsList,
                ServerAuth::getAccessToken
        );
    }

    /**
     * Takes access token, gets data by the token and covert it to the list of strings
     *
     * @param getDataMethod        method to get data from server
     * @param convertDataMethod    method to convert data from Json to List of Strings
     * @param getAccessTokenMethod method to get access token
     * @return list of strings representing the data
     */
    List<String> getAndConvertDataByAccessToken(Function<String, JsonObject> getDataMethod,
                                                Function<JsonObject, List<String>> convertDataMethod,
                                                Supplier<String> getAccessTokenMethod) {
        String accessToken = getAccessTokenMethod.get();
        if (accessToken.isEmpty()) {
            return List.of(accessNotProvidedMsg);
        }
        JsonObject dataJson = getDataMethod.apply(accessToken);
        return convertDataMethod.apply(dataJson);
    }

    List<String> getAndConvertDataByAccessTokenAndString(String findBy,
                                                         BiFunction<String, String, JsonObject> getDataMethod,
                                                         Function<JsonObject, List<String>> convertDataMethod,
                                                         Supplier<String> getAccessTokenMethod) {
        String accessToken = getAccessTokenMethod.get();
        if (accessToken.isEmpty()) {
            return List.of(accessNotProvidedMsg);
        }
        JsonObject dataJson = getDataMethod.apply(accessToken, findBy);
        return convertDataMethod.apply(dataJson);
    }

    private static final String alreadyAuthorizedMsg = "You are already logged in";
    private static final String successfulAuthorizationMsg = "---SUCCESS---";
    private static final String emptyTokenMsg = "Token not provided";
    private static final String accessNotProvidedMsg = "Please, provide access for application.";

    private static Model instance;

    private Model() {
    }
}
