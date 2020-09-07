import java.io.IOException;
import java.net.*;
import java.util.Scanner;
/*
 *
 *     客户端的代码
 *
 *
 * */
public class Test_DatagramSocket2 {
    // 客户端的主要流程分成四步.
    // 1. 从用户这里读取输入的数据.
    // 2. 构造请求发送给服务器
    // 3. 从服务器读取响应
    // 4. 把响应写回给客户端.

    private DatagramSocket socket = null;
    private String serverIp;
    private int serverPort;

    // 需要在启动客户端的时候来指定需要连接哪个服务器
    public Test_DatagramSocket2(String serverIp, int serverPort) throws SocketException {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        socket = new DatagramSocket();//客户端必须不绑定端口（由操作系统自动分配一个空闲端口）
        //客户端不能绑定的原因：如果绑定一个端口，一个主机就只能启动一个客户端
    }

    public void start() throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            // 1. 读取用户输入的数据
            System.out.print("-> ");
            String request = scanner.nextLine();
            if (request.equals("exit")) {
                break;
            }
            // 2. 构造请求发送给服务器
            DatagramPacket requestPacket = new DatagramPacket(request.getBytes(),
                    request.getBytes().length, InetAddress.getByName(serverIp), serverPort);
            socket.send(requestPacket);
            // 3. 从服务器读取响应
            DatagramPacket responsePacket = new DatagramPacket(new byte[4096], 4096);
            socket.receive(responsePacket);
            String response = new String(responsePacket.getData(), 0, responsePacket.getLength()).trim();
            // 4. 显示响应数据
            System.out.println(response);
        }
    }

    public static void main(String[] args) throws IOException {
        //"127.0.0.1" 这是个特殊的ip地址 回响服务器
        Test_DatagramSocket2 client = new Test_DatagramSocket2("127.0.0.1", 9090);
        // UdpEchoClient client = new UdpEchoClient("47.98.116.42", 9090);
        client.start();
    }
}