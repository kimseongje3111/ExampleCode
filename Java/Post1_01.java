public class Post1_01 {

    public static void main(String[] args) {
        Integer inmmutable1 = 10;

        System.out.println("Before Function Call (Value): " + inmmutable1);
        System.out.println("Before Function Call (Address): " + inmmutable1.hashCode());
        System.out.println("--------------In Function--------------");

        IncreaseIntegerValue(inmmutable1);

        System.out.println("--------------Out Function--------------");
        System.out.println("After Function Call (Value): " + inmmutable1);
        System.out.println("After Function Call (Address): " + inmmutable1.hashCode());

        System.out.println("----------------------------------------");

        String inmmutable2 = "hello";
        String constantPool = "hello";

        System.out.println("Before Change Value (Address): " + inmmutable2.hashCode());     // 객체의 주소값을 이용해 해시코드 생성
        System.out.println("Before Change Value (Constant): " + constantPool.hashCode());   // 상수 풀의 존재 확인 (값이 중복이면 기존의 값을 사용)

        inmmutable2 = "bye";
        constantPool = "bye";

        System.out.println("After Change Value (Address): " + inmmutable2.hashCode());
        System.out.println("Before Change Value (Constant): " + constantPool.hashCode());

    }

    private static void IncreaseIntegerValue(Integer parm) {
        System.out.println("Before Calculation (Parmeter Address):" + parm.hashCode());

        parm += 20;

        System.out.println("After Calculation (Parmeter Address):" + parm.hashCode());
    }
}
