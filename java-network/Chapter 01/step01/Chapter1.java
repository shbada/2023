package packt.step01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SocketChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * java.io, java.nio, java.nio 서브패키지는 자바에서의 IO 처리 대부분을 지원한다.
 * 채널(Channel) : 애플리케이션 간의 데이터 흐름
 * 버퍼(Buffer) : 데이터를 처리하기 위한 채널과 함께 동작
 * 셀렉터(Selector) : 다중 채널을 처리하기 위한 싱글 스레드를 허용하는 기술
 *
 * 데이터는 버퍼->채널, 채널->버퍼로 전달될 수 있다.
 * 버퍼는 정보에 대한 임시 저장소다.
 * 셀렉터는 애플리케이션 확장성을 지원하는데 유용하다.
 *
 * [주요채널]
 * FileChannel : 파일과 함께 동작
 * DatagramChannel : UDP 통신 지원
 * SocketChannel : TCP 클라이언트와 함께 사용
 * ServerSocketChannel : TCP 서버와 함께 사용
 */
public class Chapter1 {

    public static void main(String[] args) {
//        inetAddressExamples();
//        nioExamples();
//        socketExamples();
        urlConnectionExample();
    }

    /**
     * InetAddress 클래스를 사용한 네트워크 주소
     * @param address
     */
    private static void displayInetAddressInformation(InetAddress address) {
        System.out.println("---InetAddress Information---");
        System.out.println(address);
        System.out.println("CanonicalHostName: " + address.getCanonicalHostName());
        System.out.println("HostAddress: " + address.getHostAddress());
        System.out.println("HostName: " + address.getHostName());
        System.out.println("---------");
    }

    private static void inetAddressExamples() {
        try {
            InetAddress address = InetAddress.getByName("www.packtpub.com");
            System.out.println(address);
            displayInetAddressInformation(address);
            address = InetAddress.getByName("83.166.169.231");
            System.out.println(address);
            address = InetAddress.getLocalHost();
            System.out.println(address);

        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
    }

    private static void nioExamples() {
        try {
            InetAddress address = InetAddress.getByName("packtpub.com");
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress(address, 80));
            while (!socketChannel.finishConnect()) {
                // wait, or do something else...
            }
            System.out.println(socketChannel);
            System.out.println(socketChannel.isConnected());
            System.out.println(socketChannel.isBlocking());

            ByteBuffer buffer;
            buffer = ByteBuffer.allocate(64);
            System.out.println("buffer: " + buffer);
            int bytesRead = socketChannel.read(buffer);
            System.out.println("bytesRead: " + bytesRead);
            if (bytesRead > 0) {
                buffer.flip();
                while (buffer.hasRemaining()) {
                    System.out.println("--" + buffer.get());
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void socketExamples() {
        try {
            InetAddress address = InetAddress.getByName("google.com");
            Socket socket = new Socket(address, 80);
            System.out.println(socket.isConnected());
            InputStream input;
            input = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            while (!br.ready()) {
            }
            String line = br.readLine();
            System.out.println("First - " + line);
            while ((line = br.readLine()) != null) {
                System.out.println("Each - " + line);
            }
            System.out.println("Done");
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 서버에 간단하게 접속하는 방법 : URLConnection 클래스 사용
     * URLConnection 클래스 : 애플리케이션과 URL 인스턴스 간의 연결을 나타낸다.
     */
    private static void urlConnectionExample() {
        try {
            URL url = new URL("http://www.google.com");
//            URLConnection urlConnection = url.openConnection();
//            BufferedReader br = new BufferedReader(
//                    new InputStreamReader(urlConnection.getInputStream()));
//            String line;
//            while ((line = br.readLine()) != null) {
//                System.out.println(line);
//            }
//            br.close();

            // Channel
            
            System.out.println("Channel Example");
            URLConnection urlConnection = url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            /* ReadableByteChannel 인스턴스 생성 */
            ReadableByteChannel channel = Channels.newChannel(inputStream);

            ByteBuffer buffer = ByteBuffer.allocate(64);

            String line = null;

            /* ReadableByteChannel.read()를 사용하여 사이트에서 읽기 작업을 가능하게한다. */
            while (channel.read(buffer) > 0) { // 한번에 64바이트 표시로 제한된다.
                System.out.println("---" + new String(buffer.array()));
                buffer.clear();
            }
            channel.close();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
