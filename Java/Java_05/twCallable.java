import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class twCallable implements Callable<List<String>> {

    @Override
    public List<String> call() throws Exception {
        List<String> list = new ArrayList<>();

        for (int i = 0; i < 500; i++) {     // 전체 응답 시간 = (0.03 * 500) 초
            System.out.println(Thread.currentThread().getName() + " : getTwitterTimeline ... (Line " + i + ")");

            Thread.sleep(30);
            list.add("Twitter TimeLine : " + i);
        }

        return list;
    }
}
