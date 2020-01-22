public class fbThread extends Thread {

    @Override
    public void run() {
        try {
            for (int i = 0; i < 300; i++) {     // 전체 응답 시간 = (0.05 * 300) 초
                sleep(50);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("(" + System.currentTimeMillis() + ") [FaceBook] Response Complete.");
    }
}
