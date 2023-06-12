package loto_gui;

import java.util.Random;

public class Listici  {
	
	private int[][] loto;
	private int broj=0;
	private int[] listic =new int[7];
	private int[] pomoc=new int[5];
	private int pop=0;
	private int[] help=new int[39];
	
	Random rand =new Random();
	 { rand.setSeed(System.currentTimeMillis()); }
	public Listici() {
		
		loto=new int[1][1];
		
	}
	
	public int[] prebroj() {
		for(int i=0;i<5;i++)
			pomoc[i]=0;
		for(int i=0;i<broj;i++)	
			if(loto[i][7]>2) {
				switch(loto[i][7]) {
				case 3: pomoc[0]++; break;
				case 4: pomoc[1]++; break;
				case 5: pomoc[2]++; break;
				case 6: pomoc[3]++; break;
				case 7: pomoc[4]++; break;
				}
				
			}
		
		return pomoc;
	}
	
	public void pretrazi(int br) {
		for(int i=0;i<broj;i++)
			for(int j=0;j<7;j++)
				if(loto[i][j]==br) {
					loto[i][j]=0;
					loto[i][7]++;
					break;
				}
	}
	
	public void napravi(int kap) {
		broj=kap;
		loto=new int[broj][8];
		int[] pom=new int[7];
		for(int i=0;i<broj;i++) {
			pom=napraviListic();
			for(int j=0;j<pom.length;j++)
				loto[i][j]=pom[j];
			loto[i][7]=0;
			
		}
	}
	
	private int[] napraviListic() {
		boolean flag=false;
		//Random rand=new Random();
		
		for(int i=0;i<listic.length;i++) {
			flag=false;
	
			int broj = rand.nextInt(39)+1;
			for(int j=0;j<i;j++)
				if(listic[j]==broj) {
					flag=true;
					break;
				}
			
			if(flag==true)
				i--;
			else
				listic[i]=broj;
			
		}
		
		sortiraj();
		
		return listic;
	}
	
	private void sortiraj() {
		for(int i=0;i<listic.length-1;i++)
			for(int j=i+1;j<listic.length;j++)
				if(listic[i]>listic[j]) {
					int temp=listic[i];
					listic[i]=listic[j];
					listic[j]=temp;
				}
	}
	
	
	public int[] pobeda() {
		help=new int[vratiBroj()];
		pop=0;
		for(int i=0;i<broj;i++)
			if(loto[i][7]==6) {
				int p=0;
				while(loto[i][p]==0)
					p++;
				help[pop++]=loto[i][p];
			}
		return help;
	}
	
	public int vratiBroj() {
		pop=0;
		for(int i=0;i<broj;i++)
			if(loto[i][7]==6) 
				pop++;
		return pop;
	}
}
