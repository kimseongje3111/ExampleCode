package Phase_04.Contents_03;

/* ----------------------------------------------

4장. 클래스와 인터페이스

(20) < 추상 클래스보다는 인터페이스를 우선하라 >

 ---------------------------------------------- */

import java.applet.AudioClip;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        // 인터페이스의 장점
        // A. 다중 상속이 가능한 인터페이스는 기존 클래스에도 손쉽게 구현체를 가질 수 있다.
        // B. 믹스인 정의에 알맞다. (믹스인 : 클래스가 구현할 수 있는 타입으로, 원래의 '주 타입' 외에 특정 선택적 행위를 제공)

        // C. 계층 구조가 없는 타입 프레임워크를 만들 수 있다.
        // '추상 골격 구현(Skeletal Implementation) 클래스'를 함게 제공하며, 인터페이스와 추상 크랠스의 장점을 모두 취하는 방법도 존재한다.
        // 인터페이스는 타입 정의(필요에 따라 default 메서드 제공), 골격 구현 클래스는 나머지 메서드들까지 구현
        // 그 결과, 단순히 골격 구현을 확장하는 것만으로 해당 인터페이스를 구현하는 데 필요한 일이 대부분 완료된다. (템플릿 메서드 패턴)
        List<Integer> integers = intArrayAsList(new int[5]);

        for (int i : integers) System.out.println(i);

        // 이처럼 골격 구현 클래스를 사용하면 추상 클래스처럼 구현을 도와주면서, 추상 클래스의 제약에서 벗어 날 수 있다.
        // 골격 구현 클래스를 확장하는 것으로 인터페이스의 구현이 거의 끝나지만, 구조상 골격 구현을 확장하지 못한다면 직접 인터페이스를 구현한다.
        // 어떠한 경우라도 인터페이스가 제공하는 default 메서드의 이점을 누릴 수 있다.

        Map.Entry entry = new AbstractMapEntry<String, String>() {
            @Override
            public String getKey() {        // 재정의
                return null;
            }

            @Override
            public String getValue() {
                return null;
            }
        };

        // 결론 - 단순 구현은 골격 구현의 포함될 수 있는 예이다.
        // 일반적으로 다중 구현용 타입으로는 인터페이스가 가장 적합하다.
        // 복잡한 인터페이스라면 구현하는 수고를 덜기 위해 골격 구현을 함께 제공하 방법을 고려해보자.
    }

    // AbstractList 를 골격 구현체로 사용하며, List 구현체를 반환하는 정적 팩터리 메서드
    private static List<Integer> intArrayAsList(int[] a) {
        Objects.requireNonNull(a);

        return new AbstractList<Integer>() {        // 골격 구현 클래스 이름은 관례적으로 접두어에 Abstract 가 붙는다.
            @Override
            public Integer get(int index) {
                return a[index];
            }

            @Override
            public Integer set(int index, Integer element) {
                int oldVal = a[index];
                a[index] = element;
                return oldVal;
            }

            @Override
            public int size() {
                return a.length;
            }
        };
    }
}

class Song {

}

interface Singer {
    AudioClip sing(Song s);
}

interface Songwriter {
    Song compose(int chartPosition);
}

// 두 인터페이스를 확장하고 새로운 메서드가 정의 가능한 제 3의 인터페이스 생성 (유연성)
// 만일 이와 같은 구조를 클래스로 만들기 위해서는 복잡한 계층 구조를 만들게 된다. (조합 폭발 현상)
interface SingerSongwriter extends Singer, Songwriter {
    AudioClip strum();

    void actSensitive();
}