import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static java.lang.Thread.sleep;

public class Main {

    public static void main(String[] args) {
//        System.out.println("_____________________ By Thread _____________________");
//
//        Thread fbthread = new fbThread();
//        Thread twthread = new twThread();
//
//        fbthread.start();
//        twthread.start();
//
//        System.out.println("(" + System.currentTimeMillis() + ") [Main] Merge Start.");
//
//        try {
//            sleep(1000);       // 합병 시간 = 1초
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("(" + System.currentTimeMillis() + ") [Main] Merge End.");

        System.out.println("_____________________ By Callable _____________________");

        try {
            // 스레드 풀 생성
            ExecutorService executorService1 = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
            ExecutorService executorService2 = Executors.newCachedThreadPool();

            // 작업 생성 및 처리 요청
            List<String> responseFb;
            List<String> responseTw;

            Future<List<String>> futureFb = executorService2.submit(new fbCallable());
            Future<List<String>> futureTw = executorService2.submit(new twCallable());

            // 작업 종료 응답 대기 : 블록됨
            // 만일 트위터 응답이 더 빨라도 메인 스레드는 페이스북 응답이 완료될 때까지 블록됨
            responseFb = futureFb.get();
            System.out.println("_______________________________________________________");
            System.out.println("(" + System.currentTimeMillis() + ") [Main] FaceBook Response is done.");
            System.out.println("_______________________________________________________");

            responseTw = futureTw.get();
            System.out.println("_______________________________________________________");
            System.out.println("(" + System.currentTimeMillis() + ") [Main] Twitter Response is done.");
            System.out.println("_______________________________________________________");

            // 확인 및 병합
            if (futureFb.isDone() && futureTw.isDone()) {
                System.out.println("_______________________________________________________");
                System.out.println("(" + System.currentTimeMillis() + ") [Main] Merge Start.");
                System.out.println("_______________________________________________________");
                sleep(1000);

                for (String line : responseFb) System.out.println(line);
                for (String line : responseTw) System.out.println(line);

                System.out.println("_______________________________________________________");
                System.out.println("(" + System.currentTimeMillis() + ") [Main] Merge End.");
                System.out.println("_______________________________________________________");
            }

            // 스레드 풀 종료
            executorService2.shutdown();

            try {
                if (!executorService2.awaitTermination(5, TimeUnit.MINUTES)) {
                    executorService2.shutdownNow();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                executorService2.shutdownNow();
            }

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
