package Phase_05.Contents_06;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// 타입 안전 이종 컨테이너 패턴

public class Favorites {
    private Map<Class<?>, Object> favorites = new HashMap<>();

    // 각 타입의 Class 객체를 매개변수화한 키 역할로 사용한다.
    // 이것이 가능한 이유는 class 의 클래스가 제네릭이기 때문이다.
    // class 리터럴 타입은 Class<T> 이다.
    // 예를 들어 String.class 의 타입은 Class<String> 이다.
    // 이처럼 컴파일러 타임 또는 런타임의 타입 정보를 알아내기 위해 메서드들이 주고 받는 class 리터럴 타입을 '토큰'이라고 한다.

    public <T> void putFavorite(Class<T> type, T instance) {
        favorites.put(Objects.requireNonNull(type), instance);
        // HashMap 값의 타입이 단순히 Object 이라는 것은 키와 값의 타입 관계를 보증하지 않음을 뜻한다.
        // 즉, 모든 값이 키로 명시한 타입임을 보증하지 않는다.
        // 자바에서는 이 관계(모든 값이 키로 명시한 타입)를 명시할 방법은 없지만 우리는 이 관계가 성립함을 알고 있다.
    }

    public <T> T getFavorite(Class<T> type) {
        return type.cast(favorites.get(type));
        // Class 의 cast 메서드를 통해 이 객체 참조를 Class 객체가 가리키는 타입으로 동적 형변환한다.
        // 이는 앞서 put 메서드에서 명시하지 못한 관계를 다시 살리는 행위이다.
        // cast 메서드가 반환하는 타입은 Class 의 T 타입과 같기 때문에 T 로 비검사 형변환하는 손실 없이 타입 안전을 보장한다.
    }
}
