package kosa.com.suntofu.L_LIFE.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BrandFilter {
    GLOBAL_LIVART_GALLERY(1, "Global Livart Gallery"),
    LIVART(2, "리바트"),
    LIVART_H(3,"리바트_H몬도"),
    FM_DESIGN(4,"FM 디자인"),
    LIVART_ONLINE(5, "리바트온라인");

    private final int id;
    private final String name;
}
