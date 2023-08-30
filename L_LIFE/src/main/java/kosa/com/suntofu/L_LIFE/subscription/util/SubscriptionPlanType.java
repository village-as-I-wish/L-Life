package kosa.com.suntofu.L_LIFE.subscription.util;

public enum SubscriptionPlanType {
    Standard33(0, "L-life 스탠다드 33 구독권", 33000),
    Standard55(1, "L-life 스탠다드 55 구독권", 55000);

    private final Integer type; // 구독 타입
    private final String name; // 구독 이름
    private final Integer price;   // 가격

    SubscriptionPlanType(Integer type, String name, int price) {
        this.type = type;
        this.name = name;
        this.price = price;
    }

    public Integer getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }
}