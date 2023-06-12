package loto_gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import java.awt.*;

public class Bubanj extends Panel implements Runnable {

	private static int[] niz=new int[39];
	private static int idUk=0;
	private int id=++idUk;
	private boolean radi=false;
	private Thread nit=new Thread(this);
	private Label label=new Label("39", Label.CENTER);
	Random rand =new Random();
	{ rand.setSeed(System.currentTimeMillis()); }
	
	public Bubanj() {
		setSize(15000, 70000);
		setBackground(Color.YELLOW);

		label.setFont(new Font(null, Font.BOLD, 30));
		add(label, "Center");
		for(int i=0;i<39;i++)
			niz[i]=0;
		
		setVisible(true);
		
		nit.start();
	}
	
	private void radnja() {
		
	
	
		String s=label.getText();
		int d=Integer.parseInt(s);
		niz[d-1]=0;
		int number=0;
		
		boolean flag=true;
		while(flag) {
			number=rand.nextInt(39)+1;
			flag=false;
			if(niz[number-1]==1)
				flag=true;
			else 
				niz[number-1]=1;
		}
		
			
		label.setText(Integer.toString(number));
	}
	
	@Override
	public void run() {
		try {
			while(!Thread.interrupted()) {
				synchronized (this) {
					if(!radi) wait();
				}
				
				radnja();
				Thread.sleep(10);
			}
			
		}catch(InterruptedException ie) {}
	}
	
	public String tekst() {
		return label.getText();
	}
	
	public synchronized void kreni() {
		setBackground(Color.RED);
		radi=true;
		notifyAll();
	}
	
	public synchronized void stani() {
		setBackground(Color.GREEN);
		radi=false;
	}
	
	public void unisti() {
		nit.interrupt();
	}
}
