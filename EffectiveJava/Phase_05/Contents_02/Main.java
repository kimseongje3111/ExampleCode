package Phase_05.Contents_02;

/* ----------------------------------------------

5장. 제네릭

(28) < 배열보다는 리스트를 사용하라 >

 ---------------------------------------------- */

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        // 배열과 제네릭 타입에는 중요한 차이가 2가지 있다.

        // A. 배열은 공변이지만 제네릭은 불공변이다.
        // 만일 A 클래스가 B 클래스의 하위 타입일 때, A[] 은 B[] 의 하위 타입이 된다.
        // 즉, 공변이란 함께 변한다는 뜻으로 불공변인 제네릭은 함께 변하지 않는다.
        // 예를 들어 서로 다른 타입에 대해 List<Type1> 과 List<Type2> 은 서로 상/하위 관계가 아니다.

        Object[] objectsArr = new Long[100];
        objectsArr[0] = "I'm String.";                           // 배열의 경우 컴파일은 되지만 런타임에 ArrayStoreException 이 발생

        // List<Object> objectsList = new ArrayList<Long>();     // 제네릭은 컴파일 오류 발생

        // B. 배열은 실체화(Reify)되지만 제네릭은 실제 불가화 타입이다.
        // 배열은 런타임에도 자신이 담기로 한 원소의 타입을 인지하고 확인한다.
        // 때문에 앞서 코드에서도 런타임에 ArrayStoreException 이 발생했던 것이다.
        // 반면, 제네릭은 타입 정보가 런타임에는 '소거'되며 오직 컴파일 타임에만 타입 검사를 한다.

        // 이러한 차이점으로부터 배열은 제네릭 배열 생성 오류를 일으킨다.(실체 불가화 타입 - 제네릭 타입, 매개변수화 타입, 타입 매개변수로 생성 불가)
        // 예를 들어 new List<E>[], new List<String>[], new E[] 와 같은 코드는 제네릭 배열 생성 오류를 발생시킨다.
        // 제네릭 타입은 컴파일 타임에 타입 안전을 보장하기 위해 존재하지만 만일 이를 허용하면 런타임에 타입 변환 오류가 발생할 가능성이 생기며, 이는 제네릭 타입의 본질에 어긋나게 된다.
        Chooser chooser = new Chooser(new ArrayList<>());
        String result = (String) chooser.choice();              // 이곳에서 형변환 오류 위험

        Chooser2<String> chooser2 = new Chooser2<>(new ArrayList<>());
        String result2 = chooser2.choice2();                    // 타입 안정성

        // 하지만 제네릭 타입을 사용할 때, 항상 배열 E[] 보다 리스트 List<E> 를 선택하라는 것이 아니다.
        // 앞서 말한 개념과 모순되지만 !!
        // 사실 일반적으로 컴파일러가 경고하는 비검사 형변환이 프로그램의 타입 안정성을 해치지 않음을 스스로 확인한다면 @SuppressWarnings 으로 경고를 제거한다.
        // 자바가 리스트를 기본 타입으로 제공하지 않으므로 ArrayList 같은 제네릭 타입도 결국 기본 타입인 배열을 사용해 구현해야 한다.
        // 또한 일부 경우(ex. HashMap), 성능을 높일 목적으로 배열을 사용하기도 한다.
        // 결과적으로 배열 대신 리스트를 사용하는 것은 컴파일러 오류 또는 경고(타입 관련)의 해결에 대한 한가지 방법일 뿐, 해당 상황에 맞는 방법을 사용하자

        // 추가적으로 제네릭의 타입 매개변수에 제약을 둘 수도 있다.
        // 예) class DelayQueue<E extends Delayed> implements BlockingQueue<E> {} -> 타입 매개변수를 Delayed 하위 타입(자기 자신 포함)만 받는다.
        // 이를 '한정적 타입 매개변수'라고 한다.
    }
}
