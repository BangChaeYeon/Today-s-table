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
					// 스페이스바를 누를 시 실행
					if(e.getKeyCode() == KeyEvent.VK_SPACE) {
						
						timer = new Timer(true); // util 타이머 객체
						timerTask = new TimerTask() { // 점프 애니메이션을 보여줌
							@Override
							public void run() {
								// 캐릭터의 위치가 최고점일 경우 y값을 증가시켜서 내려오도록 보이게 함
								if (400+y1 <=  50 && y2 != 400) { // y2가 400이면 최저점이므로 실행X
									y2 += 5; 
									jumping = false; // 점프 상태가 아님을 나타냄
									repaint();
									CrashCheck(50+y2); // 계속해서 바뀌는 캐릭터의 y 값을 전달함
								}
								
								else {
									y1-=5; 
									jumping = true; // 현재 점프가 진행되고 있음을 나타냄
									repaint();
									CrashCheck(400+y1);
								}
								
								// 캐릭터가 점프 후 최저점에 도달 했으므로 다음 점프를 위해 timerTask를 정지 시키고 
								// 다음 객체 생성을 위해 null을 대입함
								if(y2 == 400) { 		
									// 충돌 여부 파악 후 체력 감소
									if(crashing == true) {
										health -= 10;
										crashing = false;
									}
									// 체력이 0일때 게임오버 -> 결과 창 실행해야 함
									if(health == 0) {
										System.out.println("Game Over");
									}
									
									//확인용
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
						// 0.5초 동안 0.07초의 간격으로 timerTask를 실행시킴
						timer.schedule(timerTask, 500, 7); 
					}
				}
			});		
		}
		
		@Override
        protected void paintComponent(Graphics g) { // frame에 들어가는 이미지를 그려줌
        	super.paintComponent(g);
        	g.setColor(Color.WHITE);
            g.fillRect(0, 0, getWidth(), getHeight());

            g.drawImage(coin, 30, 30, 200, 200, this);
            g.drawImage(obs, 50, 50, obs.getWidth(), obs.getHeight(), this);
            
            if(jumping == true) // 점프할 시 y값이 계속 감소하는 것을 보여줌(올라감)
            	g.drawImage(ch, 30, 400+y1, 140, 128, this);
            
            else if(y1 == 0 && y2 == 0) // 처음 시작할 때 그려줌
            	g.drawImage(ch, 30, 400, 140, 128, this);
         
            else g.drawImage(ch, 30, 50+y2, 140, 128, this); // 최고점을 도달한 후 y값이 증가하는 것을 보여줌(떨어짐)
        }
	} // CoinImage
	
	public void CrashCheck(int y) { // 충돌 확인
		int coinxl = 30, coinxr = 30 + 200, coinyl = 30, coinyr = 30 + 200;
		int chxl = 30, chxr = 30 + 140, chyl = y, chyr = y + 128;
		int obsxl = 50, obsxr = 50 + obs.getWidth(), obsyl = 50, obsyr = 50 + obs.getHeight();
		
		// coin 획득 이벤트
		if((chxl < coinxr) && (coinxl < chxr) && (chyl < coinyr) && (coinyl < chyr)) {
			if(coin != null) property += 1;
			coin = null;
		}
		
		// 장애물 충돌 이벤트 -> 체력 감소
		if((chxl < obsxr) && (obsxl < chxr) && (chyl < obsyr) && (obsyl < chyr)) {
			crashing = true; // 충돌 여부를 체크함
		}
		
		// 확인 용
//		System.out.println("Your property: " + property);
		
//		System.out.println("chxl: " + chxl + "\nchxr: " + chxr + "\nchyl : " + chyl  + "\nchyr: " + chyr);
//		System.out.println("obsxl: " + obsxl + "\nobsxr: " + obsxr + "\nobsyl: " + obsyl + "\nobsyr: " + obsyr);
		
	} // CrashChck
	
	public static void main(String[] args) throws IOException {	
		new Coin();
	}
}
