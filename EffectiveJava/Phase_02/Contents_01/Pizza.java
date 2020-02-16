package Phase_02.Contents_01;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

// 빌더 패턴과 계층 구조 클래스

public abstract class Pizza {
    public enum Topping {HAM, MUSHROOM, ONION, PEPPER, SAUSAGE}

    final Set<Topping> toppings;                                        // 추가된 토핑 리스트

    abstract static class Builder<T extends Builder<T>> {
        EnumSet<Topping> toppings = EnumSet.noneOf(Topping.class);      // 정적 메서드 팩토리

        public T addTopping(Topping topping) {
            toppings.add(Objects.requireNonNull(topping));
            return self();
        }

        abstract Pizza build();

        // Pizza 클래스를 상속받는 하위 클래스들은 이 메서드를 재정의하여 'this'를 반환한다.
        protected abstract T self();
    }

    Pizza(Builder<?> builder) {
        toppings = builder.toppings.clone();        // 불변을 위한 방어
    }
}
