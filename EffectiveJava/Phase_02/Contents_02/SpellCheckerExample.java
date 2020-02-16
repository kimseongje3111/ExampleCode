package Phase_02.Contents_02;

// 정적 유틸리티, 싱글턴을 이용한 잘못된 예와 효율적인 의존 객체 주입 방식

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SpellCheckerExample {
    /*  ---------------- 정적 유틸리티 방식 ----------------
        private static final Lexcion dictionary = ...;

        private SpellCheckerExample() {}
    */

    /*  ------------------- 싱글턴 방식 --------------------
        private final Lexcion dictionary = ...;

        private SpellCheckerExample(...) {}
        public static SpellCheckerExample INSTANCE = new SpellCheckerExample(...);
    */

    // 의존 객체 주입 방식
    private final Lexcion dictionary;

    public SpellCheckerExample(Lexcion dictionary) {
        this.dictionary = Objects.requireNonNull(dictionary);
    }

    public boolean isValid(String word) {
        boolean result = false;
        return result;
    }

    public List<String> suggestions(String typo) {
        List<String> results = new ArrayList<>();
        return results;
    }
}

enum Lexcion {     // 사전
    KOREAN, ENGLISH, SPANISH;
}
