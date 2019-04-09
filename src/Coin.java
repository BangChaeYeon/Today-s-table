import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Coin {
	Image coin, ch;
	BufferedImage obs;
	
	Boolean jumping = false;
	Boolean crashing = false;
	
	int property = 0;
	int  y1 = 0, y2 = 0;
	int health = 100;
	
	Timer timer;
	TimerTask timerTask;
	
	JFrame frame;
	
	public Coin() throws IOException{
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new CoinImage());
        frame.setSize(new Dimension(1000, 600));
        frame.setVisible(true);
	}
	
	private class CoinImage extends JPanel{
		
		public CoinImage() throws IOException {
			coin = new ImageIcon(("imgs/coin.gif")).getImage();
			ch = new ImageIcon(("imgs/chick3.gif")).getImage();
			obs = ImageIO.read(new File("imgs/obstacle01.png"));
	    	
	    	frame.addKeyListener(new KeyAdapter() {
				
				@Override
				public void keyPressed(KeyEvent e) {
					// �����̽��ٸ� ���� �� ����
					if(e.getKeyCode() == KeyEvent.VK_SPACE) {
						
						timer = new Timer(true); // util Ÿ�̸� ��ü
						timerTask = new TimerTask() { // ���� �ִϸ��̼��� ������
							@Override
							public void run() {
								// ĳ������ ��ġ�� �ְ����� ��� y���� �������Ѽ� ���������� ���̰� ��
								if (400+y1 <=  50 && y2 != 400) { // y2�� 400�̸� �������̹Ƿ� ����X
									y2 += 5; 
									jumping = false; // ���� ���°� �ƴ��� ��Ÿ��
									repaint();
									CrashCheck(50+y2); // ����ؼ� �ٲ�� ĳ������ y ���� ������
								}
								
								else {
									y1-=5; 
									jumping = true; // ���� ������ ����ǰ� ������ ��Ÿ��
									repaint();
									CrashCheck(400+y1);
								}
								
								// ĳ���Ͱ� ���� �� �������� ���� �����Ƿ� ���� ������ ���� timerTask�� ���� ��Ű�� 
								// ���� ��ü ������ ���� null�� ������
								if(y2 == 400) { 		
									// �浹 ���� �ľ� �� ü�� ����
									if(crashing == true) {
										health -= 10;
										crashing = false;
									}
									// ü���� 0�϶� ���ӿ��� -> ��� â �����ؾ� ��
									if(health == 0) {
										System.out.println("Game Over");
									}
									
									//Ȯ�ο�
									System.out.println("Your health: " + health);
									
									timer.cancel();
									timer.purge();
									timer = null;
									timerTask = null;
									y1 = 0;
									y2 = 0;
								}
							}
						};
						// 0.5�� ���� 0.07���� �������� timerTask�� �����Ŵ
						timer.schedule(timerTask, 500, 7); 
					}
				}
			});		
		}
		
		@Override
        protected void paintComponent(Graphics g) { // frame�� ���� �̹����� �׷���
        	super.paintComponent(g);
        	g.setColor(Color.WHITE);
            g.fillRect(0, 0, getWidth(), getHeight());

            g.drawImage(coin, 30, 30, 200, 200, this);
            g.drawImage(obs, 50, 50, obs.getWidth(), obs.getHeight(), this);
            
            if(jumping == true) // ������ �� y���� ��� �����ϴ� ���� ������(�ö�)
            	g.drawImage(ch, 30, 400+y1, 140, 128, this);
            
            else if(y1 == 0 && y2 == 0) // ó�� ������ �� �׷���
            	g.drawImage(ch, 30, 400, 140, 128, this);
         
            else g.drawImage(ch, 30, 50+y2, 140, 128, this); // �ְ����� ������ �� y���� �����ϴ� ���� ������(������)
        }
	} // CoinImage
	
	public void CrashCheck(int y) { // �浹 Ȯ��
		int coinxl = 30, coinxr = 30 + 200, coinyl = 30, coinyr = 30 + 200;
		int chxl = 30, chxr = 30 + 140, chyl = y, chyr = y + 128;
		int obsxl = 50, obsxr = 50 + obs.getWidth(), obsyl = 50, obsyr = 50 + obs.getHeight();
		
		// coin ȹ�� �̺�Ʈ
		if((chxl < coinxr) && (coinxl < chxr) && (chyl < coinyr) && (coinyl < chyr)) {
			if(coin != null) property += 1;
			coin = null;
		}
		
		// ��ֹ� �浹 �̺�Ʈ -> ü�� ����
		if((chxl < obsxr) && (obsxl < chxr) && (chyl < obsyr) && (obsyl < chyr)) {
			crashing = true; // �浹 ���θ� üũ��
		}
		
		// Ȯ�� ��
//		System.out.println("Your property: " + property);
		
//		System.out.println("chxl: " + chxl + "\nchxr: " + chxr + "\nchyl : " + chyl  + "\nchyr: " + chyr);
//		System.out.println("obsxl: " + obsxl + "\nobsxr: " + obsxr + "\nobsyl: " + obsyl + "\nobsyr: " + obsyr);
		
	} // CrashChck
	
	public static void main(String[] args) throws IOException {	
		new Coin();
	}
}
