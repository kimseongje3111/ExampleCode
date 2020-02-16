package Phase_04.Contents_03;

import java.util.Map;
import java.util.Objects;

// 골격 구현 클래스 (Map.Entry 인터페이스를 구현하는 추상 골격 클래스)
// 다중 상속의 많을 장점을 제공하면서 단점을 피할 수 있다.
// 골격 구현은 가능한 한 인터페이스의 default 메서드로 제공하여, 해당 인터페이스를 구현한 모든 곳에서 활용하도록 하는 것이 좋다.

public abstract class AbstractMapEntry<K, V> implements Map.Entry<K, V> {
        // A. 먼저 인터페이스에서 다른 메서드들의 구현에 사용되는 기반 메서드들을 선정한다.
        // B. 이 기반 메서드들은 골격 구현에서 추상 메서드가 된다.

        // C. 그다음, 기반 메서드들을 사용해 직접 구현할 수 있는 메서드들은 모두 default 메서드로 제공한다.
        // (단, equals 나 hashCode 같은 Object 메서드는 default 메서드로 제공하면 안된다)
        // 만일 인터페이스의 모든 메서드가 기반 메서드와 default 메서드가 된다면 골격 구현 클래스를 만들 필요가 없다.

    // D. 기반 메서드 또는 default 메서드로 만들지 못한 메서드가 남았다면 골격 구현 클래스를 만들어 남은 메서드들을 작성한다.
    // 이 예시에서 Map.Entry 의 기반 메서드는 getKey, getValue 이다.
    // 그리고 Object 메서드들은 default 메서드로 제공할 수 없기 때문에 모두 골격 구현 클래스에서 구현하여 제공한다.

    @Override
    public V setValue(V value) {        // 변경 가능한 엔트리는 이 메서드를 반드시 재정의해야 한다.
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Map.Entry)) return false;

        Map.Entry<?,?> e = (Map.Entry) obj;

        return Objects.equals(e.getKey(), getKey()) && Objects.equals(e.getValue(), getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getKey()) ^ Objects.hashCode(getValue());
    }

    @Override
    public String toString() {
        return getKey() + "=" + getValue();
    }
}