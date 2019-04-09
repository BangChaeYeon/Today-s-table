package views;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class scoreView {
	JFrame f = new JFrame();
	JPanel p = new JPanel();
	
	
	public scoreView() {
		
		p.setLayout(null);
		
		JLabel coin = new JLabel(new ImageIcon("imgs/coin3.png"));
		coin.setBounds(380, 260, 50, 50);
		p.add(coin);

//		System.out.println(score);
//		@Override
//        protected void paintComponent(Graphics g) {
//			
//		}
		
		

		JLabel home = new JLabel(new ImageIcon("imgs/homeIcon.png"));
		home.setBounds(145, 80, 75, 75);
		p.add(home);
		
		home.addMouseListener(new MouseAdapter() { 
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("»®");
				view vi = new view();
				
			}
		});
		
		JLabel ranking = new JLabel(new ImageIcon("imgs/rankingIcon.png"));
		ranking.setBounds(775, 80, 75, 75);
		p.add(ranking);
		
		ranking.addMouseListener(new MouseAdapter() { 
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("∑©≈∑");
			}
		});
		
		JLabel map = new JLabel(new ImageIcon("imgs/mapIcon.png"));
		map.setBounds(250, 380, 100, 100);
		p.add(map);
		
		map.addMouseListener(new MouseAdapter() { 
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("∏ ");
			}
		});
		
		JLabel restart = new JLabel(new ImageIcon("imgs/restartIcon.png"));
		restart.setBounds(450, 380, 100, 100);
		p.add(restart);
		
		restart.addMouseListener(new MouseAdapter() { 
			@Override
			public void mouseReleased(MouseEvent e) {
				startView sV = new startView();
				System.out.println("¿ÁΩ√¿€");
			}
		});
		
		JLabel shop = new JLabel(new ImageIcon("imgs/shopIcon.png"));
		shop.setBounds(650, 380, 100, 100);
		p.add(shop);
		shop.addMouseListener(new MouseAdapter() { 
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("º•");
				shopView sV = new shopView();
			}
		});
		
		JLabel screenBg = new JLabel(new ImageIcon("imgs/screenBg.png"));
		screenBg.setBounds(10, 10, 963, 563);
		p.add(screenBg);
		
		JLabel bg = new JLabel(new ImageIcon("imgs/bgScreen.png"));
		bg.setBounds(0, 0, 1000, 600);
		p.add(bg);
		

		f.add(p);

		f.setSize(1000,600);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle("ø¿¥√¿« π‰ªÛ");
		f.setVisible(true);
		
	}
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	new scoreView();
            }
        });
	}
}
