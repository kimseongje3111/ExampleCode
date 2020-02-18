package Phase_08.Contents_03;

import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

// 옵셔널 예시

public class OptionalExample {

    // 컬렉션에서 최대 값을 구하는 메서드로 컬렉션이 비었다면 예외를 던진다.
    public static <E extends Comparable<E>> E max(Collection<E> c) {
        if (c.isEmpty()) throw new IllegalArgumentException("빈 컬렉션");

        E result = null;

        for(E e : c) {
            if (result == null || e.compareTo(result) > 0) {
                result = Objects.requireNonNull(e);
            }
        }

        return result;
    }

    // 위와 같은 동작하는 메서드를 옵셔널을 반환하는 방식으로 변경
    public static <E extends Comparable<E>> Optional<E> max2(Collection<E> c) {
        if (c.isEmpty()) return Optional.empty();

        E result = null;

        for(E e : c) {
            if (result == null || e.compareTo(result) > 0) {
                result = Objects.requireNonNull(e);
            }
        }

        return Optional.of(result);
    }

    // 스트림 버전
    public static <E extends Comparable<E>> Optional<E> max3(Collection<E> c) {
        return c.stream().max(Comparator.naturalOrder());
    }
}
