package kiosk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static kiosk.main.choiceMenu;

public class Order extends OrderResult {
    private void addOrderMenu(ArrayList<? extends Menu> menuList, Scanner sc, int choice) {
        System.out.println(menuList.get(choice));
        System.out.println("     주문목록에 담으시겠습니까?");
        System.out.println("       1.네    2.아니오");
        int chooseMenu = sc.nextInt();
        sc.nextLine();
        if (chooseMenu == 1) {
            System.out.println("주문목록에 담았습니다.");
            addOrder(menuList.get(choice), 1);
        } else {
            System.out.println("취소되었습니다.");

        }
    }

    public void pickMenu(ArrayList<? extends Menu> menuList) {
        Scanner sc = new Scanner(System.in);
        int inputChoice = sc.nextInt();
        int choice = inputChoice - 1;
        sc.nextLine();
        if (choice < 0 || choice > 2) {
            return;
        }
        addOrderMenu(menuList, sc, choice);
    }

    
    public void pickMainMenu(ArrayList<? extends Menu> menuList) {
        Scanner sc = new Scanner(System.in);
        int inputChoice = sc.nextInt();
        int choice = inputChoice - 1;
        sc.nextLine();
        if (choice < 0 || choice > 2) {
            return;
        }
        addOrderMainMenu(menuList, sc, choice);
    }

    private void addOrderMainMenu(ArrayList<? extends Menu> menuList, Scanner sc, int choice) {
        System.out.println(menuList.get(choice));
        System.out.println("     주문목록에 담으시겠습니까?");
        System.out.println("       1.네    2.아니오");
        int chooseMenu = sc.nextInt();
        sc.nextLine();
        if (chooseMenu == 1) {
            System.out.println("주문목록에 담았습니다.");
            addOrder(menuList.get(choice), 1);
            System.out.println("\n선택하신 상품에 메뉴를 추가하시겠습니까?");
            System.out.println("원하시는 상품이 없다면 5번을 눌러주세요\n");
            choiceMenu(main.topping);
            plusMenu(main.topping);
        } else {
            System.out.println("취소되었습니다.");

        }
    }

    public void plusMenu(ArrayList<? extends Menu> menuList) {
        Scanner sc = new Scanner(System.in);
        int inputChoice = sc.nextInt();
        int choice = inputChoice - 1;
        sc.nextLine();

        if (choice == 4) {
            Menu.printMenu();
            return;
        }
        if (choice < 0 || choice > 2) {
            return;
        }
        addOrderMenu(menuList, sc, choice);
    }

    private Map<Menu, Integer> orderMap = new HashMap<>();

    public void addOrder(Menu menu, int num) {
        if (orderMap.containsKey(menu)) {
            int menuNum = orderMap.get(menu);
            orderMap.put(menu, menuNum + num);
        } else {
            orderMap.put(menu, num);
        }
        System.out.println(menu.getName() + "  " + num + "개");
    }

    public void printOrder() {
        System.out.println(" 주문목록입니다. ");
        int totalPrice = 0;

        for (Map.Entry<Menu, Integer> entry : orderMap.entrySet()) {
            Menu menu = entry.getKey();
            int dishNum = entry.getValue();
            int menuTotalPrice = dishNum * menu.getPrice();

            System.out.println(menu.getName() + "  " + dishNum + "개");
            totalPrice += menuTotalPrice;
        }
        System.out.println("총 금액은 " + totalPrice + " 원 입니다.");
        System.out.println("     \n주문하시겠습니까?");
        System.out.println("    1.주문하기    2.돌아가기");
        Scanner sc = new Scanner(System.in);
        int orderOk = sc.nextInt();
        sc.nextLine();
        if (orderOk == 1) {
            for (Map.Entry<Menu, Integer> entry : orderMap.entrySet()) {
                Menu menu = entry.getKey();
                OrderResult.setSelledMenu(menu);
            }
            setTotalSales(totalPrice);
            endOrder();
        } else if (orderOk == 2) {
            Menu.printMenu();
        }
    }


    int orderNumber = 1;

    public void endOrder() {

        if (!orderMap.isEmpty()) {
            System.out.println(orderNumber + "번 주문이 완료되었습니다.");
            orderMap.clear();
        } else if (orderMap.isEmpty()) {
            System.out.println("주문 목록이 없습니다.");
            return;
        }
        orderNumber++;

        try {
            Thread.sleep(3 * 1000);
        } catch (InterruptedException e) {
            System.out.println("메인메뉴로 돌아갑니다.");
        }
    }

    public void cancelOrder() {
        orderMap.clear();
    }

}