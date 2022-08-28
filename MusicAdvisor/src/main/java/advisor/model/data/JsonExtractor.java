package advisor.model.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.LinkedList;
import java.util.List;

public class JsonExtractor {
    public static List<String> extractCategoriesList(JsonObject json) {
        String errorMessage = getErrorMessageIfPresent(json);
        if (!errorMessage.isEmpty()) {
            return List.of(errorMessage);
        }

        List<String> categoriesList = new LinkedList<>();
        for (JsonElement categoryElement : json.getAsJsonObject("categories").getAsJsonArray("items")) {
            categoriesList.add(categoryElement.getAsJsonObject().get("name").getAsString());
        }
        return categoriesList;
    }

    public static List<String> extractPlaylistsList(JsonObject json) {
        String errorMessage = getErrorMessageIfPresent(json);
        if (!errorMessage.isEmpty()) {
            return List.of(errorMessage);
        }

        List<String> playlistsList = new LinkedList<>();
        for (JsonElement playlistElement : json.getAsJsonObject("playlists").getAsJsonArray("items")) {
            JsonObject playlist = playlistElement.getAsJsonObject();
            playlistsList.add(playlist.get("name").getAsString() + "\n" + playlist.getAsJsonObject("external_urls").get("spotify").getAsString() + "\n");
        }
        return playlistsList;
    }

    public static List<String> extractFeaturedPlaylistsList(JsonObject json) {
        return extractPlaylistsList(json);
    }

    public static List<String> extractNewAlbumsList(JsonObject json) {
        String errorMessage = getErrorMessageIfPresent(json);
        if (!errorMessage.isEmpty()) {
            return List.of(errorMessage);
        }

        List<String> albumsList = new LinkedList<>();
        // Iterate through albums
        JsonArray albumsArray = json.getAsJsonObject("albums").getAsJsonArray("items");
        for (JsonElement albumElement : albumsArray) {
            JsonObject album = albumElement.getAsJsonObject();
            String albumName = album.get("name").getAsString();

            // Create string builder for album information
            // Add album name
            StringBuilder albumInfo = new StringBuilder(albumName);

            // Add album artists
            JsonArray artists = album.getAsJsonArray("artists");

            albumInfo.append("\n[");
            albumInfo.append(artists.get(0).getAsJsonObject().get("name").getAsString());
            for (int i = 1; i < artists.size(); i++) {
                albumInfo.append(", ").append(artists.get(i).getAsJsonObject().get("name").getAsString());
            }
            albumInfo.append("]\n");

            // Add album link
            albumInfo.append(album.getAsJsonObject("external_urls").get("spotify").getAsString());
            albumInfo.append("\n");

            // Add album info to the albums list
            albumsList.add(albumInfo.toString());
        }
        return albumsList;
    }

    static String getErrorMessageIfPresent(JsonObject json) {
        if (!json.has("error")) {
            return "";
        }

        JsonObject errorJson = json.getAsJsonObject("error");
        if (!errorJson.has("message")) {
            return preErrorMessage + noErrorMessage;
        }
        return preErrorMessage + errorJson.get("message").getAsString();
    }

    private static final String preErrorMessage = "Error occurred: ";
    private static final String noErrorMessage = "No error message specified";
}
