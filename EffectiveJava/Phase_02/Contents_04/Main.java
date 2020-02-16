package Phase_02.Contents_04;

/* -----------------------------------------------------

2장. 객체 생성과 파괴

(9) < try-finally 보다는 try-with-resources 를 사용하자 >

 ---------------------------------------------------- */

import java.io.*;

public class Main {

    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) throws IOException {

        // 자바 라이브러리에는 close 메서드를 통해 직접 닫아줘야 하는 자원이 많다.
        // 하지만 일반적인 try-finally 방식은 자원이 2개 이상일 경우 지저분하다.
        // 또한 이 방식은 예외가 무시될 경우가 생긴다.
        copy("읽을 파일 경로", "파일을 쓸 경로");

        // AutoCloseable 인터페이스를 구현한 클래스를 이용한 try-with-resources 방식을 사용하자 !
        // 이 방식은 쉽고 정확하게 자원을 회수할 수 있다.
        copyImproved("읽을 파일 경로", "파일을 쓸 경로");
    }

    private static void copyImproved(String src, String dst) throws IOException {
        try (InputStream in = new FileInputStream(src);
             OutputStream out = new FileOutputStream(dst)) {

            byte[] buf = new byte[BUFFER_SIZE];
            int n;

            while ((n = in.read(buf)) >= 0) {
                out.write(buf, 0, n);
            }
        }
    }

    private static void copy(String src, String dst) throws IOException {
        InputStream in = new FileInputStream(src);

        try {
            OutputStream out = new FileOutputStream(dst);

            try {
                byte[] buf = new byte[BUFFER_SIZE];
                int n;

                while ((n = in.read(buf)) >= 0) {
                    out.write(buf, 0, n);
                }
            } finally {
                out.close();
            }
        } finally {
            in.close();
        }
    }
}
