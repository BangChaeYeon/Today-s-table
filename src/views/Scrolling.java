//package views;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

//import audios.BGMusic;
import constant.TimerCopy;
import constant.TimerTaskCopy;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.TimerTask;

//import constant.constant;

public class Scrolling{
	long start, now; // �ð��� �޴� ����
	Boolean jumping = false;
	Boolean crashing = false;
	
	int savetime = 0; // ������ �ð��� �� ������ ���� �ִ� ����
	int score = 0; // ����
	int scoreNum = 50; // �ð� �� ����
	int health = 100; // ü��	
	int  y1 = 0, y2 = 0;
	int damage = 10; // ��ֹ� �浹 �� ������
	int charNum = 6; // ĳ���� ��ȣ (1 ~ 6)
	int coinNum = 1; // ��ԵǴ� ���ξ�
	int wealth = 0; // �� ���� ����
	
	TimerCopy timer2;
	TimerTaskCopy timerTask2;
	
	 JFrame frame;

	public Scrolling() throws IOException {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ���Ŀ� ����
        frame.add(new AnimatingPanel());
        frame.pack();
        frame.setVisible(true);
        
        // ĳ���� ���� Ư�� �ɷ� ����
        switch(charNum) {
			case 2: // ��� ġŲ
			    damage = 5;
			    break;
			case 3: // ���� ġŲ
				scoreNum = 75;
				break;
			case 4: // Ȳ�� �ø��� ġŲ
				coinNum = 2;
				break;
			case 5: // ���� �� ��� ��
				damage = 5;
				scoreNum = 75;
				break;
			case 6: // �� ��
				health = 150;
				break;
        }
        
        start = System.currentTimeMillis(); // ���α׷� ���� �ð��� ����
    }
	
	private class AnimatingPanel extends JPanel {
        private static final int DIM_W = 1000; // ȭ�� x�������� width�� �󸶸�ŭ ����������
        private static final int DIM_H = 600;  // ȭ�� y�������� height�� �󸶸�ŭ ����������
        private int INCREMENTBG = 5; // background�� �ӵ�
        private int INCREMENTTILE = 40; // tile�� �ӵ�

        private BufferedImage tile, tile2;
        private BufferedImage backgroundImage, backgroundImage2;
        private BufferedImage obs1;
        private BufferedImage heart, faster, obs;
        private Image runnerImage, coin;

        private int bg1xl, bg2xl, bg1xr, bg2xr, tile1xl, tile2xl, tile1xr, tile2rx; // initImagePoints ����
        private int obs1xl, obs1xr;
        private int IMAGE_WIDTH; 

        int time = 40; // 40�� �̻����� �ӵ� //�ݺ�Ƚ��?
        
        public AnimatingPanel() {
            initImages();
            initImagePoints();
            
            Timer timer = new Timer(time, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    moveBackground();
                    moveTile();
                    repaint();
                }
            });
            
	        timer.start();
	        
	        FlowLayout layout = (FlowLayout)getLayout();
	        layout.setHgap(0);
	        layout.setVgap(0);

	        frame.addKeyListener(new KeyAdapter() {
				
				@Override
				public void keyPressed(KeyEvent e) {
					// �����̽��ٸ� ���� �� ����
					if(e.getKeyCode() == KeyEvent.VK_SPACE) {
						timer2 = new TimerCopy(true); // util Ÿ�̸� ��ü
						timerTask2 = new TimerTaskCopy() { // ���� �ִϸ��̼��� ������
							@Override
							public void run() {
								// ĳ������ ��ġ�� �ְ����� ��� y���� �������Ѽ� ���������� ���̰� ��
								if (350+y1 <=  50 && y2 != 350) { // y2�� 350�̸� �������̹Ƿ� ����X
									y2 += 5; 
									jumping = false; // ���� ���°� �ƴ��� ��Ÿ��
									repaint();
									CrashCheck(50+y2); // ����ؼ� �ٲ�� ĳ������ y ���� ������
								}
								
								else {
									y1-=5; 
									jumping = true; // ���� ������ ����ǰ� ������ ��Ÿ��
									repaint();
									CrashCheck(350+y1);
								}
								
								// ĳ���Ͱ� ���� �� �������� ���� �����Ƿ� ���� ������ ���� timerTask�� ���� ��Ű�� 
								// ���� ��ü ������ ���� null�� ������
								if(y2 == 350) { 		
									// �浹 ���� �ľ� �� ü�� ����
									if(crashing == true) {
										health -= damage;
										crashing = false;
									}
									// ü���� 0�϶� ���ӿ��� -> ��� â �����ؾ� ��
									if(health == 0) {
										System.out.println("Game Over");
									}
									
									timer2.cancel();
									timer2.purge();
									timer2= null;
									timerTask2 = null;
									y1 = 0;
									y2 = 0;
									
									// Ȯ�ο�
									System.out.println("your health: " + health); // ü�� ���
									System.out.println("score: " + score); // ���� ���
									System.out.println("savetime: " + savetime); // ������ �ð� ���
									System.out.println("wealth: " + wealth); // ���� ���� �ڻ� ���
								}
							}
						};
						// 0.5�� ���� 0.07���� �������� timerTask�� �����Ŵ
						timer2.schedule(timerTask2, 500, 7); 
					}
				}
			});		
	        
	        
	    } // AnimatingPanel
        
	
        @Override
        protected void paintComponent(Graphics g) {
        	super.paintComponent(g);
        	g.setColor(Color.WHITE);
            g.fillRect(0, 0, getWidth(), getHeight());

            g.drawImage(backgroundImage2, bg1xl, 0, backgroundImage2.getWidth(), backgroundImage2.getHeight(), this);
            g.drawImage(backgroundImage2, bg2xl, 0, backgroundImage2.getWidth(), backgroundImage2.getHeight(), this);
            g.drawImage(tile, tile1xl, 30, tile.getWidth(), tile.getHeight(), this);
            g.drawImage(tile2, tile2xl, 30, tile.getWidth(), tile.getHeight(), this);
            
            g.drawImage(heart, 80, 50, 100, 100, this);
            g.drawImage(faster, 80, 50, 100, 100, this);
            g.drawImage(coin, 80, 100, 100, 100, this);
            g.drawImage(obs, 80, 50, obs.getWidth(), obs.getHeight(), this);
            
//            g.drawImage(runnerImage, 80, 350, 140, 128, this);
            
            if(jumping == true) // ������ �� y���� ��� �����ϴ� ���� ������(�ö�)
            	g.drawImage(runnerImage, 80, 350+y1, 140, 128, this);
            
            else if(y1 == 0 && y2 == 0) // ó�� ������ �� �׷���
            	g.drawImage(runnerImage, 80, 350, 140, 128, this);
         
            else g.drawImage(runnerImage, 80, 50+y2, 140, 128, this); // �ְ����� ������ �� y���� �����ϴ� ���� ������(������)
            
            g.drawImage(obs1, obs1xl, 300, this);
        }
        
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(1000, 600);
        }
        
    	public void CrashCheck(int y) { // �浹 Ȯ��
    		int fasterxl = 80, fasterxr = 80 + 100, fasteryl = 50, fasteryr = 50 + 100;
    		int chxl = 80, chxr = 80 + 140, chyl = y, chyr = y + 128;
    		int heartxl = 80, heartxr = 80 + 100, heartyl = 50, heartyr = 50 + 100;
    		int coinxl = 80, coinxr = 80 + 100, coinyl = 100, coinyr = 100 + 100;
    		int obsxl = 80, obsxr = 80 + obs.getWidth(), obsyl = 50, obsyr = 50 + obs.getHeight();
    		
    		// ���ǵ� �� ������ ȹ�� �̺�Ʈ
    		if((chxl < fasterxr) && (fasterxl < chxr) && (chyl < fasteryr) && (fasteryl < chyr)) {
    			if(faster != null) {	  // ��ũ�Ѹ� �ӵ��� ������ ��
    				INCREMENTBG += 5; 
    			}
    			faster = null;
    		}
    		
    		// ü��ȸ�� ������ ȹ�� �̺�Ʈ
    		if((chxl < heartxr) && (heartxl < chxr) && (chyl < heartyr) && (heartyl < chyr)) {
    			if(heart != null) health += 10; // ü���� ���� ������
    			heart = null;
    		}
    		
    		// coin ȹ�� �̺�Ʈ
    		if((chxl < coinxr) && (coinxl < chxr) && (chyl < coinyr) && (coinyl < chyr)) {
    			if(coin != null) wealth += coinNum;
    			coin = null;
    		}
    		
    		// ��ֹ� �浹 �̺�Ʈ -> ü�� ����
    		if((chxl < obsxr) && (obsxl < chxr) && (chyl < obsyr) && (obsyl < chyr)) {
    			crashing = true; // �浹 ���θ� üũ��
    		}

    	} // CrashChck
        
        private void initImagePoints() {
        	bg1xl = 0; // ù���� background�� x��ǥ
        	bg2xl = 2000; // �� ���� background�� x��ǥ
        	bg1xr = 2000; // ù���� background�� x��ǥ�� �� (�̹��� ������ ��ǥ?)
        	bg2xr = 4000; // ù���� background�� x��ǥ�� �� (�̹��� ������ ��ǥ?)
        	
        	tile1xl = 0;
        	tile2xl = 2000;
        	tile1xr = 2000; 
        	tile2rx = 4000;
        	
        	obs1xl = 300;
        	obs1xr = 358;

        	// tile�̶� bg�̶� �ӵ� �ٸ��� ���
        }
        
        private void initImages() {
            try {
                runnerImage = new ImageIcon("imgs/chick" + charNum + ".gif").getImage();
                tile = ImageIO.read(new File("imgs/tile2.png"));
                tile2 = ImageIO.read(new File("imgs/tile2.png"));
                
                backgroundImage = ImageIO.read(new File("imgs/background.png")); 	//background = 2000, background2 = 1000
                backgroundImage2 = ImageIO.read(new File("imgs/background.png")); 		
                
                obs = ImageIO.read(new File("imgs/obstacle01.png"));
                coin =  new ImageIcon("imgs/coin.gif").getImage();
                heart = ImageIO.read(new File("imgs/heart.png"));
                faster = ImageIO.read(new File("imgs/faster.png"));
                
                IMAGE_WIDTH = backgroundImage.getWidth();

            } catch (Exception ex) {
            	System.out.println("img_error");
                ex.printStackTrace();
            }
       } // initImages
    
	    private void moveBackground() {
	    	if(bg1xr == 0) { 
	    		bg1xl = 2000; 
	    		bg1xr = 4000; 
	    	}
	    	if(bg2xr == 0 ) { 
	    		bg2xl = 2000; 
	    		bg2xr = 4000;
	    	}

	    	bg1xl -= INCREMENTBG; // bg1�� ���� x��ǥ ����
	    	bg2xl -= INCREMENTBG; // bg2�� ���� y��ǥ ����
	    	bg1xr -= INCREMENTBG; // bg1�� ������ x��ǥ ����
	    	bg2xr -= INCREMENTBG; // bg2�� ������ y��ǥ ����
	    	
	    	
	    	obs1xl -= INCREMENTBG;
	    	obs1xr -= INCREMENTBG;
	    	
	    	if(obs1xr == 0) { 
	    		 obs1 = null;
	    	}
	    	
			now = System.currentTimeMillis(); // ���� �ð��� �Է� ����

			// 5�ʰ� ���� �� ���� score�� 50�� ������
			savetime = (int) ((now-start) / 1000); // ���α׷� ���� �� ���� �ð��� �� ������ ����
			score = scoreNum * (savetime/5); // ���� �ð��� time���� �ް� 5�ʸ��� 50�� ������ ��
	    	
	    } //moveBackground
	    
	    private void moveTile() {
	    	if(tile1xr == 0) {
	    		tile1xl = 2000 - INCREMENTTILE;
	    		tile1xr = 4000 - INCREMENTTILE;
	    	}
	    	if(tile2rx == 0) {
	    		tile2xl = 2000 - INCREMENTTILE;
	    		tile2rx = 4000 - INCREMENTTILE;
	    	}

	    	tile1xl -= INCREMENTTILE; // tile1 ���� x��ǥ ����
	    	tile2xl -= INCREMENTTILE; // tile2 ���� x��ǥ ����
	    	tile1xr -= INCREMENTTILE; // tile1 ������x��ǥ ����
	    	tile2rx -= INCREMENTTILE; // tile1 ������x��ǥ ����
	    	
	    } // moveTile

            
    }// AnimatingPanel
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
					new Scrolling();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
	}
}
