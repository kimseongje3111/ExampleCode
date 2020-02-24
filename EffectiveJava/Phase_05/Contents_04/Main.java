package Phase_05.Contents_04;

/* ----------------------------------------------

5장. 제네릭

(30) < 한정적 와일드카드를 사용해 API 유연성을 높이라 >

 ---------------------------------------------- */

import java.util.*;
import java.util.concurrent.ScheduledFuture;

public class Main {

    public static void main(String[] args) {

        // 앞서 보았듯이 매개변수화 타입은 불공변하다.
        // 하지만 때로는 불공변 방식보다 유연한 방식이 필요하다.
        // 이 때, '한정적 와일드카드 타입'을 사용하면 좀더 유연한 제네릭 타입 또는 메서드를 작성할 수 있을 것이다.
        pushAll(new ArrayList<Integer>());
        popAll(new ArrayList<Object>());

        // A. '유연성을 극대화하려면 원소의 생산자나 소비자용 입력 매개변수에 와일드카드 타입을 사용하라'
        // '펙스(PECS)' : producer-extends, consumer-super

        List<ScheduledFuture<?>> scheduledFutureList = new LinkedList<>();
        Optional<ScheduledFuture<?>> max = max(scheduledFutureList);
        // 만일 max 를 와일드카드 타입을 이용하여 수정하지 않았다면 처리할 수 없는 코드이다.
        // 그 이유는 ScheduledFuture 은 Delayed 의 하위 인터페이스이며, Delayed 는 Comparable<Delayed> 를 구현했지만
        // ScheduledFuture 가 Comparable<ScheduledFuture> 를 구현하지 않았기 때문이다.
        // 결과적으로 ScheduledFuture 인스턴스가 Delayed 인스턴스와도 비교될 수 있기 때문에 처리를 거부하는 것이다.
        // max 를 수정함으로써 Comparable 직접 구현하지 않고, 직접 구현한 다른 타입(Delayed)으로부터 확장한 타입(ScheduledFuture)을 지원한다.

        // B. '메서드 선언에 타입 매개변수가 한 번만 나오면 와일드카드로 대체하라'
        ArrayList<Integer> arrayList = new ArrayList<>(Arrays.asList(1, 2, 3));

        swapExample(arrayList, 0, 2);
        swapExample2(arrayList, 0, 2);
        // 내부적으로는 swapExample2 가 더 복잡하지만 클라이언트 입장에서는 복잡한 swapHelper 의 존재를 모른채 사용할 수 있게 된다.
        // 외부에 노출되는 API 라면 이러한 와일드카드를 활용하면 더욱 유연한 모습을 갖추게 된다.
    }

    // E 생산자(producer)
    // <? extends E> : 타입 매개변수가 E 의 하위 타입
    // 그러므로 Iterable<? extends E> 은 E 의 하위 타입의 Iterable 이라는 뜻
    private static <E> void pushAll(Iterable<? extends E> src) {
        for (E e : src) {
            // push(e);
            // 만일 일반적인 타입 매개변수로 선언된 Iterable<E> 를 매개변수로 받고,
            // 불공변의 특성 때문에 Stack<Object> 로 선언 후 pushAll(Integer) 를 호출하면 오류 발생
            // 결과적으로 한정적 와일드카드 타입을 사용하면 이러한 제약에서 벗어날 수 있으며 유연성이 향상된다.
        }
    }

    // E 소비자(consumer)
    // 반대로 Stack<Integer> 의 원소를 Object 용 컬렉션으로 옮긴다고 했을 때
    // 목적지 컬렌션인 메서드의 매개변수는 E 의 Collection(Collection<E>) 이 아니라 'E 의 상위 타입의 Collection' 임을 알려야 한다.
    // <? super E> : 타입 매개변수 E 의 상위 타입
    private static <E> void popAll(Collection<? super E> dst) {
        // while(!isEmpty()) dst.add(pop());
    }

    // 와일드카드 타입을 사용하여 개선된 max
    // 우선 입력 매개변수에서는 E 인스턴스를 생산하기 때문에 List<E> 를 List<? extends E> 으로 변경
    // 기존의 Comparable<E> 은 E 인스턴스를 소비하기 때문에 Comparable<? super E> 으로 변경
    private static <E extends Comparable<? super E>> Optional<E> max(List<? extends E> c) {
        if (!c.isEmpty()) {
            E result = null;

            for (E e : c) {
                if (result == null || e.compareTo(result) > 0) {
                    result = Objects.requireNonNull(e);
                }
            }

            return Optional.of(result);

        } else {
            return Optional.empty();
        }
    }

    // 두 메서드는 동일한 동작을 한다.
    // 어느 방식이 더 나을까?
    public static <E> void swapExample(List<E> list, int i, int j) {
        list.set(i, list.set(j, list.get(i)));
    }

    public static void swapExample2(List<?> list, int i, int j) {
        // list.set(i, list.set(j, list.get(i))); -> List<?> 에는 null 이외에 어떤 값도 넣을 수 없기 때문에 오류
        swapHelper(list, i, j);     // 해결
    }

    private static <E> void swapHelper(List<E> list, int i, int j) {
        list.set(i, list.set(j, list.get(i)));
    }
}
