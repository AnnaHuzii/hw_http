package consoleMenu;

import lombok.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


@Data
public class Menu {
    private String name;
    private HashMap<Integer, String> contentMenu;

    public Menu(String name) {
        this.name = name;
    }

    public void printMenu () {
        if(name.equals("Main")) {System.out.println("\tВ даній системі ви можете працювати з наступними розділами:");}
        else {System.out.println("\tВ даному розділі ви зможете:");}
        for (Map.Entry<Integer, String> element : contentMenu.entrySet()) {
            System.out.println(element.getKey() + " - " + element.getValue());
        }
        System.out.print("Зробіть свій вибір (1,2,3 і т.д.) : ");
    }

    public int makeChoice() {
        int result = 0;
        boolean isRightChoice = true;
        Scanner sc = new Scanner(System.in);
        do {
            result = sc.nextInt();
            isRightChoice = checkChoice(result);
        } while (!isRightChoice);
        return result;
    }

    public boolean checkChoice(int actualChoice) {
        boolean result = false;
        for (Map.Entry<Integer, String> element : getContentMenu().entrySet()) {
            if (element.getKey() == actualChoice) {
                result = true;
            }
        }
        if (result) return result;
        else {
            System.out.print("\tВи зробили не правильний вибір, спробуйте ще :");
            return result;
        }
    }
}
