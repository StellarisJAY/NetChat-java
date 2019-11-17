import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class ServerThread implements Runnable {
    private Socket socket;
    private ArrayList<Socket> clients;

    public ServerThread(Socket socket, ArrayList<Socket> clients){
        this.socket = socket;
        this.clients = clients;
    }
    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            Scanner reader = new Scanner(inputStream, "UTF-8");
           while(true){
               String str = reader.nextLine();
               System.out.println(str);

               // 服务器将读取到的信息全部分发到各个客户端，相当于服务器只做信息中转
               // 一条信息通过客户端到达服务器，服务器再将该消息分发给其他客户端，这样就做到了远程通信
               Iterator<Socket> iterator = clients.iterator();
               while(iterator.hasNext()){
                   Socket currentClient = iterator.next();
                   OutputStream outputStream = currentClient.getOutputStream();
                   PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true);
                   writer.println(str);
               }
           }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
