package BallThread;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class MainFrame extends JFrame implements ActionListener {
	private Canvas canvas; 
	public MainFrame(){  	
		canvas = new Canvas();
		
		setSize(400, 300);
      	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      	add("Center", canvas);
      	
      	JPanel p = new JPanel();
		JButton s = new JButton("Start");
		JButton c = new JButton("Close");
		p.add(s);	p.add(c);
      	s.addActionListener(this);
		c.addActionListener(this);
      	add("South", p);
      
      	setVisible(true); 
   	}
	
	public void actionPerformed(ActionEvent evt)
   	{  	if (evt.getActionCommand() == "Start")
      	{  	
      		for (int i=0;i<5;i++) {
      			BallList.b.add(new Ball(canvas,170,130,20,i*(Math.PI/5)));
      			BallList.b.get(i).start();
      		}
      	}
      	else if (evt.getActionCommand() == "Close")
         	System.exit(0);
   	}
	
	
	public static void main(String[] args) {
		new MainFrame(); 
	}
}
