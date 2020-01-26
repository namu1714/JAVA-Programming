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
		setTitle("����ê");
		setSize(400, 600);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		title = new JLabel("\n����ä��\n");
		title.setFont(new Font("�������", Font.BOLD, 40));
		title.setHorizontalAlignment(JLabel.CENTER);
		
		ok = new JButton("����ä�ÿ� �����մϴ�");
		ok.addActionListener(this);

		this.add(title, BorderLayout.CENTER);
		this.add(ok,  BorderLayout.SOUTH);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if(cmd.equals("����ä�ÿ� �����մϴ�")) {
			new Client();
			this.setVisible(false);
		}
	}
}
