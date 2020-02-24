package Phase_05.Contents_03;

// 제네릭 싱글턴 팩터리 패턴 - 항등함수 예제

public class UnaryOperatorClass {
    // 불변 객체 (항등함수의 객체는 상태가 없기 때문에 요청마다 새로 생성하는 것이 낭비)
    // 제네릭은 실체화가 불가하기 때문에 타입별로 하나씩 만드는 것이 아니라 제네릭 싱글턴 하나면 충분
    private static UnaryOperatorExample<Object> IDENTITY_FN = (t) -> t;     // 매개변수 t -> return t (apply 정의)

    // 항등함수란 입력 값을 수정 없이 그대로 반환하는 특성을 가짐 : F(x) = x
    // T 가 어떤 타입이던지 타입 안전을 보장
    @SuppressWarnings("unchecked")
    public static <T> UnaryOperatorExample<T> identityFunction() {
        return (UnaryOperatorExample<T>) IDENTITY_FN;
    }
}
