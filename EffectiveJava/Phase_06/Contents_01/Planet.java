package Phase_06.Contents_01;

// 열거 타입 클래스 - 태양계

public enum Planet {

    // 열거 타입 상수 가각을 특정 데이터와 연결짓기 위해서는 생성자를 이용하면 된다.
    MERCURY(3.302e+23, 2.439e6),
    VENUS(4.869e+24, 6.052e6),
    EARTH(5.975e+24, 6.378e6),
    MARS(6.419e+23, 3.393e6),
    JUPITER(1.899e+27, 7.149e7),
    SATURN(5.686e+26, 6.027e7),
    URANUS(8.683e+25, 2.556e7),
    NEPTUNE(1.024e+26, 2.477e7);

    private final double mass;              // 질량
    private final double radius;            // 반지름
    private final double surfaceGravity;    // 표면중력

    private static final double G = 6.67300E-11;    // 중력상수

    Planet(double mass, double radius) {
        this.mass = mass;
        this.radius = radius;
        surfaceGravity = G * mass / (radius * radius);
    }

    public double mass() {
        return mass;
    }

    public double radius() {
        return radius;
    }

    public double surfaceGravity() {
        return surfaceGravity;
    }

    public double surfaceWeight(double mass) {
        return mass * surfaceGravity;       // F = ma
    }
}
