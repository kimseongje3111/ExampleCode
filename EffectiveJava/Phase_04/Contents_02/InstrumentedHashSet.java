package Phase_04.Contents_02;

import java.util.Collection;
import java.util.HashSet;

// 잘못된 상속의 예

public class InstrumentedHashSet<E> extends HashSet<E> {
    private int addCount = 0;       // 추가되 원소의 수

    public InstrumentedHashSet() {}

    public InstrumentedHashSet(int initCap, float loadFactor) {
        super(initCap, loadFactor);
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
