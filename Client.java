import java.io.IOException;
import java.net.Socket;

/**
 * 聊天室客户端程序
 * @version 2.0 BETA
 * @author Jay
 * 1. 客户端拥有图形化界面
 * 2. 客户端只有一个信息接收线程，负责实时接收服务端发来的信息，并输出到gui
 * 3. 客户端通过发送按钮发送信息到服务端的独立线程，然后服务器分发信息到其他客户端（包括发送端，这样可以确认信息是否成功发送）
 *
 * TODO
 *  1. 加入回车键发送消息功能
 *  2. 自动清空输入框
 *  3. gui界面的自适应
 */
public class Client {
    public static void main(String[] args)
    {
        GUI gui = new GUI();
        try{
            Socket client = new Socket("182.92.116.152", 8000);
            gui.setClientSock(client);
            gui.buttListen.setSocket(client);
            gui.messageBox.append(StdOut.message("服务器", "成功建立连接\n"));
            new Thread(new ThreadedClientHandler(client, gui)).start();
        }
        catch(IOException e){
            gui.messageBox.append(StdOut.message("错误", "无法连接到服务器，请重试...\n"));
            e.printStackTrace();
        }

    }
}
