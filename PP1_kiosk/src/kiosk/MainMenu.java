package kiosk;

import java.util.ArrayList;

public class MainMenu extends Menu{


    private int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public MainMenu(String name, int price, String desc) {
        super(name, desc);
        this.price = price;
    }



    @Override
    public String toString(){
        return new String(super.name + "  "+price+ "원" +"   "+super.desc);
    }
}


