package kiosk;

public class PlusMenu extends Menu{

    private int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public PlusMenu(String name, int price, String desc) {
        super(name, desc);
        this.price = price;
    }



    @Override
    public String toString(){
        return new String(super.name + "  "+price+ "원" +"   "+super.desc);
    }
}
