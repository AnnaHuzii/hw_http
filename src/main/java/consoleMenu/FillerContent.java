package consoleMenu;

import java.util.HashMap;

public class FillerContent {

    public HashMap<Integer, String> fill(String name) {
        HashMap<Integer, String> contentMenu = new HashMap<>();
        switch(name) {
            case "Main":
                contentMenu.put(1, "Домашні тварини. Все про ваших улюбленців");
                contentMenu.put(2, "Замовлення. Доступ до замовлень у зоомагазині");
                contentMenu.put(3, "Користувачі. Операції щодо користувачів");
                contentMenu.put(4, "Вийти з програми");
                break;
            case "Pet":
                contentMenu.put(1, "Найти домашню тварину за id");
                contentMenu.put(2, "Знайти домашніх тварин за статусом" );
                contentMenu.put(3, "Додати нового вихованця в магазин");
                contentMenu.put(4, "Завантажити зображення домашньої тварини");
                contentMenu.put(5, "Оновити дані наявного вихованця");
                contentMenu.put(6, "Оновлює дані по домашній тварині в магазині з допомогою форми");
                contentMenu.put(7, "Видалити домашню тварину");
                contentMenu.put(8, "Перейти до верхнього меню");
                break;
            case "Store":
                contentMenu.put(1, "Знайти замовлення на купівлю за ідентифікатором");
                contentMenu.put(2, "Створити замовлення на придбання домашньої тварини");
                contentMenu.put(3, "Видалити замовлення на придбання по id");
                contentMenu.put(4, "Провести інвентаризацію домашніх тварин за статусом");
                contentMenu.put(5, "Перейти до верхнього меню");
                break;
            case "User":
                contentMenu.put(1, "Вхід користувача в системі");
                contentMenu.put(2, "Вихід користувача із поточного сеансу" );
                contentMenu.put(3, "Найти користувача по userName");
                contentMenu.put(4, "Додати користувача");
                contentMenu.put(5, "Додати користувачів із списку");
                contentMenu.put(6, "Оновити дані по існуючому користувачу");
                contentMenu.put(7, "Видалити користувача");
                contentMenu.put(8, "Перейти до верхнього меню");
        }
        return contentMenu;
    }
}
