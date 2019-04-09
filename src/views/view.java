package views;

import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import audios.BGMusic;
import constant.constant;

public class view{
	JLayeredPane layeredPane;
	JFrame f = new JFrame();
	JPanel p = new JPanel();
	
	JButton btn1 = new JButton("�Ͻ� ����");
	JButton btn2 = new JButton("�� ����"); 
	JButton btn3 = new JButton("on / off"); 

	ImageIcon icon;
	
	public view(){
			BGMusic bgm = new BGMusic();
			bgm.music(); // bgm ����
			p.setLayout(null);

//			              ******* ����� �̹��� ��� ����!!!!!!!!!!! *******
			
			//���� ������
			JLabel startImg = new JLabel(new ImageIcon("imgs/startImg.png"));
			startImg.setBounds(constant.btn1x, constant.mainViewIconY, 150, 200);
			p.add(startImg);
			
			startImg.addMouseListener(new MouseAdapter() { 
				@Override
				public void mouseReleased(MouseEvent e) {
					System.out.println("����");
					
					startView sV = new startView();
					
				}
			});
			
			//���� ������
			JLabel explainImg = new JLabel(new ImageIcon("imgs/explainImg.png"));
			explainImg.setBounds(constant.btn2x, constant.mainViewIconY, 150, 200);
			p.add(explainImg);
			
			explainImg.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					System.out.println("����");
					explainView eV  = new explainView();
				}
				

			});
			
			//��ŷ ������
			JLabel rankingImg = new JLabel(new ImageIcon("imgs/rankingImg.png"));
			rankingImg.setBounds(constant.btn3x, constant.mainViewIconY, 150, 200);
			p.add(rankingImg);
			
			rankingImg.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					System.out.println("��ŷ");
					rankingView rV = new rankingView();
				}
			});
			
			//���� ������
			JLabel musicImg = new JLabel(new ImageIcon("imgs/musicIcon.png"));
			musicImg.setBounds(850, 30, 100, 100);
			p.add(musicImg);
			
			musicImg.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					System.out.println("����");
					bgm.music();
				}
			});
			

			// �̸� textField
			Label nameLabel = new Label("  ���̵� : ");
			TextField userTextField = new TextField(10);

			
			nameLabel.setBackground(Color.WHITE);
			nameLabel.setBounds(300, 480,60, 30);
			nameLabel.setForeground(Color.white);
			nameLabel.setBackground(Color.DARK_GRAY);
			
			userTextField.setBounds(370, 480, 270 ,30);
			
			
//			nameOk.setBackground(Color.WHITE);
//			nameOk.setBounds(650, 480, 60, 30);
			
			JButton nameOk = new JButton("Ȯ��");
			nameOk.setForeground(Color.white);
			nameOk.setBackground(Color.DARK_GRAY);
		    Border line = new LineBorder(Color.DARK_GRAY);
		    Border margin = new EmptyBorder(5, 15, 5, 15);
		    Border compound = new CompoundBorder(line, margin);
		    nameOk.setBorder(compound);
		    
		    nameOk.setBounds(650, 480, 60, 30);
		    
		    p.add(nameLabel);
			p.add(userTextField);
			p.add(nameOk);
			
			
			//���
			JLabel bg = new JLabel(new ImageIcon("imgs/bg.png"));
			bg.setBounds(0, 0, 1000, 600);

			p.add(bg);
			f.add(p);

			
			f.setSize(1000,600);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.setTitle("������ ���");
			f.setVisible(true);
			icon = new ImageIcon("imgs/icon.jpg");
			f.setIconImage(icon.getImage());

	}
	
}
