import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class fbCallable implements Callable<List<String>> {

    @Override
    public List<String> call() throws Exception {
        List<String> list = new ArrayList<>();

        for (int i = 0; i < 300; i++) {     // 전체 응답 시간 = (0.05 * 300) 초
            System.out.println(Thread.currentThread().getName() + " : getFaceBookTimeline ... (Line " + i + ")");

            Thread.sleep(50);
            list.add("FaceBook TimeLine : " + i);
        }

        return list;
    }
}
