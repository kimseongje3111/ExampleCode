public class twThread extends Thread {

    @Override
    public void run() {
        try {
            for (int i = 0; i < 500; i++) {     // 전체 응답 시간 = (0.03 * 500) 초
                sleep(50);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("(" + System.currentTimeMillis() + ") [Twitter] Response Complete.");
    }
}
