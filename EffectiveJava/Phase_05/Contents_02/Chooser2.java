package Phase_05.Contents_02;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

// 제네릭을 사용하여 타입 안전성 확보

public class Chooser2<T> {
    private final T[] choiceArray;
    private final List<T> choiceArray2;

    public Chooser2(Collection<T> choices) {
        this.choiceArray = (T[]) choices.toArray();         // 제네릭 배열의 비검사 경고
        this.choiceArray2 = new ArrayList<>(choices);       // List 로 해결
    }

    public T choice() {
        Random random = ThreadLocalRandom.current();

        return choiceArray[random.nextInt(choiceArray.length)];
    }

    public T choice2() {
        Random random = ThreadLocalRandom.current();

        return choiceArray2.get(random.nextInt(choiceArray2.size()));
    }
}
