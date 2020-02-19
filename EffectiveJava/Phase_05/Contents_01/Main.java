package Phase_05.Contents_01;

/* ----------------------------------------------

5장. 제네릭

(26) < 로 타입은 사용하지 말라 >
(27) < 비검사 경고를 제거하라 >

 ---------------------------------------------- */

import com.sun.org.apache.xpath.internal.operations.String;

import java.util.*;

public class Main {

    private static Object[] elements;
    private static int size;

    public static void main(String[] args) {

        // 제네릭 - 클래스 또는 인터페이스 선선에 타입 매개변수가 쓰이는 것 (제네릭 타입)
        // 각각의 제네릭 타입은 일련의 매개변수화 타입을 정의한다. (정규화 타입 매개변수 E 에 해당하는 실제 타입 매개변수 <?>)
        // 그리고 제네릭 타입을 정의하게 되면 그에 대한 로(raw) 타입도 함께 정의된다.

        // 로 타입 - 제네릭 타입에서 타입 매개변수를 전혀 사용하지 않을 때 (ex. List<E> 의 로 타입 : List)
        // 하지만 로 타입을 사용하면 제네릭이 주는 타입 안전성과 표현력을 모두 잃게 되는 결과를 가져온다.
        // 또한 컴파일 단계가 아닌 런타임에 오류를 발생시켜 원인 발생 코드를 찾기가 복잡해 질 수 있다.
        // 로 타입은 호환성을 위해 존재할 뿐 !!
        List<String> strings = new ArrayList<>();
        unSafeAdd(strings, Integer.valueOf(42));

        // SafeAdd(strings, Integer.valueOf(42));

        String result = strings.get(0);     // 컴파일러가 자동으로 형변환 코드를 넣어주지만 Integer 를 String 으로 변환하려 시도한 것이다. (런타임, ClassCastException)

        // 제네릭 타입을 쓰고 싶지만 실제 타입 매개변수가 무엇인지 신경 쓰고 싶지 않다면
        // '비한정적 와일드카드 타입'인 ? 를 사용하면 된다. (ex. Set<?>)
        // 와일드카드 타입은 안전하고, 로 타입은 안전하지 않다.
        // 로 타입에는 아무 원소를 넣을 수 있기 때문에 타입 불변식을 훼손할 위험이 있지만 와일드카드 타입에는 null 외에 어떤 원소도 넣을 수 없다.
        // 즉, 컬렉션의 타입 불변식을 웨손하지 못하게 막음으로써 안전함을 보장한다. --> 제네렉 메서드나 한정적 와일드카드 타입을 사용하여 해결
        // 예외적으로 class 리터럴, instanceof 에는 로 타입을 사용한다. (그 후 형변환 수행)

        // 제네릭 사용시 주로 발생할 수 있는 컴파일러 경고 - 비검사 형변환, 비검사 메서드 호출, 비검사 매개변수화 가변인수 타입, 비검사 변환 등
        // 결론은 이러한 비검사 경고들을 최대한 제거해야 한다는 것이다.
        Set<String> set = new HashSet();        // 비검사 경고 예

        // 만일 경고를 제거할 수는 없지만 타입 안전함을 보장할 수 있다면 @SuppressWarnings("unchecked") 애너테이션으로 경고를 숨기자
        // 단, 이 애너테이션은 선언에만 달 수 있으며, 가능한 한 좁은 범위에 적용한다. (지역 변수를 추가하여)
        elements = new Object[1024];
        size = elements.length;

        String[] result2 = toArray(new String[1000]);
        // 마지막으로 @SuppressWarnings("unchecked") 사용시, 경고를 무시해도 되는 이유를 주석으로 남겨놓자!
    }

    private static void unSafeAdd(List list, Object o) {        // 로 타입 매개변수 list
        list.add(o);
    }

    private static void SafeAdd(List<Object> list, Object o) {      // 컴파일 오류를 발생시킨다.
        list.add(o);
    }

    private static <T> T[] toArray(T[] a) {
        if (a.length < size) {
            @SuppressWarnings("unchecked")      // 생성한 배열과 매개변수 배열의 타입이 모두 T[] 이기 때문에 올바른 형변환이다.
                    T[] result = (T[]) Arrays.copyOf(elements, size, a.getClass());

            return result;
        }

        System.arraycopy(elements, 0, a, 0, size);
        if (a.length > size) a[size] = null;

        return a;
    }
}
