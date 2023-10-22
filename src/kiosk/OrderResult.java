package kiosk;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;


public class OrderResult {
    private static ArrayList<Menu> selledMenu= new ArrayList<>();



    private static int totalSales= 0;

    public static HashSet<String> getSelledMenu() {
        HashSet<String> selledMenuName = new HashSet<>();
        for (Menu menu : selledMenu) {
            selledMenuName.add(menu.getName()+ "         "+ menu.getPrice()+"원");
        }
        return selledMenuName;
    }



    public static void setSelledMenu(Menu menu) {
        selledMenu.add(menu);
    }

    public static int getTotalSales() {
        if (totalSales == 0){
            System.out.println("아직 주문이 없어요! ㅠㅠ");
        }else {
            return totalSales;
        }
        return 0;
    }

    public void setTotalSales(int sales) {
        totalSales +=sales;
    }
}
