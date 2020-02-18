package Phase_08.Contents_02;

import java.util.Collection;
import java.util.List;
import java.util.Set;

// 다중정의

public class CollectionClassifier {

    // 다중정의(오버로딩)된 세 classify 메서드는 어떤 메서드를 호출할지 컴파일 시점에 결정된다.

    public static String classify(Set<?> s) {
        return "집합";
    }

    public static String classify(List<?> l) {
        return "리스트";
    }

    public static String classify(Collection<?> c) {
        return "그 외";
    }

    public static String realClassify(Collection<?> c) {
        return c instanceof Set ? "집합" : c instanceof List ? "리스트" : "그 외";
    }
}
