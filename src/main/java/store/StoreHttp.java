package store;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class StoreHttp {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final String URL = "https://petstore.swagger.io/v2/store/";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final int RESPONCE_CODE_OK = 200;

    //https://petstore.swagger.io/#/store/getInventory (4)
    public static void getInventory() throws IOException, InterruptedException {
        String requestURL = String.format("%s%s", URL, "inventory");
        HttpRequest request = HttpRequest.newBuilder().
                uri(URI.create(requestURL)).
                GET().
                build();
        HttpResponse<String> responce = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(responce.body().replaceAll(",", ",\n"));
    }

    public static Order getOrderByID(long id) throws IOException, InterruptedException {
        String requestURL = String.format("%s%s/%d", URL, "order",id);
        HttpRequest request = HttpRequest.newBuilder().
                uri(URI.create(requestURL)).
                GET().
                build();
        HttpResponse<String> responce = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(responce.body(), Order.class);
    }

    public static void addNewOrder(Object newOrder) throws IOException, InterruptedException {
        String requestURL = String.format("%s%s", URL, "order");
        String requestBody = GSON.toJson(newOrder);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requestURL))
                .header("Content-Type", "application/json; charset=utf-8")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> responce = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        if (responce.statusCode() == RESPONCE_CODE_OK ) {
            System.out.println("???????????????????? ??????????: " + ((Order) newOrder).getShipDate()
                    + " ?? id: " + ((Order) newOrder).getId()
                    + ", ?? ??????????????: " + ((Order) newOrder).getStatus()
                    + " - ?????????????? ?????????????????? ?? ???????? ??????????");
            System.out.println(newOrder);
        } else {
            System.out.println("???????? ?????????? ???? ?????? ??  " + newOrder.getClass().getName().replaceAll("", "") + " ???? ???????? ?????????????????? ?? ???????? ??????????");
        }
    }

    public static void deleteOrder(String endpoint, Object orderDelete) throws IOException, InterruptedException {
        System.out.println(orderDelete);
        String requestBody = GSON.toJson(orderDelete);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL + endpoint))
                .method("DELETE", HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> responce = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        if (responce.statusCode() == RESPONCE_CODE_OK) {
            System.out.println("???????????????????? ??????????: " + ((Order) orderDelete).getShipDate()
                    + " ?? id: " + ((Order) orderDelete).getId()
                    + ", ?? ??????????????: " + ((Order) orderDelete).getStatus()
                    + " - ?????????????? ??????????????????");
        } else {
            System.out.println("???????? ?????????? ???? ?????? ??  "  + orderDelete.getClass().getName().replaceAll("http.", "") + " ???? ?????? ??????????????????");
        }
    }
}
