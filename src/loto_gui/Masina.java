package loto_gui;

import java.awt.*;
import java.awt.event.*;

public class Masina extends Panel {
	
	private Bubanj[] niz;
	private int pop=0;
	private int[] brojevi;
	private int jot=0;
	private int[] stat;
	
	public Masina(int kap) {
		
		niz=new Bubanj[kap];
		for(int i=0;i<niz.length;i++) {
			niz[i]=new Bubanj();
			add(niz[i]);
		}
			
		brojevi=new int[niz.length];
		stat=new int[39];
		for(int i=0;i<stat.length;i++)
			stat[i]=0;
		
		setVisible(true);
		
	}
	
	public String zaustavi(int k) {
		niz[k].stani();
		brojevi[jot++]=Integer.parseInt(niz[k].tekst());
		stat[Integer.parseInt(niz[k].tekst())-1]++;
	
		if(jot==7) {
			sortiraj();
			jot=0;
		}
		
		return niz[k].tekst();
	}
	
	public void pokreniSve() {
		
		for(int i=0;i<niz.length;i++)
			niz[i].kreni();
	}
	
	private void zaustaviSve() {
	
		for(int i=0;i<niz.length;i++) {
			niz[i].stani();
			stat[Integer.parseInt(niz[i].tekst())-1]++;
		}
	}

	private void brojevi() {
		zaustaviSve();
		for(int i=0;i<brojevi.length;i++) {
			String s=niz[i].tekst();
			brojevi[i]=Integer.parseInt(s);
		}
	}
	
	private void sortiraj() {
		
		for(int i=0;i<brojevi.length-1;i++)
			for(int j=i+1;j<brojevi.length;j++)
				if(brojevi[i]>brojevi[j]) {
					int temp=brojevi[i];
					brojevi[i]=brojevi[j];
					brojevi[j]=temp;
				}
	}
	
	public void zaustaviBubnjeve() {
		brojevi();
		sortiraj();

	}
	
	public void unisti() {
		for(int i=0;i<niz.length;i++)
			niz[i].unisti();
	}
	public int[] niz() {
		return brojevi;
	}
	public int[] niz_stat() {
		return stat;
	}
	
	public int nadji_max() {
		int max=stat[0];
		for(int i=1;i<stat.length;i++)
			if(stat[i]>max)
				max=stat[i];
		return max;
	}
	
	public int nadji_min() {
		int min=stat[0];
		for(int i=1;i<stat.length;i++)
			if(stat[i]<min)
				min=stat[i];
		return min;
	}
}
