package user;

import com.google.gson.Gson;
import utilities.Utility;

import java.util.List;
import java.util.Scanner;

public class UserMenu {

    //https://petstore.swagger.io/#/user/loginUser (1)
    public static void loginUser() {
        System.out.print("Введите имя пользователя : ");
        Scanner sc = new Scanner(System.in);
        String nameForLogIn = sc.nextLine();
        System.out.print("Введите пароль пользователя : ");
        String passwordForLogIn = sc.nextLine();
        try {
            UserHttp.logsUser(nameForLogIn, passwordForLogIn);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //https://petstore.swagger.io/#/user/getUserByName (3)
    public static void findUserByName() {
        System.out.print("Ведіть userName користувача: ");
        Scanner sc = new Scanner(System.in);
        String userName = sc.nextLine();
        try {
            if (Utility.isObjectExistNeme("user", userName) == 200) {
                User user = UserHttp.getUserByUserName(userName);
                Gson gson = new Gson();
                System.out.println(gson.toJson(user));
            } else {
                System.out.println("Користувач з таким ім'ям не існує");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //https://petstore.swagger.io/#/user/createUser (4)
    public static void createUser() {
        System.out.println("Ведіть дані по користувачу.");
        User newUser = inputAllDataOfUser();
        try {
            UserHttp.addNewUser(newUser);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //https://petstore.swagger.io/#/user/createUsersWithListInput (5)
    public static void createUsersList() {
        List<User> usersForTest = UserHttp.createListOfUsersForTest();
        try {
            UserHttp.createListOfNewUsers(usersForTest);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //https://petstore.swagger.io/#/user/updateUser (6)
    public static void updateUser() {
        System.out.print("Ведіть userName користувача, якого хочете оновити: ");
        Scanner sc = new Scanner(System.in);
        String nameToUpdate = sc.nextLine();
        try {
            if (Utility.isObjectExistNeme("user", nameToUpdate) == 200) {
                System.out.println("Ведіть всі дані користувача, якого бажаєте оновити.");
                User userToUpdate = inputAllDataOfUser();
                UserHttp.updateUser(nameToUpdate, userToUpdate);
            } else {
                System.out.println("Користувач з таким userName не існує");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //https://petstore.swagger.io/#/user/deleteUser (7)
    public static void deleteUser() {
        System.out.print("Ведіть userName користувача, якого бажаєте видалити з системи: ");
        Scanner sc = new Scanner(System.in);
        String nameToDelete = sc.nextLine();
        try {
            if (Utility.isObjectExistNeme("user", nameToDelete) == 200) {
                User userToDelete = UserHttp.getUserByUserName(nameToDelete);
                UserHttp.deleteUser(nameToDelete, userToDelete);
            } else {
                System.out.println("Користувач з таким username не існує в системі");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static User inputAllDataOfUser () {
        Scanner sc = new Scanner(System.in);
        System.out.print("id користувача: ");
        long userId = sc.nextLong();
        System.out.print("userName користувача: ");
        String userName = sc.nextLine();
        if(userName.equals("")) userName = sc.nextLine();
        System.out.print("Ім'я користувача: ");
        String firstName = sc.nextLine();
        System.out.print("Прізвище користувача: ");
        String lastName = sc.nextLine();
        System.out.print("e-mail користувача: ");
        String email = sc.nextLine();
        System.out.print("Пароль користувача: ");
        String password = sc.nextLine();
        System.out.print("Телефон користувача: ");
        String phone = sc.nextLine();
        System.out.print("Статус користувача: ");
        int userStatus = sc.nextInt();
        return User.builder().
                id(userId).
                username(userName).
                firstName(firstName).
                lastName(lastName).
                email(email).
                password(password).
                phone(phone).
                userStatus(userStatus).
                build();
    }
}
