package packt.step02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * 클라이언트 측면에서 console에 텍스트를 입력하자.
 * 서버에서 클라이언트에서 보낸 텍스트를 출력한다.
 */
public class SimpleEchoClientJava8 {

    public static void main(String args[]) {
        System.out.println("Simple Echo Client");

        try {
            System.out.println("Waiting for connection.....");
            /* 서버와 클라이언트가 동일한 머신에서 실행되는 것으로 가정한다.
            포트 6000과 함께 사용되는 InetAddress 클래스의 정적 getLocalHost 메서드는 머신의 주소를 반환한다. */
            InetAddress localAddress = InetAddress.getLocalHost();

            try (Socket clientSocket = new Socket("127.0.0.1", 6000);

                    PrintWriter out = new PrintWriter(
                            clientSocket.getOutputStream(), true);

                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(
                                    clientSocket.getInputStream()))) {

                System.out.println("Connected to server");
                Scanner scanner = new Scanner(System.in);

                // Java 8 implementation
                Supplier<String> scannerInput = scanner::nextLine;
                System.out.print("Enter text: ");
                /* 무한 스트림 생성 */
                Stream.generate(scannerInput)
                        .map(s -> { // 입력을 받고 이를 서버에 전송
                            out.println(s);
                            System.out.println("Server response: " + s);
                            System.out.print("Enter text: ");
                            return s;
                        })
                        .allMatch(s -> !"quit".equalsIgnoreCase(s)); // 메서드의 인자가 false면 스트림 종료
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
