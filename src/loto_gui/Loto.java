package loto_gui;

import java.awt.*;
import java.awt.event.*;

public class Loto extends Frame {

	
	private Masina masina;
	private Button kreni, zaustavi, stani, napravi, statistika;
	private TextField[] txt;
	private Label label, label_br;
	private int[] niz;
	private int[] numbers;
	private Label[] labele;
	private TextField[] tekst;
	private static int brojac=0;
	private int broj_kombinacija=0;
	private Listici listici=new Listici();
	private int[] pomoc=new int[5];
	private Panel tabela=new Panel(new GridLayout(13, 3));
	private Label[] tab=new Label[39];
	private Panel[] paneli=new Panel[39];
	private Dobitak dobitak=new Dobitak();
	private int[] help=new int[20];
	private Statistika stat;
	

	private TextField broj_komb=new TextField("1");
	
	{
		kreni=new Button("Kreni");
		zaustavi=new Button("Zaustavi");
		stani=new Button("Stani");
		napravi=new Button("Napravi");
		statistika=new Button("Statistika");
		
	}
	
	private void mozeKreni(boolean flag) {
		kreni.setEnabled(flag);
		zaustavi.setEnabled(!flag);	
		stani.setEnabled(!flag);
	}
	
	private void mozeStani(boolean flag) {
		kreni.setEnabled(!flag);
		zaustavi.setEnabled(!flag);
		stani.setEnabled(flag);
	
	}
	public Loto(int kap) {
		super("Loto");
		setSize(1000, 600);
		
		masina=new Masina(kap);
		niz=new int[kap];
		for(int i=0;i<pomoc.length;i++)
			pomoc[i]=0;
		
		labele=new Label[5];
		for(int i=0;i<labele.length;i++) 
			if(i<2)
				labele[i]=new Label("Loto listica sa " + Integer.toString(i+3) + " pogotka ima:");
			else
				labele[i]=new Label("Loto listica sa " + Integer.toString(i+3) + " pogodaka ima:");
	
		
		tekst=new TextField[5];
		for(int i=0;i<tekst.length;i++) {
			tekst[i]=new TextField("");
			tekst[i].setEditable(false);
			tekst[i].setFont(new Font(null, Font.ITALIC, 50));
		}
		
		txt=new TextField[niz.length];
		for(int i=0;i<txt.length;i++) {
			
			txt[i]=new TextField("");
			txt[i].setEditable(false);
		}
		stat=new Statistika();
		popuniProzore();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				masina.unisti();
				dobitak.dispose();
				dispose();
			}
		});
		
		setVisible(true);
	}
	
	private void popuniProzore() {
		Panel ploca=new Panel(new BorderLayout());
		add(ploca);
		Panel plo=new Panel(new BorderLayout());
		plo.add(masina, "North");
		for(int i=0;i<39;i++) {
			paneli[i]=new Panel();
			if(i%3==0)
				tab[i]=new Label(Integer.toString(i+1), Label.RIGHT);
			else 
				if(i%3==1)
					tab[i]=new Label(Integer.toString(i+1), Label.CENTER);
				else 
					tab[i]=new Label(Integer.toString(i+1), Label.LEFT);
					
			tab[i].setBackground(Color.GRAY);
			tab[i].setFont(new Font(null, Font.BOLD,20));
			paneli[i].add(tab[i]);
			tabela.add(paneli[i]);
		}
		plo.add(tabela, "Center");		
		ploca.add(plo, "Center");
		Panel plot=new Panel(new GridLayout(tekst.length, 2));
		ploca.add(plot, "West");
		for(int i=0;i<tekst.length;i++) {
			plot.add(labele[i]);
			plot.add(tekst[i]);
		}
		
		plo=new Panel();
	
	
	
		ploca.add(plo, "South");
	
		label=new Label("Izvuceni su brojevi: ");
		plo.add(label);
		
		for(int i=0;i<txt.length;i++)
			plo.add(txt[i]);
		
		
		plo.add(kreni);
		kreni.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				mozeKreni(false);
				for(int i=0;i<txt.length;i++)
					txt[i].setText("");
				masina.pokreniSve();	
				broj_kombinacija=Integer.parseInt(broj_komb.getText());
				listici.napravi(broj_kombinacija);
				
				for(int i=0;i<tab.length;i++)
					tab[i].setBackground(Color.GRAY);
			
			}
		});
		
		plo.add(zaustavi);
		zaustavi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mozeKreni(true);
				masina.zaustaviBubnjeve();
				niz=masina.niz();
				for(int i=0;i<niz.length;i++) {
					txt[i].setText(Integer.toString(niz[i]));
				}
				
			}
		});
		
		plo.add(stani);
		stani.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
		
				if(brojac==niz.length) {
					mozeStani(false);
					brojac=0;
					
					return;
				}
				else {
					mozeStani(true);
				}
				
				String s=masina.zaustavi(brojac);
				int pom=Integer.parseInt(s);
				tab[pom-1].setBackground(Color.RED);
				listici.pretrazi(pom);
				pomoc=listici.prebroj();
				
			
				for(int i=0;i<pomoc.length;i++)
					tekst[i].setText(Integer.toString(pomoc[i]));
				
				if(brojac==niz.length-1) {
					for(int i=0;i<help.length;i++)
						if(pom==help[i]) {
							dobitak.setVisible(true);
							break;
						}
				}
				
				brojac++;
				if(brojac==niz.length-1) {
					help=new int[listici.vratiBroj()];
					help=listici.pobeda();
					for(int i=0;i<help.length;i++)
						tab[help[i]-1].setBackground(Color.GREEN);
				}
				
				
				if(brojac==niz.length) {
					niz=masina.niz();
					for(int i=0;i<niz.length;i++) {
						txt[i].setText(Integer.toString(niz[i]));
					}
					
					
				}
				
			}
			
		}
		
	
		);
		plo.add(statistika);
		statistika.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
			
				stat.obnovi_statistiku();
				stat.setVisible(true);
				
			}
		});
		plo=new Panel();
		ploca.add(plo, "North");
		plo.add(napravi);
		label_br=new Label("Broj kombinacija: ");
		plo.add(label_br);
		broj_komb.setColumns(10);
		plo.add(broj_komb);
		napravi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				broj_kombinacija=Integer.parseInt(broj_komb.getText());
				listici.napravi(broj_kombinacija);
			}
		});
			
	}
	

	public class Dobitak extends Frame{
		Label lbl;
		
		Dobitak() {
			
		super("LOTO 7/39");
		setBounds(250, 300, 500, 350);
		add(lbl=new Label(" IMAMO LOTO DOBITAK ", Label.CENTER));
		lbl.setFont(new Font(null, Font.BOLD, 40));
		
		this.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
			}
		
		});
		}
	}
	
	public class Statistika extends Frame{
		
		Label[][] lab=new Label[39][2];
	
		
		Statistika() {
			
			super("Statistika");
			setBounds(250, 100, 150, 600);
			Panel plo=new Panel();
			plo.setLayout(new GridLayout(39, 2));
			numbers=masina.niz_stat();
			for(int i=0;i<numbers.length;i++) {
				lab[i][0]=new Label(Integer.toString(i+1));
				plo.add(lab[i][0]);
				lab[i][1]=new Label(Integer.toString(numbers[i]));
				plo.add(lab[i][1]);
			}
			
			add(plo);
			this.addWindowListener(new WindowAdapter() {
				
				@Override
				public void windowClosing(WindowEvent e) {
					setVisible(false);
				}
			
			});
			
		}
		
		public void obnovi_statistiku() {
			for(int i=0;i<numbers.length;i++) {
				lab[i][1].setText(Integer.toString(numbers[i]));
				
		}
	}
	}
	
	
	public static void main(String []args) {
		new Loto(7);
	}
}
