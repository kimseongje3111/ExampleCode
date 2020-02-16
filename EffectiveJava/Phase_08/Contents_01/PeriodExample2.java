package Phase_08.Contents_01;

import java.util.Date;

// 수정된 클래스 - 매개변수의 방어적 복사

public class PeriodExample2 {
    private final Date start;
    private final Date end;

    /**
     * @param start 시작 시각
     * @param end 종료 시각; 시작 시각보다 뒤여야 한다.
     * @throws IllegalArgumentException 시작 시각이 종료 시각보다 늦을 떄 발생한다.
     * @throws NullPointerException start 나 end 가 null 이면 발생한다.
     */

    public PeriodExample2(Date start, Date end) {        // Date 가변 매개변수에 대한 방어적 복사
        // Date 의 clone 메서드를 사용하지 않은 것은 Date 가 final 이 아니므로 clone 메서드가 Date 가 아닌 하위 클래스 인스턴스를 반환할 가능성이 있음
        // 즉, 매개변수가 제 3자에 의해 확장될 수 있는 타입이라면 방어적 복사본을 만들 때, clone 을 사용해서는 안 된다.
        this.start = new Date(start.getTime());
        this.end = new Date(end.getTime());

        // 복사 후 유효성 검사를 하는 이유는 멀티 스레드 환경에서 유효성 검사를 하고 복사를 하는 그 순간에 객체를 수정할 위험이 있기 때문
        if (this.start.compareTo(end) > 0) throw new IllegalArgumentException(this.start + "가" + this.end + "보다 늦다.");
    }

    // 필드의 방어적 복사
    // 이 접근자 메서드 같은 경우는 PeriodExample2 가 가지고 있는 Date 객체가 java.util.Date 임이 확실하기 때문에 clone 사용이 가능하다.
    // 하지만 일반적으로 인스터를 복사하는 데는 생성자나 정적 팩터리를 쓰는 편이 좋다.
    public Date start() {
        return new Date(start.getTime());
    }

    public Date end() {
        return new Date(end.getTime());
    }
}
