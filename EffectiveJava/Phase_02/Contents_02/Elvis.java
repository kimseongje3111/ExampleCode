package Phase_02.Contents_02;

// public static final 필드 방식의 싱글턴

public class Elvis {
    public static final Elvis INSTANCE = new Elvis();       // 초기화시 딱 한번만 호출됨 - 전체 시스템에서 하나뿐임을 보장

    private Elvis() {
        // ...
    }

    private Object readResolve() {      // 싱글텀임을 보장해준다. (직렬화로 부족)
        return INSTANCE;                // 진짜 INSTANCE 를 반환하고, 가짜는 가비지 컬렉터에게 맡긴다.
    }

    public void leaveTheBuilding() {
        // ...
    }
}
