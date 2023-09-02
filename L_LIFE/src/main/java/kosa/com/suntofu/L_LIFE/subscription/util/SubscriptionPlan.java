package kosa.com.suntofu.L_LIFE.subscription.util;

public enum SubscriptionPlan {
    Standard33(1, "L-life 스탠다드 33 구독권", 33000, 5),
    Standard55(2, "L-life 스탠다드 55 구독권", 55000, 10);

    private final Integer id;
    private final String name; // 구독 이름
    private final Integer price;   // 가격

    private final Integer coinNum;

    SubscriptionPlan(Integer id, String name, int price, int coinNum) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.coinNum = coinNum;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getCoinNum() {
        return coinNum;
    }
}