package Phase_04.Contents_01;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// 불변 리스트를 사용하는 방법 (package-private 으로 정보 은닉)

public class InmmutableArray {

    // public 배열을 private 으로 변경
    private static final Thing[] PRIVATE_VALUES = {};

    // A. public 불변 리스트를 추가
    static final List<Thing> VALUES = Collections.unmodifiableList(Arrays.asList(PRIVATE_VALUES));

    // B. 방어적 복사
    static final Thing[] values() {
        return PRIVATE_VALUES.clone();
    }

    static class Thing {
    }
}
