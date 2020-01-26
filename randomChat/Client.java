package RandomChat;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class Client extends JFrame implements ActionListener {
	
	private JTextArea textarea;
	private JPanel pane;
	private JTextField field;
	private JButton ok;
	
	private DataOutputStream out;
	private DataInputStream in;
	
	Client(){
		setTitle("랜덤챗");
		setSize(400, 600);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pane = new JPanel();
		textarea = new JTextArea();
		textarea.setMargin(new Insets(3,3,3,3));
		textarea.setEditable(false);
		
		JScrollPane jsp = new JScrollPane(textarea, 
		        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
		        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		field = new JTextField(30);
		ok = new JButton("확인");
		
		pane.add(field);
		pane.add(ok);
		
		this.add(jsp, BorderLayout.CENTER);
		this.add(pane, BorderLayout.SOUTH);
		
		field.addActionListener(this);
		ok.addActionListener(this);
		
		setVisible(true);
		start();
	}
	
	public void start() {
		try {
			String serverIp = "127.0.0.1";
			Socket socket = new Socket(serverIp, 7777);
			out = new DataOutputStream(socket.getOutputStream());
			textarea.append("서버에 연결되었습니다 (socket:" + 7777 + ")\n");

			Thread receiver = new Thread(new ClientReceiver(socket));

			receiver.start();
		} catch(ConnectException ce) {
			ce.printStackTrace();
		} catch(Exception e) {}
	}
	
	class ClientReceiver extends Thread{
		Socket socket;
		
		ClientReceiver(Socket socket){
			this.socket = socket;
			try {
				in = new DataInputStream(socket.getInputStream());
			} catch(IOException e) {}
		}
		
		public void run() {
			while(in!=null) {
				try {
					textarea.append(in.readUTF());
				} catch(IOException e) {}
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		String msg = field.getText();
		Object source = e.getSource();
		
		if(source==field || source==ok) {
			if(msg==null || msg.length()==0) {
			}
			else {
				textarea.append("나 : "+msg+"\n");
				try {
					out.writeUTF("상대방  : "+msg+"\n");
				} catch(IOException ie) {}
			}
			field.setText("");
		}
	}
}
