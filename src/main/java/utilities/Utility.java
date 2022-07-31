package utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Utility {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final String URL = "https://petstore.swagger.io/v2/";

    public static int isObjectExistNeme(String endpoint, String name) throws IOException, InterruptedException {
        String requestURL = String.format("%s%s/%s", URL, endpoint,  name);
        HttpRequest request = HttpRequest.newBuilder().
                uri(URI.create(requestURL)).
                GET().
                build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode();
    }
    public static int isObjectExistId(String endpoint, long id) throws IOException, InterruptedException {
        String requestURL = String.format("%s%s/%d", URL, endpoint,  id);
        HttpRequest request = HttpRequest.newBuilder().
                uri(URI.create(requestURL)).
                GET().
                build();
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode();
    }

}
