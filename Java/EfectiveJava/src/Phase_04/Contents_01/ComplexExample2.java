package Phase_04.Contents_01;

// 생성자 대신 정적 팩터를 사용한 불변 클래스 (더 유연한 방법)

public class ComplexExample2 {
    private final double re;
    private final double im;

    // 자주 사용하는 불변 객체는 재활용하자 - 상수로 제공
    public static final ComplexExample2 ZERO = new ComplexExample2(0, 0);
    public static final ComplexExample2 ONE = new ComplexExample2(1, 0);
    public static final ComplexExample2 I = new ComplexExample2(0, 1);

    private ComplexExample2(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public static ComplexExample2 valueOf(double re, double im) {
        return new ComplexExample2(re, im);
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

    public ComplexExample2 plus(ComplexExample2 c) {
        return new ComplexExample2(re + c.re, im + c.im);
    }

    public ComplexExample2 minus(ComplexExample2 c) {
        return new ComplexExample2(re - c.re, im - c.im);
    }

    public ComplexExample2 times(ComplexExample2 c) {
        return new ComplexExample2(re * c.re - im * c.im, im * c.im + im * c.im);
    }

    public ComplexExample2 divideBy(ComplexExample2 c) {
        double temp = c.re * c.re + c.im * c.im;

        return new ComplexExample2((re * c.re + im * c.im) / temp, (im * c.re - re * c.im) / temp);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof ComplexExample2)) return false;

        ComplexExample2 c = (ComplexExample2) obj;

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
