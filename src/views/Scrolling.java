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
	long start, now; // 시간을 받는 변수
	Boolean jumping = false;
	Boolean crashing = false;
	
	int savetime = 0; // 지나간 시간을 초 단위로 갖고 있는 변수
	int score = 0; // 점수
	int scoreNum = 50; // 시간 당 점수
	int health = 100; // 체력	
	int  y1 = 0, y2 = 0;
	int damage = 10; // 장애물 충돌 시 데미지
	int charNum = 6; // 캐릭터 번호 (1 ~ 6)
	int coinNum = 1; // 얻게되는 코인양
	int wealth = 0; // 총 얻은 코인
	
	TimerCopy timer2;
	TimerTaskCopy timerTask2;
	
	 JFrame frame;

	public Scrolling() throws IOException {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 차후에 제거
        frame.add(new AnimatingPanel());
        frame.pack();
        frame.setVisible(true);
        
        // 캐릭터 마다 특수 능력 제공
        switch(charNum) {
			case 2: // 양념 치킨
			    damage = 5;
			    break;
			case 3: // 간장 치킨
				scoreNum = 75;
				break;
			case 4: // 황금 올리브 치킨
				coinNum = 2;
				break;
			case 5: // 간장 반 양념 반
				damage = 5;
				scoreNum = 75;
				break;
			case 6: // 생 닭
				health = 150;
				break;
        }
        
        start = System.currentTimeMillis(); // 프로그램 시작 시간을 받음
    }
	
	private class AnimatingPanel extends JPanel {
        private static final int DIM_W = 1000; // 화면 x에서부터 width을 얼마만큼 보여지는지
        private static final int DIM_H = 600;  // 화면 y에서부터 height을 얼마만큼 보여지는지
        private int INCREMENTBG = 5; // background의 속도
        private int INCREMENTTILE = 40; // tile의 속도

        private BufferedImage tile, tile2;
        private BufferedImage backgroundImage, backgroundImage2;
        private BufferedImage obs1;
        private BufferedImage heart, faster, obs;
        private Image runnerImage, coin;

        private int bg1xl, bg2xl, bg1xr, bg2xr, tile1xl, tile2xl, tile1xr, tile2rx; // initImagePoints 참조
        private int obs1xl, obs1xr;
        private int IMAGE_WIDTH; 

        int time = 40; // 40이 이상적인 속도 //반복횟수?
        
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
					// 스페이스바를 누를 시 실행
					if(e.getKeyCode() == KeyEvent.VK_SPACE) {
						timer2 = new TimerCopy(true); // util 타이머 객체
						timerTask2 = new TimerTaskCopy() { // 점프 애니메이션을 보여줌
							@Override
							public void run() {
								// 캐릭터의 위치가 최고점일 경우 y값을 증가시켜서 내려오도록 보이게 함
								if (350+y1 <=  50 && y2 != 350) { // y2가 350이면 최저점이므로 실행X
									y2 += 5; 
									jumping = false; // 점프 상태가 아님을 나타냄
									repaint();
									CrashCheck(50+y2); // 계속해서 바뀌는 캐릭터의 y 값을 전달함
								}
								
								else {
									y1-=5; 
									jumping = true; // 현재 점프가 진행되고 있음을 나타냄
									repaint();
									CrashCheck(350+y1);
								}
								
								// 캐릭터가 점프 후 최저점에 도달 했으므로 다음 점프를 위해 timerTask를 정지 시키고 
								// 다음 객체 생성을 위해 null을 대입함
								if(y2 == 350) { 		
									// 충돌 여부 파악 후 체력 감소
									if(crashing == true) {
										health -= damage;
										crashing = false;
									}
									// 체력이 0일때 게임오버 -> 결과 창 실행해야 함
									if(health == 0) {
										System.out.println("Game Over");
									}
									
									timer2.cancel();
									timer2.purge();
									timer2= null;
									timerTask2 = null;
									y1 = 0;
									y2 = 0;
									
									// 확인용
									System.out.println("your health: " + health); // 체력 출력
									System.out.println("score: " + score); // 점수 출력
									System.out.println("savetime: " + savetime); // 지나간 시간 출력
									System.out.println("wealth: " + wealth); // 현재 보유 자산 출력
								}
							}
						};
						// 0.5초 동안 0.07초의 간격으로 timerTask를 실행시킴
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
            
            if(jumping == true) // 점프할 시 y값이 계속 감소하는 것을 보여줌(올라감)
            	g.drawImage(runnerImage, 80, 350+y1, 140, 128, this);
            
            else if(y1 == 0 && y2 == 0) // 처음 시작할 때 그려줌
            	g.drawImage(runnerImage, 80, 350, 140, 128, this);
         
            else g.drawImage(runnerImage, 80, 50+y2, 140, 128, this); // 최고점을 도달한 후 y값이 증가하는 것을 보여줌(떨어짐)
            
            g.drawImage(obs1, obs1xl, 300, this);
        }
        
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(1000, 600);
        }
        
    	public void CrashCheck(int y) { // 충돌 확인
    		int fasterxl = 80, fasterxr = 80 + 100, fasteryl = 50, fasteryr = 50 + 100;
    		int chxl = 80, chxr = 80 + 140, chyl = y, chyr = y + 128;
    		int heartxl = 80, heartxr = 80 + 100, heartyl = 50, heartyr = 50 + 100;
    		int coinxl = 80, coinxr = 80 + 100, coinyl = 100, coinyr = 100 + 100;
    		int obsxl = 80, obsxr = 80 + obs.getWidth(), obsyl = 50, obsyr = 50 + obs.getHeight();
    		
    		// 스피드 업 아이템 획득 이벤트
    		if((chxl < fasterxr) && (fasterxl < chxr) && (chyl < fasteryr) && (fasteryl < chyr)) {
    			if(faster != null) {	  // 스크롤링 속도를 빠르게 함
    				INCREMENTBG += 5; 
    			}
    			faster = null;
    		}
    		
    		// 체력회복 아이템 획득 이벤트
    		if((chxl < heartxr) && (heartxl < chxr) && (chyl < heartyr) && (heartyl < chyr)) {
    			if(heart != null) health += 10; // 체력을 증가 시켜줌
    			heart = null;
    		}
    		
    		// coin 획득 이벤트
    		if((chxl < coinxr) && (coinxl < chxr) && (chyl < coinyr) && (coinyl < chyr)) {
    			if(coin != null) wealth += coinNum;
    			coin = null;
    		}
    		
    		// 장애물 충돌 이벤트 -> 체력 감소
    		if((chxl < obsxr) && (obsxl < chxr) && (chyl < obsyr) && (obsyl < chyr)) {
    			crashing = true; // 충돌 여부를 체크함
    		}

    	} // CrashChck
        
        private void initImagePoints() {
        	bg1xl = 0; // 첫번재 background의 x좌표
        	bg2xl = 2000; // 두 번재 background의 x좌표
        	bg1xr = 2000; // 첫번재 background의 x좌표의 끝 (이미지 오른쪽 좌표?)
        	bg2xr = 4000; // 첫번재 background의 x좌표의 끝 (이미지 오른쪽 좌표?)
        	
        	tile1xl = 0;
        	tile2xl = 2000;
        	tile1xr = 2000; 
        	tile2rx = 4000;
        	
        	obs1xl = 300;
        	obs1xr = 358;

        	// tile이랑 bg이랑 속도 다르게 계산
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

	    	bg1xl -= INCREMENTBG; // bg1의 왼쪽 x좌표 감소
	    	bg2xl -= INCREMENTBG; // bg2의 왼쪽 y좌표 감소
	    	bg1xr -= INCREMENTBG; // bg1의 오른쪽 x좌표 감소
	    	bg2xr -= INCREMENTBG; // bg2의 오른쪽 y좌표 감소
	    	
	    	
	    	obs1xl -= INCREMENTBG;
	    	obs1xr -= INCREMENTBG;
	    	
	    	if(obs1xr == 0) { 
	    		 obs1 = null;
	    	}
	    	
			now = System.currentTimeMillis(); // 현재 시간을 입력 받음

			// 5초가 지날 때 마다 score가 50씩 증가함
			savetime = (int) ((now-start) / 1000); // 프로그램 시작 후 지난 시간을 초 단위로 받음
			score = scoreNum * (savetime/5); // 지난 시간을 time으로 받고 5초마다 50씩 오르게 함
	    	
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

	    	tile1xl -= INCREMENTTILE; // tile1 왼쪽 x좌표 감소
	    	tile2xl -= INCREMENTTILE; // tile2 왼쪽 x좌표 감소
	    	tile1xr -= INCREMENTTILE; // tile1 오른쪽x좌표 감소
	    	tile2rx -= INCREMENTTILE; // tile1 오른쪽x좌표 감소
	    	
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
