package kosa.com.suntofu.L_LIFE.community.util;

public enum Style {
    MODERN("modern",1),

    CASUAL("casual", 2),
    NATURAL("natural",3 ),
    CLASSIC("classic",4 ),
    ROMANTIC("romantic",5),
    VINTAGE("vintage",6),
    NORDIC("nordic", 7 );



    private final String value;

    private final int num;

    Style(String value, int num ) {
        this.value = value;
        this.num = num;
    }

    public String getValue() {
        return value;
    }

    public int getNum(){
        return num;
    }

    // 이름값으로 Enum 객체 생성하는 메소드
    public static Style fromValue(String value) {
        for (Style style : values()) {
            if (style.value.equalsIgnoreCase(value)) { // 대소문자 무시
                return style;
            }
        }
        throw new IllegalArgumentException("No such enum constant for value: " + value);
    }
}
