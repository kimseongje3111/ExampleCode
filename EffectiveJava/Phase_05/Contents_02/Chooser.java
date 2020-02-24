package Phase_05.Contents_02;

// 제네릭을 사용하지 않는 경우

import java.util.Collection;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Chooser {
    private final Object[] choiceArray;

    public Chooser(Collection choices) {
        this.choiceArray = choices.toArray();
    }

    // choice 를 호출한 클라이언트는 원하는 타입으로 형변환 해야 한다.
    // 하지만 런타임에 형변환 오류 발생 위험이 있다.
    public Object choice() {
        Random random = ThreadLocalRandom.current();

        return choiceArray[random.nextInt(choiceArray.length)];
    }
}
