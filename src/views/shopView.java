package views;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import views.view;;


public class shopView {
	JFrame f = new JFrame();
	JPanel p = new JPanel();
	
	String buyText = "�����Ͻðڽ��ϱ�?" + "\n" + "�ݾ� : ";
	int buyCh1, buyCh2, buyCh3, buyCh4, buyCh5, buyCh6;
	int buyitem1, buyitem2;
	int buymap1, buymap2, buymap3;
	int buyhint1, buyhint2, buyhint3;
	
	
	public shopView() {
		p.setLayout(null);
		
		// ������ �ǵ��ư���
		JLabel back = new JLabel(new ImageIcon("imgs/back.png"));
		back.setBounds(120, 70, 75, 75);
		p.add(back);
		
		back.addMouseListener(new MouseAdapter() { 
			@Override
			public void mouseReleased(MouseEvent e) {
				shopView sV = new shopView();
				view v  = new view();
			}
		});
		
		// ĳ����
		JLabel ch1 = new JLabel(new ImageIcon("imgs/shopChar1.png"));
		ch1.setBounds(165, 420, 60, 71);
		p.add(ch1);
		
		ch1.addMouseListener(new MouseAdapter() { 
			@Override
			public void mouseReleased(MouseEvent e) {
				int chk;
				JOptionPane.showMessageDialog(null, "�⺻ �������Դϴ�.");
			}
		});
		
		
		JLabel ch2 = new JLabel(new ImageIcon("imgs/shopChar2.png"));
		ch2.setBounds(280, 420, 60, 71);
		p.add(ch2);
		
		ch2.addMouseListener(new MouseAdapter() { 
			@Override
			public void mouseReleased(MouseEvent e) {
				buyText = ""; 
				String buyText = "�����Ͻðڽ��ϱ�?" + "\n" + "�ݾ� : 50";
				int chk;
				chk = JOptionPane.showConfirmDialog(null, buyText , "����", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if(chk == 0) {
					if(buyCh2 != 0) {
						JOptionPane.showMessageDialog(null, "�� �����ϼ̽��ϴ� ��");
					}
				}
				++buyCh2;
			}
		});
		
		JLabel ch3 = new JLabel(new ImageIcon("imgs/shopChar3.png"));
		ch3.setBounds(400, 420, 60, 71);
		p.add(ch3);
		
		ch3.addMouseListener(new MouseAdapter() { 
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("ĳ����3");
				String buyText = "�����Ͻðڽ��ϱ�?" + "\n" + "�ݾ� : 100";
				int chk;
				chk = JOptionPane.showConfirmDialog(null, buyText , "����", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if(chk == 0) {
					if(buyCh3 != 0) {
						JOptionPane.showMessageDialog(null, "�� �����ϼ̽��ϴ� ��");
					}
				}
				++buyCh3;
			}
		});
		
		
		JLabel ch4 = new JLabel(new ImageIcon("imgs/shopChar4.png"));
		ch4.setBounds(520, 420, 60, 71);
		p.add(ch4);
		
		
		ch4.addMouseListener(new MouseAdapter() { 
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("ĳ����4");
				String buyText = "�����Ͻðڽ��ϱ�?" + "\n" + "�ݾ� : 300";
				int chk;
				chk = JOptionPane.showConfirmDialog(null, buyText , "����", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if(chk == 0) {
					if(buyCh4 != 0) {
						JOptionPane.showMessageDialog(null, "�� �����ϼ̽��ϴ� ��");
					}
				}
				++buyCh4;
			
			}
		});
		
		
		JLabel ch5 = new JLabel(new ImageIcon("imgs/shopChar5.png"));
		ch5.setBounds(630, 420, 120, 71);
		p.add(ch5);
		
		ch5.addMouseListener(new MouseAdapter() { 
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("ĳ����5");
				String buyText = "�����Ͻðڽ��ϱ�?" + "\n" + "�ݾ� : 200";
				int chk;
				chk = JOptionPane.showConfirmDialog(null, buyText , "����", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if(chk == 0) {
					if(buyCh5 != 0) {
						JOptionPane.showMessageDialog(null, "�� �����ϼ̽��ϴ� ��");
					}
				}
				++buyCh5;
			}
		});
		
		
		JLabel ch6 = new JLabel(new ImageIcon("imgs/shopChar6.png"));
		ch6.setBounds(790, 420, 60, 71);
		p.add(ch6);
		
		ch6.addMouseListener(new MouseAdapter() { 
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("ĳ����6");
				String buyText = "�����Ͻðڽ��ϱ�?" + "\n" + "�ݾ� : 250";
				int chk;
				chk = JOptionPane.showConfirmDialog(null, buyText , "����", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if(chk == 0) {
					if(buyCh6 != 0) {
						JOptionPane.showMessageDialog(null, "�� �����ϼ̽��ϴ� ��");
					}
				}
				++buyCh6;
			}
		});
		
		
		// ������ �̹���
		JLabel item1 = new JLabel(new ImageIcon("imgs/bomb.png"));
		item1.setBounds(165, 200, 147, 50);
		p.add(item1);
		
		item1.addMouseListener(new MouseAdapter() { 
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("������1");
				String buyText = "�����Ͻðڽ��ϱ�?" + "\n" + "�ݾ� : 100";
				int chk;
				chk = JOptionPane.showConfirmDialog(null, buyText , "����", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if(chk == 0) {
					if(buyitem1 !=0)
						JOptionPane.showMessageDialog(null, "�� �����ϼ̽��ϴ� ��");
				}
				++buyitem1;
			}
		});
		
		JLabel item2 = new JLabel(new ImageIcon("imgs/barrierImg.png"));
		item2.setBounds(165, 270, 147, 50);
		p.add(item2);
		
		item2.addMouseListener(new MouseAdapter() { 
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("������2");
				String buyText = "�����Ͻðڽ��ϱ�?" + "\n" + "�ݾ� : 100";
				int chk;
				chk = JOptionPane.showConfirmDialog(null, buyText , "����", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if(chk == 0) {
					if(buyitem2 !=0)
						JOptionPane.showMessageDialog(null, "�� �����ϼ̽��ϴ� ��");
				}
				++buyitem2;
			}
		});
	
		
		// �� �̹���
		JLabel map1 = new JLabel(new ImageIcon("imgs/shopMap1.png"));
		map1.setBounds(400, 200, 177, 50);
		p.add(map1);
		
		map1.addMouseListener(new MouseAdapter() { 
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("��1");
				String buyText = "�����Ͻðڽ��ϱ�?" + "\n" + "�ݾ� : 0";
				int chk;
				JOptionPane.showMessageDialog(null, "�⺻ �������Դϴ�.");
				++buymap1;
			}
		});
		
		JLabel map2 = new JLabel(new ImageIcon("imgs/shopMap2.png"));
		map2.setBounds(400, 270, 177, 50);
		p.add(map2);
		
		map2.addMouseListener(new MouseAdapter() { 
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("��2");
				String buyText = "�����Ͻðڽ��ϱ�?" + "\n" + "�ݾ� : 300";
				int chk;
				chk = JOptionPane.showConfirmDialog(null, buyText , "����", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if(chk == 0) {
					if(buymap2 !=0)
						JOptionPane.showMessageDialog(null, "�� �����ϼ̽��ϴ� ��");
				}
				++buymap2;
			}
		});
		
		JLabel map3 = new JLabel(new ImageIcon("imgs/shopMap3.png"));
		map3.setBounds(400, 340, 177, 50);
		p.add(map3);
		
		map3.addMouseListener(new MouseAdapter() { 
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("��3");
				String buyText = "�����Ͻðڽ��ϱ�?" + "\n" + "�ݾ� : 500";
				int chk;
				chk = JOptionPane.showConfirmDialog(null, buyText , "����", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if(chk == 0) {
					if(buymap3 !=0)
						JOptionPane.showMessageDialog(null, "�� �����ϼ̽��ϴ� ��");
				}
				++buymap3;
			}
		});
		
		// ��Ʈ �̹���
		JLabel hint1 = new JLabel(new ImageIcon("imgs/shopHint1.png"));
		hint1.setBounds(650, 200, 177, 50);
		p.add(hint1);
		
		hint1.addMouseListener(new MouseAdapter() { 
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("��Ʈ1");
				String buyText = "�����Ͻðڽ��ϱ�?" + "\n" + "�ݾ� : 500";
				int chk;
				chk = JOptionPane.showConfirmDialog(null, buyText , "����", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if(chk == 0) {
					if(buyhint1 !=0)
						JOptionPane.showMessageDialog(null, "�� �����ϼ̽��ϴ� ��");
				}
				++buyhint1;
			}
		});
		
		JLabel hint2 = new JLabel(new ImageIcon("imgs/shopHint2.png"));
		hint2.setBounds(650, 270, 177, 50);
		p.add(hint2);
		
		hint2.addMouseListener(new MouseAdapter() { 
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("��Ʈ2");
				String buyText = "�����Ͻðڽ��ϱ�?" + "\n" + "�ݾ� : 600";
				int chk;
				chk = JOptionPane.showConfirmDialog(null, buyText , "����", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if(chk == 0) {
					if(buyhint2 !=0)
						JOptionPane.showMessageDialog(null, "�� �����ϼ̽��ϴ� ��");
				}
				++buyhint2;
			}
		});
		
		JLabel hint3 = new JLabel(new ImageIcon("imgs/shopHint3.png"));
		hint3.setBounds(650, 340, 177, 50);
		p.add(hint3);
		
		hint3.addMouseListener(new MouseAdapter() { 
			@Override
			public void mouseReleased(MouseEvent e) {
				System.out.println("��Ʈ3");
				String buyText = "�����Ͻðڽ��ϱ�?" + "\n" + "�ݾ� : 700";
				int chk;
				chk = JOptionPane.showConfirmDialog(null, buyText , "����", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if(chk == 0) {
					if(buyhint3 !=0)
						JOptionPane.showMessageDialog(null, "�� �����ϼ̽��ϴ� ��");
				}
				++buyhint3;
			}
		});
		
		
		// ���
		JLabel screenBg = new JLabel(new ImageIcon("imgs/screenBg2.png"));
		screenBg.setBounds(10, 10, 963, 563);
		p.add(screenBg);
		
		JLabel bg = new JLabel(new ImageIcon("imgs/bgScreen.png"));
		bg.setBounds(0, 0, 1000, 600);
		p.add(bg);
		

		f.add(p);

		f.setSize(1000,600);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle("������ ���");
		f.setVisible(true);
		
	}
	
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	new shopView();
            }
        });
	}
}
