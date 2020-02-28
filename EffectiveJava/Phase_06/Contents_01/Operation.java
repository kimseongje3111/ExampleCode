package Phase_06.Contents_01;

// 열거 타입 상수별 메서드 구현 - 사칙연산
// 추가적으로 상수별 클래스 몸체와 데이터를 함께 사용하여 기능을 확장할 수 있다.

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public enum Operation {
    PLUS("+") {
        @Override
        public double apply(double x, double y) {
            return x + y;
        }
    },
    MINUS("-") {
        @Override
        public double apply(double x, double y) {
            return x - y;
        }
    },
    TIMES("*") {
        @Override
        public double apply(double x, double y) {
            return x * y;
        }
    },
    DIVIDE("/") {
        @Override
        public double apply(double x, double y) {
            return x / y;
        }
    };

    private final String symbol;

    private static final Map<String, Operation> stringToEnum = Stream.of(values()).collect(toMap(Objects::toString, e -> e));
    // 열거 타입의 정적 필드 중 열거 타입의 생성자에서 접근할 수 있는 것은 오직 상수 변수뿐이다.
    // 그렇기 때문에 생성자에서 자신의 인스턴스를 맵에 추가할 수 없고, Operation 상수가 맵에 추가되는 시점은 열거 타입 상수 생성 후 정적 필드가 초기화 일 때 뿐이다.

    Operation(String symbol) {
        this.symbol = symbol;
    }

    public String symbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }

    public static Optional<Operation> fromString(String symbol) {       // toString 문자열을 받아 열거 타입 상수를 반환
        return Optional.ofNullable(stringToEnum.get(symbol));
    }

    public abstract double apply(double x, double y);       // 추상 메서드이기 때문에 재정의하지 않으면 컴파일 오류
}
