package me.whiteship.chapter01.item09.suppress;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TopLine {
    // 코드 9-1 try-finally - 더 이상 자원을 회수하는 최선의 방책이 아니다! (47쪽)
    static String firstLineOfFile(String path) throws IOException {
        // readLine() 오류 발생 -> finally 실행 -> close() 오류 발생 일때
        // 이때는 가장 먼저 발생한 readLine() 오류 발생 후, close() 오류도 보인다.
        // 바이트코드 보면 var9.addSuppressed(var6); 이런식으로 add 해주기 때문임
        try(BufferedReader br = new BadBufferedReader(new FileReader(path))) {
            return br.readLine();
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println(firstLineOfFile("pom.xml"));
    }
}
