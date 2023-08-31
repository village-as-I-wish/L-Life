package kosa.com.suntofu.L_LIFE.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MoodFilter {

    MODERN(1, "모던"),
    MINIMAL_SIMPLE(2, "미니멀 & 심플"),
    NATURAL(3,"내추럴"),
    NORTH_EU(4,"북유럽"),
    RETRO(5, "빈티지 & 레트로");

    private final int id;
    private final String name;
}
