package Phase_06.Contents_01;

// 전략 열거 타입 패턴 - 직원 수당 계산

import static Phase_06.Contents_01.PayrollDay.PayType.WEEKDAY;
import static Phase_06.Contents_01.PayrollDay.PayType.WEEKEND;

public enum PayrollDay {
    // 새로운 상수를 추가할 때, 잔업수당 '전략'을 선택하도록 하자
    // 잔업수당 계산을 private 중첩 열거 타입(PayType)으로 옮기고, 원래 열거 타입(PayrollDay)의 생성자에서 적당한 것을 선택하도록 한다.
    // 결과적으로 switch 문이나 상수별 메서드 구현없이 더 안전하고 유연한 코드를 작성할 수 있다.

    MONDAY(WEEKDAY), TUESDAY(WEEKDAY), WEDNESDAY(WEEKDAY), THURSDAY(WEEKDAY),
    FRIDAY(WEEKDAY), SATURDAY(WEEKEND), SUNDAY(WEEKEND);

    // 만일 열거 타입에 공휴일을 추가하여도 문제가 발생하지 않는다.

    private final PayType payType;

    PayrollDay(PayType payType) {
        this.payType = payType;
    }

    int pay(int minutesWorked, int payRate) {
        return payType.pay(minutesWorked, payRate);
    }

    enum PayType {      // 잔업수당 계산을 위임받은 전략 열거 타입
        WEEKDAY {
            @Override
            int overtimePay(int minsWorked, int payRate) {
                return minsWorked <= MINS_PER_SHIFT ? 0 : (minsWorked - MINS_PER_SHIFT) * payRate / 2;
            }
        },
        WEEKEND {
            @Override
            int overtimePay(int minsWorked, int payRate) {
                return minsWorked * payRate / 2;
            }
        };

        private static final int MINS_PER_SHIFT = 8 * 60;

        int pay(int minutesWorked, int payRate) {
            int basePay = minutesWorked * payRate;
            return basePay + overtimePay(minutesWorked, payRate);
        }

        abstract int overtimePay(int minsWorked, int payRate);
    }
}
