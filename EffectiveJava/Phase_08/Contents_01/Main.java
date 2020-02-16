package Phase_08.Contents_01;

/* ----------------------------------------------

8장. 메서드

(49) < 매개변수가 유효한지 검사하라 >
(50) < 적시에 방어적 복사본을 만들라 >

 ---------------------------------------------- */

import java.util.Date;
import java.util.Objects;

public class Main {

    public static void main(String[] args) throws Exception {

        // 메서드와 생성자 대부분은 입력 매개변수의 값이 특정 조건을 만족하기를 원한다.
        // 그러한 제약들은 반드시 문서화 되어야 하며, 메서드가 수행되기 전에 검사해야 한다.
        // 메서드 몸체가 실행되기 전에 매개변수를 확인한다면 잘못된 값이 넘어 오더라도 즉각적으로 예외를 처리할 수 있다.
        int i = stringToInteger(null);
        int j = stringToInteger(50);

        // 모든 클라이언트가 자신의 불변식을 깨뜨릴 수 있다는 가정 하에 방어적으로 프로그래밍 하는 것이 필요하다.
        // A. 생성자 매개변수 변경
        Date start = new Date();
        Date end = new Date();
        PeriodExample p = new PeriodExample(start, end);

        end.setTime(78);        // p 의 내부를 수정한 코드

        // 해결 방법
        // a. Date 대신 불변 인스턴스를 사용한다.
        // b. 그러지 못할 경우 생성자에서 받은 가변 매개변수 각각을 방어적으로 복사해야 한다.
        PeriodExample2 p2 = new PeriodExample2(start, end);

        start.setTime(78);

        // B. 인스턴스의 가변 필드 변경
        p.end().setTime(78);

        // 해결 방법
        // 필드의 방어적 복사본 반환
        p2.start().setTime(78);

        // 결론 - 클래스가 클라이언트로부터 받거나 반환하는 구성요소가 가변이라면 그 요소는 반드시 방어적으로 복사되어야 한다.
        // 단, 복사 비용이 너무 크거나 클라이언트를 신뢰할 수 있다면, 문서를 통해 불변이 깨지는 책임이 클라이언트에게 있다는 사실을 명시하자
    }

    private static int stringToInteger(Object o) throws Exception {
        o = Objects.requireNonNull(o);                          // null 검사
        if (!(o instanceof String)) throw new Exception();      // 타입 검사

        String s = (String) o;

        return Integer.parseInt(s);
    }
}
