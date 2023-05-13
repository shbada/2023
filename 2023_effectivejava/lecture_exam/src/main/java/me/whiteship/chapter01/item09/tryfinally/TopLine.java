package me.whiteship.chapter01.item09.tryfinally;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TopLine {
    // 코드 9-1 try-finally - 더 이상 자원을 회수하는 최선의 방책이 아니다! (47쪽)
    static String firstLineOfFile(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path));
        try {
            return br.readLine();
        } finally {
            // readLine() 오류 발생 -> finally 실행 -> close() 오류 발생
            // readLine() 오류를 삼켜버려서, 가장 마지막에 발생한 close() 오류'만' 보인다.
            // 디버깅때는 가장 처음에 발생한 예외가 중요한데, 이는 오류 찾기를 어렵게만든다.
            br.close();
        }
    }

    public static void main(String[] args) throws IOException {
        String path = args[0];
        System.out.println(firstLineOfFile(path));
    }
}
