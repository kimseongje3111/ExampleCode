package Phase_02.Contents_03;

/* ----------------------------------------------

2장. 객체 생성과 파괴

(6) < 불필요한 객체 생성을 피하라 >
(7) < 다 쓴 객체 참조를 해제하라 >

 --------------------------------------------- */

import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {

        // A. 불필요한 객체
        // 불필요한 객체 생성 예 (1) - 문자열 상수 풀을 이용하지 않는 경우
        String unNecessary = new String("Bikini");      // 이 문장이 여러번 호출되는 메서드 안에 존재한다면 쓸데없는 String 객체를 만들게 됨

        // 불필요한 객체 생성 예 (2) - 생성 비용이 아주 비싼 객체
        // 해당 객체가 생성 비용이 비싸다는 것을 매번 알 수 없으나, 이런 객체는 캐싱하여 재사용하는 것이 효율적
        boolean result = isRomanNumeral("123456789");

        // 하지만 이 방법은 성능이 중요한 상황에서 반복해 사용하기 적합하지 않다.
        // 해당 메서드 호출시 내부에서 만들어지는 정규표현식용 Pattern 인스턴스는 '유한 상태 머신'을 만들기 때문에 생성 비용이 비싸다.
        // 때문에 여러번 사용하기 위해서 Pattern 인스턴스를 클래스 초기화시 직접 생성 및 캐싱해두고 재사용하는 것이 바람직하다.
        boolean result2 = RomanNumerals.isRomanNumeral("123456789");

        // 불필요한 객체 생성 예 (3) - 오토박싱(Auto Boxing)
        // 기본 타입과 박싱된 기본 타입을 섞어 쓸 때, 자동으로 상호 변환해주는 기술
        // 되도록 박싱된 기본 타입보다 기본 타입을 사용하고, 의도하지 않은 오토박싱을 주의하자
        Long result3 = sum();

        // 결론 - 객체 생성을 피해야 한다는 것이 아니라 불필요한 객체 생성을 주의하자
        // 일반적으로 프로그램의 명확성, 간결성, 기능을 위해 객체를 생성하는 것은 좋은 일이며, GC의 성능이 좋기 때문에 일반적인 객체 생성 및 회수 비용을 걱정하지 말자

        // B. 메모리 누수
        // 다 쓴 객체를 참조 해제 않을 경우 발생하는 문제 - 메모리 누수
        // 이 스택에 객체를 추가하고 제거하는 과정에서 꺼내진 객체들은 GC 가 회수하지 않음 (활성 영역이 존재)
        Stack stack = new Stack();
        stack.push("Stack");
        stack.pop();

        // 다 쓴 객체는 참조를 해제한다. (만일 그 객체를 참조할지라도 NullPointerException 발생)
        stack.push("Stack");
        stack.popAndSwap();

        // 하지만 null 처리는 예외적인 경우에 해야한다.
        // 가장 좋은 방법은 해당 참조를 담은 변수를 유효 범위(Scope) 밖으로 밀어내는 것 !
        // 해당 객체가 더 이상 쓰이지 않을 것이라는 것은 프로그래머가 알고 있기 때문에 모든 객체를 다 쓴 즉시 참조를 해제하는 것이 아니라 비활성 영역이 되는 순간 null 처리를 하자

        // 메모리를 직접 관리하는 클래스는 메모리 누수를 주의하자 (되도록 객체 사용이 끝나면 참조를 해제하는 것이 좋다)
        // 캐시, 리스너 및 콜백 메서드 역시 메모리 누수의 주범
    }

    private static Long sum() {
        Long sum = 0L;      // 박싱된 기본 타입으로 인한 불필요한 객체 생성

        for (long i = 0; i <= Integer.MAX_VALUE; i++) {
            sum += i;
        }

        return sum;
    }

    private static boolean isRomanNumeral(String input) {       // String.matches() 과 정규표현식을 이용하여 문자열 형태 확인
        return input.matches("^(?=.)M*(C[MD]|D?C{0,3})(X[CL]|L?X{0,3})(I[XV]|V?I{0,3})$");
    }
}

