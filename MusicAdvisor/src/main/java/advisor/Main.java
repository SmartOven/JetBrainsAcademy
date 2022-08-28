package advisor;

public class Main {
    public static String clientId = "84d42a9caa3b46c998e0e60aa39b6769";
    public static String clientSecret = "694c5e96f3fe467c9d8824a3f7a78401";
    public static String redirectUri = "http://127.0.0.1:8888/";
    public static String accessServerPoint = "https://accounts.spotify.com";
    public static String resource = "https://api.spotify.com";
    public static String path = "/v1/browse/";
    public static int entriesPerPage = 5;

    public static void main(String[] args) {
        parseArgs(args);

        CLI userInterface = CLI.getInstance(System.in);
        userInterface.work();
    }

    private static void parseArgs(String[] args) {
        // Parse settings
        for (int i = 0; i < args.length - 1; i++) {
            switch (args[i]) {
                case "-access":
                    accessServerPoint = args[i + 1];
                    break;
                case "-resource":
                    resource = args[i + 1];
                    break;
                case "-page":
                    entriesPerPage = Integer.parseInt(args[i + 1]);
                    break;
            }
        }
    }
}
