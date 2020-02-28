package Phase_06.Contents_01;

/* ----------------------------------------------

6장. 열거 타입과 애너테이션

(34) < int 상수 대신 열거 타입을 사용하라 >
(35) < ordinal 메서드 대신 인스턴스 필드를 사용하라 >

 ---------------------------------------------- */

public class Main {

    public static void main(String[] args) {

        // 자바의 열거 타입은 완전한 형태의 클래스이기 때문에 강력하다.
        // 열거 타입 자체는 클래스이며, 상수 하나당 자신의 인스턴스를 하나씩 만들어 public static final 필드로 공개한다.
        // 클라이언트 입장에서는 열거 타입 클래스 인스턴스를 직접 생성하거나 확장 할 수 없기 때문에 이는 각 인스턴스가 하나씩만 존재함을 보장한다.

        // A. 열거 타입은 컴파일 타임 타입 안정성을 제공한다.
        // B. 열거 타입에는 각자의 이름 공간이 존재해서 같은 이름의 상수도 공존할 수 있다.
        // C. 열거 타입에 새로운 상수를 추가하거나 순서를 수정하여도 다시 컴파일을 할 필요가 없다.
        // D. 열거 타입의 toString 메서드는 출력하기에 적합한 문자열을 준다.
        // E. 열거 타입에는 임의의 메서드나 필드를 추가할 수 있고, 인터페이스도 구현할 수 있다.
        // F. 기본적으로 열거 타입 클래스는 추상 클래스이다.

        double earthWeight = Double.parseDouble("74");
        double mass = earthWeight / Planet.EARTH.surfaceGravity();

        for (Planet p : Planet.values()) System.out.printf("%s 에서의 무게는 %f 이다.%n", p, p.surfaceWeight(mass));

        // 만일 열거 타입의 상수별로 다르게 동작하는 코드를 구현하고 싶다면
        // 열거 타입에 추상 메서드를 선어하고 각 상수별 클래스 몸체(즉, 각 상수에서 자신에 맞게) 재정의하는 방법이 있다.
        // 이를 '상수별 메서드 구현'이라고 한다.

        double x = Double.parseDouble("5");
        double y = Double.parseDouble("2");

        for (Operation op : Operation.values()) System.out.printf("%f %s %f = %f%n", x, op, y, op.apply(x, y));

        // 추가적으로 열거 타입의 toString 메서드를 재정의 한다면 역으로 toString 문자열을 열거 타입 상수로 변환해주는 fromString 도 고려해보자
        for (Operation op : Operation.values()) System.out.println(Operation.fromString(op.symbol()));

        // 한편, 상수별 메서드 구현에는 열거 타입 상수끼리 코드를 공유하기 어렵다.
        // 값에 따라 분기하여 코드를 공유하는 방법은 간결하지만 관리 관점에서 위험하다.
        // 만일 새로운 값을 열거 타입에 추가하기 위해서는 분기문을 잊지 말고 넣어야 한다.
        // 이러한 불편함을 해결하기 위해 '전략 열거 타입 패턴'을 사용하자
        int payMonday = PayrollDay.MONDAY.pay(10 * 60, 2);
        int paySaturday = PayrollDay.SATURDAY.pay(5 * 60, 3);

        // 결과적으로 필요한 원소를 컴파일타임에 다 알 수 있는 상수 집합이라면 항상 열거 타입을 사용하자
        // 일반 정수형 상수보다 더 읽기 쉽고, 안전하며, 강력하다.
        // 만일 상수와 연결된 정수가 필요하다면 열거 타입에서 제공하는 ordinal 메서드가 아니라 직접 인스턴스 필드에 저장하자
    }
}
