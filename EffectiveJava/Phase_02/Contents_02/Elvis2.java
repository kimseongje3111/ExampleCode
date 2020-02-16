package Phase_02.Contents_02;

// 정적 팩터리 메서드 방식의 싱글턴

public class Elvis2 {
    private static final Elvis2 INSTANCE = new Elvis2();

    private Elvis2() {
        // ...
    }

    private Object readResolve() {      // 싱글텀임을 보장해준다. (직렬화로 부족)
        return INSTANCE;                // 진짜 INSTANCE 를 반환하고, 가짜는 가비지 컬렉터에게 맡긴다.
    }

    public static Elvis2 getInstance() {
        return INSTANCE;
    }

    public void leaveTheBuilding() {
        // ...
    }
}
