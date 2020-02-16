package Phase_04.Contents_02;

import java.util.Collection;
import java.util.Set;

// 상속보다 컴포지션을 사용한 래퍼 클래스

public class InstrumentedHashSetByComposition<E> extends ForwardingSet<E> {
    private int addCount = 0;

    public InstrumentedHashSetByComposition(Set<E> s) {
        super(s);
    }

    @Override
    public boolean add(E e) {
        addCount++;
        return super.add(e);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        addCount += c.size();
        return super.addAll(c);
    }

    public int getAddCount() {
        return addCount;
    }
}
