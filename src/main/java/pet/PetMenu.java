package pet;

import com.google.gson.Gson;
import utilities.Utility;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PetMenu {

    //https://petstore.swagger.io/#/pet/getPetById (1)
    public static void findPetById() {
        System.out.print("Ведіть id : ");
        Scanner sc = new Scanner(System.in);
        long petId = sc.nextLong();
        try {
            if (Utility.isObjectExistId("pet", petId) == 200) {
                Pet pet = PetHttp.getPetByID(petId);
                System.out.println(pet);
            } else {
                System.out.println("Домашню тварину з id не існує");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //https://petstore.swagger.io/#/pet/findPetsByStatus (2)
    public static void findPetsByStatus(){
        System.out.print("Ведіть статус( available, pending, sold) : ");
        Scanner sc = new Scanner(System.in);
        String petStaus = sc.nextLine();
        try {
            List<Pet> pets = PetHttp.getFindPetsByStatus(petStaus);
            Gson gson = new Gson();
            System.out.println(gson.toJson(pets));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //https://petstore.swagger.io/#/pet/addPet (3)
    public static void addPetInShop() {
        System.out.println("Ведіть дані по домашній тварині.");
        Pet newPet = inputAllDataOfPet();
        try {
            PetHttp.addNewPet(newPet);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //https://petstore.swagger.io/#/pet/uploadFile (4)
    public static void uploadImage() {
        System.out.println("Для додавання фото, внесіть необхідні дані)");
        System.out.print("id домашньої тварини: ");
        Scanner sc = new Scanner(System.in);
        long idPet = sc.nextLong();
        System.out.print("Ведіть додаткові дані для передачі на сервер: ");
        String additionalMetedata = sc.nextLine();
        if( additionalMetedata.equals("")) additionalMetedata = sc.nextLine();
        System.out.print("Для того, щоб завантажити фото, розмістіть файл в кореневому каталозі програми" +
                " та вкажіть його ім'я (ім'я.розширення) : ");
        String photoUrl = sc.nextLine();
        try {
            if(200 == PetHttp.uploadImagePet(idPet, photoUrl, additionalMetedata)) {
                System.out.println("Фото успішно добавлене");
                Pet pet = PetHttp.getPetByID(idPet);
                pet.getPhotoUrls().add(photoUrl);
                System.out.println("Список посилань на фото наступний:");
                System.out.println(pet.getPhotoUrls().toString());
                PetHttp.updateObject(pet);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    //https://petstore.swagger.io/#/pet/updatePet (5)
    public static void updatePetAllData() {
        System.out.println("Ведіть всі дані по домашнім тваринам, які бажаєте оновити.");
        Pet petToUpdateAllData = PetMenu.inputAllDataOfPet();
        try {
            PetHttp.updateObject(petToUpdateAllData);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //https://petstore.swagger.io/#/pet/updatePetWithForm (6)
    public static void updatePetByFormData() {
        System.out.print("id домашньої тварини: ");
        Scanner sc = new Scanner(System.in);
        long idPet = sc.nextLong();
        System.out.println("Ведіть дані домашньої тварини, які бажаєте оновити. А саме: ");
        System.out.print("новая кличка : ");
        String namePet = sc.nextLine();
        if(namePet.equals("")) namePet = sc.nextLine();
        System.out.print("Новий статус вихованця (available,pending,sold): ");
        String petStatus = sc.nextLine();
        try {
            if (PetHttp.updatePetByFormData(idPet, namePet, petStatus) == 200) {
                System.out.println("Дані оновлено");
                Pet petUpdatedByFormData = PetHttp.getPetByID(idPet);
                Gson gson = new Gson();
                System.out.println(gson.toJson(petUpdatedByFormData));
            } else {
                System.out.println("Дані не змінено. Напевне були не коректно ведені дані");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Pet inputAllDataOfPet(){
        System.out.print("id домашньої тварини: ");
        Scanner sc = new Scanner(System.in);
        long idPet = sc.nextLong();
        System.out.print("Кличка: ");
        String namePet = sc.nextLine();
        if(namePet.equals("")) namePet = sc.nextLine();
        System.out.print("id категорії: ");
        long idCategory = sc.nextLong();
        System.out.print("Назва категорії: ");
        String nameCategory = sc.nextLine();
        if(nameCategory.equals("")) nameCategory= sc.nextLine();
        System.out.print("Посилання на фото: ");
        String photoUrl = sc.nextLine();
        ArrayList<String> photoUrls = new ArrayList<>();
        photoUrls.add(photoUrl);
        System.out.print("id теги: ");
        long idTag = sc.nextLong();
        System.out.print("Назва теги: ");
        String nameTag = sc.nextLine();
        if(nameTag.equals("")) nameTag = sc.nextLine();
        Tag tag = Tag.builder().id(idTag).name(nameTag).build();
        ArrayList<Tag> tags = new ArrayList<>();
        tags.add(tag);
        System.out.print("Статус домашньої тварини (available,pending,sold): ");
        String petStatus = sc.nextLine();
        return Pet.builder().
                id(idPet).
                category(Category.builder().id(idCategory).name(nameCategory).build()).
                name(namePet).
                photoUrls(photoUrls).
                tags(tags).
                petStatus(petStatus).
                build();
    }

    //https://petstore.swagger.io/#/pet/deletePet (7)
    public static void deletePet() {
        System.out.println("Ведіть дані вихованця, якого бажаєте видалити.");
        Scanner sc = new Scanner(System.in);
        System.out.print("id домашньої тварини : ");
        long idPet = sc.nextLong();
        try {
            Pet petDelete = PetHttp.getPetByID(idPet);
            PetHttp.deleteObject(idPet, petDelete);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
