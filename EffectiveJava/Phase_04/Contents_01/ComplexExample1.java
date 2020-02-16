package Phase_04.Contents_01;

// 불변 클래스 (완전하지 않은 복소수 사칙연산 클래스)

public final class ComplexExample1 {
    private final double re;
    private final double im;

    // 자주 사용하는 불변 객체는 재활용하자 - 상수로 제공
    public static final ComplexExample1 ZERO = new ComplexExample1(0, 0);
    public static final ComplexExample1 ONE = new ComplexExample1(1, 0);
    public static final ComplexExample1 I = new ComplexExample1(0, 1);

    public ComplexExample1(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public double realPart() {
        return re;
    }

    public double imaginaryPart() {
        return im;
    }

    // 아래의 사칙연산 메서드들은 자신의 인스턴스 값을 수정하는 것이 아니라 새로운 인스턴스를 리턴한다.
    // - 불변이 되는 영역의 비율이 증가하는 효과
    // 만일 모든 생성자가 클래스 불변식을 보장한다면 그 클래스를 사용하는 프로그래머 입장에서는 영원히 불변이다.

    public ComplexExample1 plus(ComplexExample1 c) {
        return new ComplexExample1(re + c.re, im + c.im);
    }

    public ComplexExample1 minus(ComplexExample1 c) {
        return new ComplexExample1(re - c.re, im - c.im);
    }

    public ComplexExample1 times(ComplexExample1 c) {
        return new ComplexExample1(re * c.re - im * c.im, im * c.im + im * c.im);
    }

    public ComplexExample1 divideBy(ComplexExample1 c) {
        double temp = c.re * c.re + c.im * c.im;

        return new ComplexExample1((re * c.re + im * c.im) / temp, (im * c.re - re * c.im) / temp);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof ComplexExample1)) return false;

        ComplexExample1 c = (ComplexExample1) obj;

        return (Double.compare(c.re, this.re) == 0) && (Double.compare(c.im, this.im) == 0);
    }

    @Override
    public int hashCode() {
        return 31 * Double.hashCode(re) + Double.hashCode(im);
    }

    @Override
    public String toString() {
        return "(" + re + " + " + im + "i)";
    }
}
