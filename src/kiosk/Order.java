package kiosk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static kiosk.main.choiceMenu;

public class Order extends OrderResult{
    public void pickMenu(ArrayList<? extends Menu> menuList) {
        Scanner sc = new Scanner(System.in);
        int inputChoice = sc.nextInt();
        int choice = inputChoice - 1;
        sc.nextLine();
        switch (choice) {
            case 0:
                System.out.println(menuList.get(choice));
                System.out.println("     주문묵록에 담으시겠습니까?");
                System.out.println("       1.네    2.아니오");
                int pick = sc.nextInt();
                sc.nextLine();
                if (pick == 1) {
                    System.out.println("주문목록에 담았습니다.");
                    addOrder(menuList.get(choice), 1);
                } else {
                    System.out.println("취소되었습니다.");
                }
                break;

            case 1:
                System.out.println(menuList.get(choice));
                System.out.println("     주문묵록에 담으시겠습니까?");
                System.out.println("       1.네    2.아니오");
                int pick2 = sc.nextInt();
                sc.nextLine();
                if (pick2 == 1) {
                    System.out.println("주문목록에 담았습니다.");
                    addOrder(menuList.get(choice), 1);
                } else {
                    System.out.println("취소되었습니다.");
                }
                break;

            case 2:
                System.out.println(menuList.get(choice));
                System.out.println("     주문묵록에 담으시겠습니까?");
                System.out.println("        1.네    2.아니오");
                int pick3 = sc.nextInt();
                sc.nextLine();
                if (pick3 == 1) {
                    System.out.println("주문목록에 담았습니다.");
                    addOrder(menuList.get(choice), 1);
                } else {
                    System.out.println("취소되었습니다.");
                }
                break;

        }
    }

    public void plusMenu(ArrayList<? extends Menu> menu) {
        Scanner sc = new Scanner(System.in);
        int inputChoice = sc.nextInt();
        int choice = inputChoice - 1;
        sc.nextLine();
        switch (choice) {
            case 0:
                System.out.println(menu.get(choice));
                System.out.println("     주문묵록에 담으시겠습니까?");
                System.out.println("       1.네    2.아니오");
                int pick = sc.nextInt();
                sc.nextLine();
                if (pick == 1) {
                    System.out.println("주문목록에 담았습니다.");
                    addOrder(menu.get(choice), 1);
                } else {
                    System.out.println("취소되었습니다.");
                }
                break;

            case 1:
                System.out.println(menu.get(choice));
                System.out.println("     주문묵록에 담으시겠습니까?");
                System.out.println("       1.네    2.아니오");
                int pick2 = sc.nextInt();
                sc.nextLine();
                if (pick2 == 1) {
                    System.out.println("주문목록에 담았습니다.");
                    addOrder(menu.get(choice), 1);
                } else {
                    System.out.println("취소되었습니다.");
                }
                break;

            case 2:
                System.out.println(menu.get(choice));
                System.out.println("     주문묵록에 담으시겠습니까?");
                System.out.println("        1.네    2.아니오");
                int pick3 = sc.nextInt();
                sc.nextLine();
                if (pick3 == 1) {
                    System.out.println("주문목록에 담았습니다.");
                    addOrder(menu.get(choice), 1);
                } else {
                    System.out.println("취소되었습니다.");
                }
                break;

            case 4:
                break;

        }
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

            System.out.println(menu.getName() +"  "+ dishNum+ "개");
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

    public void clearOrder() {
        orderMap.clear();
    }

    int orderNumber = 1;
    public void endOrder(){

        if (!orderMap.isEmpty()) {
            System.out.println(orderNumber+"번 주문이 완료되었습니다.");
            orderMap.clear();
        }else if(orderMap.isEmpty()){
            System.out.println("주문 목록이 없습니다.");
            return;
        }
        orderNumber++;

        try{
            Thread.sleep(2700);
        }catch (InterruptedException e){
            System.out.println("메인메뉴로 돌아갑니다.");
        }
    }
    public void cancelOrder(){
        orderMap.clear();
    }

}
