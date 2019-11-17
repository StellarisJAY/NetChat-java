import java.net.*;
import java.io.*;
import java.util.Scanner;

public class ThreadedClientHandler implements Runnable {
    private Socket socket;
    private GUI gui;

    public ThreadedClientHandler(Socket socket, GUI gui){
        this.socket = socket;
        this.gui = gui;
    }
    @Override
    public void run() {
        try{
            InputStream inputStream = socket.getInputStream();
            Scanner reader = new Scanner(inputStream, "UTF-8");
            while(true){
                String str = reader.nextLine();
                gui.messageBox.append(str + "\n");
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
