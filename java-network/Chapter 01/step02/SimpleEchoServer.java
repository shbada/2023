package packt.step02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * 에코 서버 생성
 */
public class SimpleEchoServer {
    public static void main(String[] args) {
        System.out.println("Simple Echo Server");

        // ServerSocket 클래스 : 클라이언트 요청을 수신하는 서버에서 사용하는 특별한 소켓
        try (ServerSocket serverSocket = new ServerSocket(6000)){            
            System.out.println("Waiting for connection.....");

            // 클라이언트로부터 요청을 수신할때까지 ServerSocket 클래스는 이 호출을 Block 한다.
            // 블로킹(Blocking) : 메서드가 반환될 때까지 프로그램이 중단되는 것
            // 요청이 수산되면 accept()는 클라이언트와 서버 간의 연결을 나타내는 Socket 클래스 인스턴스를 반환한다.
            Socket clientSocket = serverSocket.accept();
            System.out.println("Connected to client");

            // 텍스트를 처리하고 클라이언트의 메시지를 읽기 위해 BufferedReader 인스턴스를 사용한다.
            // BufferedReader 인스턴스는 클라이언트 소켓의 getInputStream 메서드를 사용하여 생성한다.
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
                 // 클라이언트에 응답하기 위해 PrintWriter 인스턴스를 사용한다.
                 // 클라이언트 소켓의 getOutputStream()를 사용해 생성된다.
                 // autoFlush true : out 객체를 사용해 전송된 텍스트는 사용 후에 자동으로 플러시된다.
                 // 텍스트는 소켓에서 사용될때 버퍼가 가득차거나 flush()가 호출될때까지 버퍼에 위치한다.
                    PrintWriter out = new PrintWriter(
                            clientSocket.getOutputStream(), true)) {

                // 한 라인 단위로 클라이언트 요청을 처리한다.
                // Traditional implementation
                String inputLine;
                while ((inputLine = br.readLine()) != null) {
                    System.out.println("Client request: " + inputLine);
                    // out 객체를 사용해 클라이언트에게 다시 전송한다.
                    out.println(inputLine);
                }
                
                // Functional implementation
//                Supplier<String> socketInput = () -> {
//                    try {
//                        return br.readLine();
//                    } catch (IOException ex) {
//                        return null;
//                    }
//                };
//                Stream<String> stream = Stream.generate(socketInput);
//                stream
//                        .map(s -> {
//                            System.out.println("Client request: " + s);
//                            out.println(s);
//                            return s;
//                        })
//                        .allMatch(s -> s != null);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("Simple Echo Server Terminating");
    }
}
