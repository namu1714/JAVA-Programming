package Calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class JavaCal extends JFrame implements ActionListener {
	private JTextField input;
	private JPanel display;
	private JPanel buttons;
	private String expression;
	
	JavaCal(){
		setTitle("°è»ê±â");
		setSize(300,400);
		setResizable(false);
		setLayout(new BorderLayout(5,5));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		display = new JPanel(new FlowLayout());
		input = new JTextField(11);
		input.setHorizontalAlignment(JTextField.RIGHT);
		input.setFont(new Font(Font.SERIF, Font.BOLD, 30));
		input.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if(key == KeyEvent.VK_ENTER) {
					Eval eval = new Eval(input.getText());
					eval.infixToPostfix();
					double result = eval.calPostfix();
					expression = "" + result;
					input.setText(expression);
				}
			}	
		});
		display.add(input);
		
		buttons = new JPanel();
		buttons.setLayout(new GridLayout(4,5,4,4));
		String[] str = {"1","2","3","/","C","4","5","6","*","<-","7","8","9","-","(","0",".","=","+",")"};
		JButton[] b = new JButton[20];

		 for(int i=0;i<str.length;i++)
		 {
		   b[i]=new JButton(str[i]);
		   b[i].setFont(new Font("¸¼Àº °íµñ", Font.PLAIN, 17));
		   b[i].addActionListener(this);
		   buttons.add(b[i]);
		 }
		
		add(display, BorderLayout.NORTH);
		add(buttons, BorderLayout.CENTER);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		JavaCal cal = new JavaCal();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		expression = input.getText();
		String command = e.getActionCommand();
		
		switch(command) {
		case "=":
			Eval eval = new Eval(expression);
			eval.infixToPostfix();
			double result = eval.calPostfix();
			expression = "" + result;
			input.setText(expression);
			break;
		case "C":
			expression = "";
			break;
		case "<-":
			if(expression.length()>0) {
				expression = expression.substring(0, expression.length()-1);
			}
			break;
		default:
			expression = expression + command;
		}
		input.setText(expression);
	}

}
