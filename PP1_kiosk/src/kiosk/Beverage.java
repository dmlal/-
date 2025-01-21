package kiosk;

import java.util.ArrayList;

public class Beverage  extends Menu{


    private int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Beverage(String name, int price, String desc) {
        super(name, desc);
        this.price = price;
    }

    @Override
    public String toString() {
        return new String(name + "  " + price + "원" + "   " + desc);
    }

}
