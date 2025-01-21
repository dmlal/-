package kiosk;

public class Menu {
    protected String name;
    protected String desc;

    private int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Menu(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }
    public Menu(){}
    public static void printMenu() {
        System.out.println(" \n 동대문 엽기떡볶이에 오신걸 환영합니다.");
        System.out.println(" 아래의 번호 중 원하시는 번호를 입력해주세요.");
        System.out.println();
        System.out.println("    1. 메인메뉴      떡볶이입니다.");
        System.out.println("    2. 사이드 메뉴   떡볶이와 같이 먹기 좋은 메뉴");
        System.out.println("    3. 음료         매우면 마셔요");
        System.out.println();
        System.out.println("    5. 주문목록     담아두신 메뉴를 확인합니다.");
        System.out.println("    6. 취소        주문을 종료합니다.");
        System.out.println("    0. ");
        System.out.println();

    }
}
