package RandomChat;

import java.io.*;

public class Partner {
	static int idNumber = 0;
	
	static String createID() {
		String ID = "00000000" + Integer.toString(idNumber++);
		ID = ID.substring(ID.length()-8);
		return ID;
	}
	
	static void sendTo(String name, String msg) {
		try {
			DataOutputStream out = (DataOutputStream)Server.clients.get(name);
			out.writeUTF(msg);
		}catch(IOException e) {
			
		}
	}
	
	static String find(String name) {
		int index = Server.names.indexOf(name);
		String partnerName;
		
		if(index%2==0) {
			partnerName = (String)(Server.names.get(index+1));
		} else {
			partnerName = (String)(Server.names.get(index-1));
		}
		
		return partnerName;
	}
}
