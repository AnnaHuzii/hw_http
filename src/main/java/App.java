

import consoleMenu.MenuService;
import pet.PetMenu;
import store.StoreHttp;
import store.StoreMenu;
import user.UserHttp;
import user.UserMenu;

import java.io.IOException;

public class App {
    private static final int EXIT_MAIN_MENU = 4;
    private static final int EXIT_PET_MENU = 8;
    private static final int EXIT_STORE_MENU = 5;
    private static final int EXIT_USER_MENU = 8;

    public static void main(String[] args) throws IOException, InterruptedException {
        MenuService menuService = new MenuService();
        menuService.createTopLevelOfMenu();
        int choice;
        do {
            menuService.getMenuObjectByName("Main").printMenu();
            choice = menuService.getMenuObjectByName("Main").makeChoice();
            switch (choice) {
                case 1:
                    int choicePet;
                    do {
                        menuService.getMenuObjectByName("Pet").printMenu();
                        choicePet = menuService.getMenuObjectByName("Pet").makeChoice();
                        switch (choicePet) {
                            case 1:
                                PetMenu.findPetById();
                                break;
                            case 2:
                                PetMenu.findPetsByStatus();
                                break;
                            case 3:
                                PetMenu.addPetInShop();
                                break;
                            case 4:
                                PetMenu.uploadImage();
                                break;
                            case 5:
                                PetMenu.updatePetAllData();
                                break;
                            case 6:
                                PetMenu.updatePetByFormData();
                                break;
                            case 7:
                                PetMenu.deletePet();
                                break;
                            default:
                                System.out.println("Ведене не вірне значення - спробуйте ще");
                                break;
                        }
                    } while (choicePet != EXIT_PET_MENU);
                    break;
                case 2:
                    int choiceStore;
                    do {
                        menuService.getMenuObjectByName("Store").printMenu();
                        choiceStore = menuService.getMenuObjectByName("Store").makeChoice();
                        switch (choiceStore) {
                            case 1:
                                StoreMenu.findOrderById();
                                break;
                            case 2:
                                StoreMenu.placeOrder();
                                break;
                            case 3:
                                StoreMenu.deleteOrder();
                                break;
                            case 4:
                                StoreHttp.getInventory();
                                break;
                            default:
                                System.out.println("Ведене не вірне значення - спробуйте ще");
                                break;
                        }
                    } while (choiceStore != EXIT_STORE_MENU);
                    break;
                case 3:
                    int choiceUser;
                    do {
                        menuService.getMenuObjectByName("User").printMenu();
                        choiceUser = menuService.getMenuObjectByName("User").makeChoice();
                        switch (choiceUser) {
                            case 1:
                                UserMenu.loginUser();
                                break;
                            case 2:
                                UserHttp.logsOutUser();
                                break;
                            case 3:
                                UserMenu.findUserByName();
                                break;
                            case 4:
                                UserMenu.createUser();
                                break;
                            case 5:
                                UserMenu.createUsersList();
                                break;
                            case 6:
                                UserMenu.updateUser();
                                break;
                            case 7:
                                UserMenu.deleteUser();
                                break;
                            default:
                                System.out.println("Ведене не вірне значення - спробуйте ще");
                                break;
                        }
                    } while (choiceUser != EXIT_USER_MENU);
                    break;
                default:
                    System.out.println("Ведене не вірне значення - спробуйте ще");
                    break;
            }
        } while (choice != EXIT_MAIN_MENU);
    }
}
