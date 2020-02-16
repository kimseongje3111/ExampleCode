package Phase_04.Contents_02;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

// 재사용 가능한 전달 클래스
// 상속 방식은 각각 구체 클래스들을 따로 확장하며, 지원하고 싶은 상위 클래스의 생성에 대응하는 생성자를 별도로 만들어 준다.
// 하지만 아래의 컴포지션 방식은 한 번만 구현해두면 어떠한 Set 구현체라도 계측할 수 있고, 기존 생성자들과 함께 사용 가능하다.

public class ForwardingSet<E> implements Set<E> {
    private final Set<E> s;                 // 새로운 Set

    public ForwardingSet(Set<E> s) {        // 생성자 제공
        this.s = s;
    }

    public void clear() {
        s.clear();
    }

    public boolean contains(Object o) {
        return s.contains(o);
    }

    public boolean isEmpty() {
        return s.isEmpty();
    }

    public int size() {
        return s.size();
    }

    public Iterator<E> iterator() {
        return s.iterator();
    }

    public Object[] toArray() {
        return s.toArray();
    }

    public <T> T[] toArray(T[] a) {
        return s.toArray(a);
    }

    public boolean add(E e) {
        return s.add(e);
    }

    public boolean remove(Object o) {
        return s.remove(o);
    }

    public boolean containsAll(Collection<?> c) {
        return s.containsAll(c);
    }

    public boolean addAll(Collection<? extends E> c) {
        return s.addAll(c);
    }

    public boolean retainAll(Collection<?> c) {
        return s.retainAll(c);
    }

    public boolean removeAll(Collection<?> c) {
        return s.removeAll(c);
    }

    @Override
    public int hashCode() {
        return s.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return s.equals(obj);
    }

    @Override
    public String toString() {
        return s.toString();
    }
}
