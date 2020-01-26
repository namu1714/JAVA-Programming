package RandomChat;
import java.net.*;
import java.util.*;
import java.awt.Insets;
import java.io.*;
import javax.swing.*;

public class Server extends JFrame{
	static HashMap clients = new HashMap();
	static ArrayList names  = new ArrayList();
	
	private JTextArea area;
	boolean startFlag;
	
	Server(){
		setTitle("ä�ü���");
		setSize(400, 300);
		area = new JTextArea();
		area.setMargin(new Insets(3,3,3,3));
		area.setEditable(false);
		
		JScrollPane jsp = new JScrollPane(area, 
		        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
		        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(jsp);
		
		Collections.synchronizedList(names);
		Collections.synchronizedMap(clients);
		
		setVisible(true);
	}
	
	public void ServerStart() {
		ServerSocket serverSocket = null;
		Socket socket = null;
		
		try {
			serverSocket = new ServerSocket(7777);
			area.append("������ ���۵Ǿ����ϴ�\n");
			
			while(true) {
				socket = serverSocket.accept();
				ServerReceiver thread = new ServerReceiver(socket);
				thread.start();
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	class ServerReceiver extends Thread{
		Socket socket;
		DataOutputStream out;
		DataInputStream in;
		
		ServerReceiver(Socket socket){
			this.socket = socket;
			try {
				in = new DataInputStream(socket.getInputStream());
				out = new DataOutputStream(socket.getOutputStream());
			} catch(IOException e) {}
		}
		
		public void run() {
			String name = "";
			String partnerName="";
			try {
				name = Partner.createID();
				clients.put(name, out);
				names.add(name);
				area.append("[" + socket.getInetAddress() + ":" + socket.getPort() + "]-" + name + "�� �����Ͽ����ϴ�\n");
				area.append("���� �����ڴ� " + names.size() + "���Դϴ�.\n");
				
				if(names.size()%2==0) {
					startFlag = true;
				}else { 
					startFlag = false;
					Partner.sendTo(name, "���� ���� �����...\n");
				}
				
				while(startFlag==false) {
					try {	sleep(1000);  } catch (InterruptedException e) {}
				}
				
				partnerName = Partner.find(name);
				
				while(in.available()>0) {
					in.readUTF();
				}
				
				Partner.sendTo(name, "������ �����Ͽ����ϴ�!\n");
				
				while(in!=null) {
					Partner.sendTo(partnerName, in.readUTF());
				}
				
			} catch(IOException e) {

			} finally {
				if(names.contains(partnerName))
					Partner.sendTo(partnerName, "������ ��ȭ�� �����߽��ϴ�\n");
				clients.remove(name);
				names.remove(name);
				area.append(name + "�� ������ �����Ͽ����ϴ�.\n");
				area.append("���� ���������� ���� " + clients.size() + "�Դϴ�.\n");
			}
		}
	}
	
	public static void main(String[] args) {
		new Server().ServerStart();
	}
}
