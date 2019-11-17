import java.net.*;
import java.io.*;
import java.util.ArrayList;

/**聊天室服务端程序
 * @author Jay
 * @version 2.0 BETA
 * 1. 服务端只做接受信息和分发信息，相当于只是一个信息中转站
 * 2. 服务器会为每一个建立连接的用户独立开启一个监听线程，通过该线程来接收用户信息，并分发给其他用户
 *
 * TODO：
 *  1. 加入服务器管理员，可以从服务器向所有用户发送消息。
 *  2. 为服务端加入图形界面，但是保留命令行界面。
 *  3. 聊天记录保存功能
 */
public class Server {
    public static void main(String[] args)
    {
        ArrayList<Socket> clients = new ArrayList<Socket>();
        try{
            ServerSocket server = new ServerSocket(8000);
            System.out.println(StdOut.message("服务器", "等待新连接..."));

            // 服务器不断接受连接，没连接一个客户端就为该客户端生成一个单独的服务线程
            while(true){
                Socket client = server.accept();
                System.out.println(StdOut.message("服务器", "新连接建立..."));

                // 服务器发送欢迎信息
                // 更新计划：
                // 1、个性化欢迎信息
                // 2、输出当前聊天室信息
                OutputStream outputStream = client.getOutputStream();
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true);
                writer.println(StdOut.message("服务器", "欢迎加入！😀"));
                clients.add(client);
                new Thread(new ServerThread(client, clients)).start();
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
