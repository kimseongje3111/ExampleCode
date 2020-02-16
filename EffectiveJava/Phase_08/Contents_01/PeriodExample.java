package Phase_08.Contents_01;

// 불변식을 지키지 못하는 클래스

import java.util.Date;

public class PeriodExample {
    private final Date start;
    private final Date end;

    /**
     * @param start 시작 시각
     * @param end 종료 시각; 시작 시각보다 뒤여야 한다.
     * @throws IllegalArgumentException 시작 시각이 종료 시각보다 늦을 떄 발생한다.
     * @throws NullPointerException start 나 end 가 null 이면 발생한다.
     */

    public PeriodExample(Date start, Date end) {        // Date 가 가변이라는 사실을 이용하면 불변식을 깨드릴 수 있다..
        if (start.compareTo(end) > 0) throw new IllegalArgumentException(start + "가" + end + "보다 늦다.");

        this.start = start;
        this.end = end;
    }

    public Date start() {
        return start;
    }

    public Date end() {
        return end;
    }
}
