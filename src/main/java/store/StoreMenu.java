package store;

import com.google.gson.Gson;
import utilities.Utility;

import java.util.Scanner;

public class StoreMenu {

    //https://petstore.swagger.io/#/store/getOrderById (1)
    public static void findOrderById() {
        System.out.println("Увага, номер замовлення повинен бути в межах від 1 до 10.");
        System.out.print("Введите id : ");
        Scanner sc = new Scanner(System.in);
        long orderId = sc.nextLong();
        try {
            if (Utility.isObjectExistId("store/order", orderId) == 200) {
                Order order = StoreHttp.getOrderByID(orderId);
                Gson gson = new Gson();
                System.out.println(gson.toJson(order));
            } else {
                System.out.println("Замовлення з таким id не існує");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //https://petstore.swagger.io/#/store/placeOrder (2)
    public static void placeOrder() {
        System.out.println("Вести замовлення на придбання домашньої тварини.");
        Order newOrder = inputAllDataOfOrder();
        try {
            StoreHttp.addNewOrder(newOrder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Order inputAllDataOfOrder() {
        System.out.print("id замовлення (від 1 до 10): ");
        Scanner sc = new Scanner(System.in);
        long idPet = sc.nextLong();
        System.out.print("id домашньої тварини: ");
        long petId = sc.nextLong();
        System.out.print("Кількість домашніх тварин: ");
        int quantity = sc.nextInt();
        System.out.print("Дата відвантаження (РРРР-ММ-ДД): ");
        String shipDate = sc.nextLine();
        if (shipDate.equals("")) shipDate = sc.nextLine();
        System.out.print("Статус замовлення (placed, approved, delivered): ");
        String status = sc.nextLine();
        boolean complete = true;
        return Order.builder().
                id(idPet).
                petId(petId).
                quantity(quantity).
                shipDate(shipDate).
                status(Order.Status.valueOf(status)).
                complete(complete).
                build();
    }

    //https://petstore.swagger.io/#/store/deleteOrder (3)
    public static void deleteOrder() {
        System.out.print("Ведіть дані по замовленню, який бажаєте видалити з бази.");
        Scanner sc = new Scanner(System.in);
        System.out.print("id домашньої тварини (1 - 9): ");
        long orderId = sc.nextLong();
        try {
            StoreHttp.deleteOrder("order/" + orderId, StoreHttp.getOrderByID(orderId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
