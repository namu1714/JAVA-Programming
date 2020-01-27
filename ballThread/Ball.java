package BallThread;

import java.awt.*;
import java.util.ArrayList;

public class Ball extends Thread{
	private Canvas box;
	private int diameter;
	private double dx=2, dy;
	private int x;
	private int y;
	double radian;
	boolean isRunnable;
	
	public Ball(Canvas c, int cx, int cy, int size, double rad) 	{ 
		box = c;
		x = cx;
		y = cy;
		diameter = size;
		radian = rad;
		dy = dx*Math.tan(radian);
		if(radian>=Math.PI/2 && radian<3*Math.PI/2){
			dx = -dx;}
		isRunnable = true;
	}
	
	//공들이 충돌하는지 검사
	public boolean colliding (Ball b)
	{   
		try {
			int distX = this.x - b.x;
			int distY = this.y - b.y;
			double distance = Math.sqrt((distX * distX) + (distY * distY));

			if(distance <= (this.diameter + b.diameter)/2){
				double distanceXPrime = (this.x + this.dx) - (b.x + b.dx);
				double distanceYPrime = (this.y + this.dy) - (b.y + b.dy);
				double distancePrime = Math.sqrt(distanceXPrime * distanceXPrime + distanceYPrime * distanceYPrime);
				if(distance > distancePrime) {return true;}
			}
		}catch(Exception e) {return false;}
		
		return false;
	}

	//공이 충돌하면 공을 둘로 쪼개고, 원래 공은 소멸
	public void splitTwoBall(Ball b1, Ball b2) {
		try {
			int indexOfBall;
			
			if(b1.diameter>2){
				Ball ballOne = new Ball(box, b1.x, b1.y, b1.diameter/2, b1.radian-Math.PI/4);
				Ball ballTwo = new Ball(box, b1.x, b1.y, b1.diameter/2, b1.radian+Math.PI/4);
				BallList.b.add(ballOne);
				BallList.b.add(ballTwo);
	   
				ballOne.start();
				ballTwo.start();
			}

			indexOfBall = BallList.b.indexOf(b1);
			b1.isRunnable = false;
			BallList.b.remove(indexOfBall);
		  
			if(b2.diameter>2){
				Ball ballThree = new Ball(box, b2.x, b2.y, b2.diameter/2, b2.radian-Math.PI/4);
				Ball ballFour = new Ball(box, b2.x, b2.y, b2.diameter/2, b2.radian+Math.PI/4);
				BallList.b.add(ballThree);
				BallList.b.add(ballFour);
 
				ballThree.start();
				ballFour.start();
			}
			indexOfBall = BallList.b.indexOf(b2);
			b2.isRunnable = false;
			BallList.b.remove(indexOfBall);

		}catch(Exception e) {}
	}

	public void draw(){  	
		Graphics g = box.getGraphics();
  		g.fillOval(x, y, diameter, diameter);
  		g.dispose();	
  	}
		
	public void move(){  	
		Graphics g = box.getGraphics();
  		g.setXORMode(box.getBackground());
  		g.fillOval(x, y, diameter, diameter);
  		
  		x += dx;	
  		y += dy;
  		
  		Dimension d = box.getSize();
  		if (x < 0) 					  { x = 0; dx = -dx; }
  		if (x + diameter >= d.width)  { x = d.width - diameter; dx = -dx; }
  		if (y < 0)					  { y = 0; dy = -dy; }
  		if (y + diameter >= d.height) { y = d.height - diameter; dy = -dy; }
  			
  		synchronized(this) {
  		for (int i=0;i<BallList.b.size();i++) {
  			Ball targetBall = BallList.b.get(i);
  			if(colliding(targetBall) && (i!=BallList.b.indexOf(this))) {
  				this.splitTwoBall(this, targetBall);
  			}
  		}
  		}//synchronized
  			
  		g.setPaintMode();
  		g.fillOval(x, y, diameter, diameter);
  		g.dispose();	
  	}	
	
	public void run()
	{  	
		draw();
		while(isRunnable==true){	
			move();
			try { Thread.sleep(20); } catch(InterruptedException e) {}
		} 
	}
}

class BallList {
	public static ArrayList<Ball> b = new ArrayList<Ball>();
}

