package user;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import pet.Pet;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class UserHttp {
    public static final HttpClient CLIENT = HttpClient.newHttpClient();
    public static final String URL = "https://petstore.swagger.io/v2/user/";
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final int RESPONCE_CODE_OK = 200;

    public static void logsUser(String userName, String password) throws IOException, InterruptedException {
        String requestURL = String.format("%slogin?username=%s&password=%s", URL, userName, password);
        HttpRequest request = HttpRequest.newBuilder().
                uri(URI.create(requestURL)).
                GET().
                build();
        HttpResponse<String> responce = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        if (responce.statusCode() == RESPONCE_CODE_OK ) {
            System.out.println("Авторизація " +  userName + " успішна");
        } else {
            System.out.println("Щось пішло не так і  "  + userName + " не авторизувався в системі");
        }
    }

    //https://petstore.swagger.io/#/user/logoutUser (2)
    public static void logsOutUser() throws IOException, InterruptedException {
        String requestURL = String.format("%s%s", URL, "logout");
        HttpRequest request = HttpRequest.newBuilder().
                uri(URI.create(requestURL)).
                GET().
                build();
        HttpResponse<String> responce = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        if (responce.statusCode() == RESPONCE_CODE_OK) {
            System.out.println("Користувач успішно вийшов з системи");
        } else {
            System.out.println("Щось пішло не так і користувач не вийшов з системи");
        }
    }

    public static User getUserByUserName(String userName) throws IOException, InterruptedException {
        String requestURL = String.format("%s%s", URL, userName);
        HttpRequest request = HttpRequest.newBuilder().
                uri(URI.create(requestURL)).
                GET().
                build();
        HttpResponse<String> responce = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(responce.body(), User.class);
    }


    public static void addNewUser(Object newUser) throws IOException, InterruptedException {
        String requestURL = String.format("%s", URL);
        String requestBody = GSON.toJson(newUser);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requestURL))
                .header("Content-Type", "application/json; charset=utf-8")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> responce = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        if (responce.statusCode() == RESPONCE_CODE_OK ) {
            System.out.println(((User) newUser).getFirstName() + " " + ((User) newUser).getLastName()
                    + " з id: " + ((User) newUser).getId()
                    + ", userName: " + ((User) newUser).getUsername()
                    + "  успішно добавлені в базу даних");
            System.out.println(newUser);
        } else {
            System.out.println("Щось пішло не так і  " + newUser.getClass().getName().replaceAll("http.", "") + " не були добавлені в базу даних");
        }
    }

    public static void createListOfNewUsers (List<User> newUsers) throws IOException, InterruptedException {
        String requestURL = String.format("%s%s", URL, "createWithList");
        String requestBody = GSON.toJson(newUsers);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requestURL))
                .header("Content-Type", "application/json; charset=utf-8")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> responce = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        if (responce.statusCode() == RESPONCE_CODE_OK ) {
            System.out.println(newUsers);
            System.out.println("Список "
                    + newUsers.get(0).getClass().getName().replaceAll("httpUtilities.", "")
                    + " успішно добавлений в базу даних");
        } else {
            System.out.println("Щось пішло не так і список "
                    + newUsers.get(0).getClass().getName().replaceAll("httpUtilities.", "")
                    + " не було добавлено в базу даних");
        }
    }
    public static List<User> createListOfUsersForTest() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            User newUser = User.builder().
                    id(100+i).
                    username("name" + i).
                    firstName("firstName" + i).
                    lastName("lastName" + i).
                    email("test" + i + "@gmail.com").
                    password("password" + i).
                    phone("067-123-45-0" + i).
                    userStatus(1).
                    build();
            users.add(newUser);
        }
        return users;
    }

    public static void updateUser(String endpoint, Object userUpdate) throws IOException, InterruptedException {
        String requestBody = GSON.toJson(userUpdate);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL + endpoint))
                .header("Content-Type", "application/json; charset=utf-8")
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> responce = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        if (responce.statusCode() == RESPONCE_CODE_OK ) {
            System.out.println("Дані по " + ((User) userUpdate).getFirstName() + " " + ((User) userUpdate).getLastName()
                    + " з id: " + ((User) userUpdate).getId()
                    + ", userName: " + ((User) userUpdate).getUsername()
                    + " - успішно обновлені");
        } else {
            System.out.println("Щось пішло не так і дані по "  + userUpdate.getClass().getName().replaceAll("http.", "") + " не були обновлені");
        }
    }

    public static void deleteUser(String endpoint, Object userDelete) throws IOException, InterruptedException {
        System.out.println(userDelete);
        String requestBody = GSON.toJson(userDelete);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL+endpoint))
                .method("DELETE", HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> responce = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        if (responce.statusCode() == RESPONCE_CODE_OK ) {
            System.out.println(((User) userDelete).getFirstName() + " " + ((User) userDelete).getLastName()
                    + " з id: " + ((User) userDelete).getId()
                    + ", userName: " + ((User) userDelete).getUsername()
                    + " - успішно видалений");
        } else {
            System.out.println("Щось пішло не так і  "  + userDelete.getClass().getName().replaceAll("http.", "") + " не був видалений");
        }
    }
}
