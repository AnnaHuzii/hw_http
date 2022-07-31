package consoleMenu;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MenuService {
    private Map<String, Menu> elementsMenu = new HashMap<String, Menu>();

    public void createTopLevelOfMenu() {
        FillerContent fillerMenuContent = new FillerContent();

        Menu mainMenu = new Menu("Main");
        mainMenu.setContentMenu(fillerMenuContent.fill("Main"));
        elementsMenu.put("Main", mainMenu);

        Menu petMenu = new Menu("Pet");
        petMenu.setContentMenu(fillerMenuContent.fill("Pet"));
        elementsMenu.put("Pet", petMenu);

        Menu storeMenu = new Menu("Store");
        storeMenu.setContentMenu(fillerMenuContent.fill("Store"));
        elementsMenu.put("Store", storeMenu);

        Menu userMenu = new Menu("User");
        userMenu.setContentMenu(fillerMenuContent.fill("User"));
        elementsMenu.put("User", userMenu);

    }

    public Menu getMenuObjectByName(String name) {
        Menu result = null;
        Optional<Menu> optionalResult = Optional.empty();
        for (Map.Entry<String, Menu> entry : elementsMenu.entrySet()) {
            if ( entry.getKey().equals(name)) optionalResult = Optional.ofNullable(entry.getValue());
        }
        if (optionalResult.isPresent() ) {
            result = optionalResult.get();
        } else {
            System.out.println("Меню з такою назвою не знайдено");
        }
        return result;
    }
    
}
