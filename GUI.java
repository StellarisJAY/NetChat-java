import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;

public class GUI extends JFrame {
    private int width, height;

    protected JTextField nameBox;
    protected JScrollPane scrollPane;

    protected JTextArea messageBox;
    protected JTextField inputBox;
    protected JButton sendButton;
    protected SendListener buttListen;

    protected Socket clientSock;

    public GUI(Socket clientSock)
    {
        this.clientSock = clientSock;
        Init();
    }
    public GUI(){
        Init();
    }

    private void Init()
    {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension size = kit.getScreenSize();
        width = size.width / 3;
        height = (int)(size.height / 1.5);
        setSize(width, height);

        FlowLayout flow = new FlowLayout();
        flow.setHgap(30);
        flow.setVgap(20);
        setLayout(flow);

        JLabel name = new JLabel("用户名：");
        add(name);
        nameBox = new JTextField(20);
        add(nameBox);
        messageBox = new JTextArea(25, 40);
        messageBox.setLineWrap(true);
        messageBox.setEditable(false);
        scrollPane = new JScrollPane(messageBox, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setViewportView(messageBox);
        add(scrollPane);
        inputBox = new JTextField(30);
        add(inputBox);

        setTitle("聊天室");
        sendButton = new JButton("发送");
        buttListen = new SendListener();
        sendButton.addActionListener(buttListen);
        add(sendButton);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setClientSock(Socket clientSock) {
        this.clientSock = clientSock;
    }

    // 客户端向服务器发送消息，发送按钮的监视器
    class SendListener implements ActionListener{
        private Socket socket;
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                OutputStream outputStream = socket.getOutputStream();
                //BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true);
                // 将message设置为标准输出格式，这样在后来的接受和服务器分发就不用对message再进行处理,
                // 而且还通过这种方式将用户名和message打包一起发送
                String message = StdOut.message(nameBox.getText(), inputBox.getText());
                writer.println(message);
            }
            catch(IOException exception){
                exception.printStackTrace();
            }
        }

        public void setSocket(Socket socket) {
            this.socket = socket;
        }
    }
}
