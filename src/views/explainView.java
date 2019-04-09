package views;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class explainView {
	JFrame f = new JFrame();
	JPanel p = new JPanel();
	int num = 1;
	
	
	public explainView() {
		
		JLabel bg = new JLabel(new ImageIcon("imgs/explainBg.jpg"));
		bg.setBounds(0, 0, 1000, 600);
		p.add(bg);

		
		f.add(p);

		f.setSize(1000,600);
//		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle("ø¿¥√¿« π‰ªÛ");
		f.setVisible(true);
	}

}
