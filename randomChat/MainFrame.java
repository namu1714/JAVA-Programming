package RandomChat;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;

public class MainFrame extends JFrame implements ActionListener{
	private JLabel title;
	private JButton ok;
	
	public static void main(String[] args) {
		new MainFrame();
	}
	
	MainFrame(){
		setTitle("랜덤챗");
		setSize(400, 600);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		title = new JLabel("\n랜덤채팅\n");
		title.setFont(new Font("나눔고딕", Font.BOLD, 40));
		title.setHorizontalAlignment(JLabel.CENTER);
		
		ok = new JButton("랜덤채팅에 입장합니다");
		ok.addActionListener(this);

		this.add(title, BorderLayout.CENTER);
		this.add(ok,  BorderLayout.SOUTH);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if(cmd.equals("랜덤채팅에 입장합니다")) {
			new Client();
			this.setVisible(false);
		}
	}
}
