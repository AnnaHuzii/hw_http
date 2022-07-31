package pet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;


public class PetHttp {
    private static final HttpClient CLIENT = HttpClient.newHttpClient();
    private static final String URL = "https://petstore.swagger.io/v2/pet/";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final int RESPONCE_CODE_OK = 200;

    public static int uploadImagePet(long idPet, String petUrl, String additionalMetadata) throws IOException {
        String requestURL = String.format("%s%d/%s", URL, idPet, "uploadImage");
        File imgToLoad = new File(petUrl);
        InputStream fis = new FileInputStream(imgToLoad);
        byte[] allBytes = fis.readAllBytes();
        CloseableHttpClient client = HttpClients.createDefault();
        HttpEntity multipart = MultipartEntityBuilder.create().
                addTextBody("additionalMetadata", additionalMetadata, ContentType.TEXT_PLAIN).
                addBinaryBody("file", allBytes, ContentType.DEFAULT_BINARY, imgToLoad.getName()).
                build();
        HttpPost httpPost = new HttpPost(requestURL);
        httpPost.setEntity(multipart);
        int statusCode=0;
        try {
            client.execute(httpPost);
            statusCode = client.execute(httpPost).getStatusLine().getStatusCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return statusCode;
    }

    public static List<Pet> getFindPetsByStatus(String petStatus) throws IOException, InterruptedException {
        String requestURL = String.format("%s%s?status=%s", URL, "findByStatus", petStatus);
        HttpRequest request = HttpRequest.newBuilder().
                uri(URI.create(requestURL)).
                GET().
                build();
        HttpResponse<String> responce =  CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(responce.body(), new TypeToken<List<Pet>>(){}.getType());
    }

    public static Pet getPetByID(long id) throws IOException, InterruptedException {
        String requestURL = String.format("%s%d", URL, id);
        HttpRequest request = HttpRequest.newBuilder().
                uri(URI.create(requestURL)).
                GET().
                build();
        HttpResponse<String> responce = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return GSON.fromJson(responce.body(), Pet.class);
    }

    public static int updatePetByFormData (long idPet, String newName, String newStatus) throws IOException{
        String requestURL = String.format("%s%d", URL, idPet);
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(requestURL);
        List<NameValuePair> nameValuePairList = new ArrayList<>();
        nameValuePairList.add(new BasicNameValuePair("name", newName));
        nameValuePairList.add(new BasicNameValuePair("status", newStatus));
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nameValuePairList, "utf-8");
        formEntity.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
        httpPost.setEntity(formEntity);
        int statusCode=0;
        try {
            client.execute(httpPost);
            statusCode = client.execute(httpPost).getStatusLine().getStatusCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return statusCode;
    }

    public static void addNewPet( Object newPet) throws IOException, InterruptedException {
        String requestURL = String.format("%s", URL);
        String requestBody = GSON.toJson(newPet);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requestURL))
                .header("Content-Type", "application/json; charset=utf-8")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> responce = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        if (responce.statusCode() == RESPONCE_CODE_OK ) {
            System.out.println(((Pet) newPet).getCategory().getName()
                    + " з id: " + ((Pet) newPet).getId()
                    + ", кличка: " + ((Pet) newPet).getName()
                    + ", в статусі: " + ((Pet) newPet).getPetStatus()
                    + " - успішно добавлені в базу даних");
            System.out.println(newPet);
        } else {
            System.out.println("Щось пішло не так і  " + newPet.getClass().getName().replaceAll(".", "") + " не були добавлені в базу даних");
        }
    }

    public static void updateObject(Object objectUpdate) throws IOException, InterruptedException {
        String requestBody = GSON.toJson(objectUpdate);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .header("Content-Type", "application/json; charset=utf-8")
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> responce = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        if (responce.statusCode() == RESPONCE_CODE_OK ) {
            System.out.println("Дані по " + ((Pet) objectUpdate).getCategory().getName()
                    + " з id: " + ((Pet) objectUpdate).getId()
                    + ", кличка: " + ((Pet) objectUpdate).getName()
                    + ", в статусі: " + ((Pet) objectUpdate).getPetStatus()
                    + " - успішно обновлені");
        } else {
            System.out.println("Щось пішло не так і дані по "  + objectUpdate.getClass().getName().replaceAll("http.", "") + " не були обновлені");
        }
    }

    public static void deleteObject (long endpoint, Object objectDelete) throws IOException, InterruptedException {
        System.out.println(objectDelete);
        String requestBody = GSON.toJson(objectDelete);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL+endpoint))
                .method("DELETE", HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        HttpResponse<String> responce = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        if (responce.statusCode() == RESPONCE_CODE_OK ) {
            System.out.println(((Pet) objectDelete).getCategory().getName()
                    + " з id: " + ((Pet) objectDelete).getId()
                    + ", кличка: " + ((Pet) objectDelete).getName()
                    + ", в статусі: " + ((Pet) objectDelete).getPetStatus()
                    + " - успішно видалений");
        } else {
            System.out.println("Щось пішло не так і  "  + objectDelete.getClass().getName().replaceAll("http.", "") + " не був видалений");
        }
    }
}
