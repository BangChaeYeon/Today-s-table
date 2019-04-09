package views;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import constant.TimerCopy;
import constant.TimerTaskCopy;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.TextField;
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

public class startView{
	long start, now; // 시간을 받는 변수
	long feverStart, fever;
	
	Boolean jumping = false;
	Boolean crashing = false;
	Boolean jumpInterval = false;
	Boolean barrier = false;
//	Boolean bomb = false;
	Boolean bomb = true;

	int savetime = 0; // 지나간 시간을 초 단위로 갖고 있는 변수
	int score = 0; // 점수
	int scoreNum = 50; // 시간 당 점수
	int health = 100; // 체력	
	int  y1 = 0, y2 = 0;
	int damage = 2; // 장애물 충돌 시 데미지
	int charNum = 1; // 캐릭터 번호 (1 ~ 6)
	int coinNum = 1; // 얻게되는 코인양
	int wealth = 0; // 총 얻은 코인
	int feverNum = 0;
	int map = 3;
	int y = 410;
	
	TimerCopy timer2;
	TimerTaskCopy timerTask2;
	
	Image image;
	private Image runnerImage;
	
	JLabel jl;
	JPanel jp = new JPanel();
	JFrame frame = new JFrame();
	
	String scoreStr= "";
	Label nameLabel = new Label();
	Label heartLabel = new Label();
	int scoreN; 

	public startView() {

		jp.add(new AnimatingPanel());
		
		frame.add(jp);
        
		frame.pack();
        
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 차후에 제거
        
        switch(charNum) {
		case 2: // 양념 치킨
		    damage = 1;
		    break;
		case 3: // 간장 치킨
			scoreNum = 75;
			break;
		case 4: // 황금 올리브 치킨
			coinNum = 2;
			break;
		case 5: // 간장 반 양념 반
			damage = 1;
			scoreNum = 75;
			break;
		case 6: // 생 닭
			health = 150;
			break;
	    }
        start = System.currentTimeMillis(); // 프로그램 시작 시간을 받음
    }
	
	private class AnimatingPanel extends JPanel {

		private int INCREMENTBG = 10; // background의 속도
        private int INCREMENTTILE = 20; // tile의 속도

        private BufferedImage tile, tile2;
        private BufferedImage backgroundImage, backgroundImage2, coin, coin2;
        private Image runnerImage;

        private BufferedImage obstacle, obstacle1, obstacle2;
        
        int fs = 1000; //frameSize (화면이 크기) 처음 장애물들은 화면 밖에서 시작
        
        int distance = 300; // 장애묻들 간의 간격
        int coinDis = 10;
        
        private int bg1xl, bg2xl, bg1xr, bg2xr ;// initImagePoints() 참조
        private int tile1xl, tile2xl, tile1xr, tile2rx; // initImagePoints() 참조
        private int IMAGE_WIDTH; 

        private BufferedImage heart, heart2, item, item2;
        private BufferedImage obs1;

        int coiny;
        int coin1xl, coin1xr, coin1yl, coin1yr;
        int coin2xl, coin2xr, coin2yl, coin2yr;
        
        int itemy;
        int item1xl, item1xr, item1yl, item1yr;
        int item2xl, item2xr, item2yl, item2yr;
        
        int hearty;
        int heart1xl, heart1xr, heart1yl, heart1yr;
        int heart2xl, heart2xr, heart2yl, heart2yr;
        
        int obsy, obsy2;
        int obs1xl, obs1xr, obs1yl, obs1yr;
        int obs2xl, obs2xr, obs2yl, obs2yr;
        int obs3xl, obs3xr, obs3yl, obs3yr;
        
        
        Image coinSi; // 코인 점수 옆에 띄어두는 이미지 
        Image heartSi; // 체력 점수 옆에 띄어주는 이미지
        Image itemSi;
        
        int time = 40; // 40이 이상적인 속도 //반복횟수?
        
        public AnimatingPanel() {
        	
            initImages();
            
            initObstacleImgs();
            initCoinImgs();
            initHeartImgs();
            initItemImgs();
            
            initImagePoints();
            
            Timer timer = new Timer(time, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                	moveTile();
                    moveBackground();
                    
                    initObstaclePoints();                       
                    moveCoin();
                    moveHeart();
                    moveItem();
                    
                    //every
                    CrashCheck(y);
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
						if(jumpInterval == false) {
							jumpInterval = true;
							timer2 = new TimerCopy(true); // util 타이머 객체
							timerTask2 = new TimerTaskCopy() { // 점프 애니메이션을 보여줌
								@Override
								public void run() {
									if (410+y1 <= 80 && y2 != 410) { 
										y2 += 7; 
										jumping = false; // 점프 상태가 아님을 나타냄
										y = 80 + y2;
										repaint();
									}
									
									else {
										y1-=5; 
										jumping = true; 
										y = 410 + y1;
										repaint();
									}
									
									if(y2+80 >= 410) {
										y = 410;
										jumpInterval = false;
										timer2.cancel();
										timer2.purge();
										y1 = 0;
										y2 = 0;
										timer2 = null;
										if(feverNum == 10) {
											if(feverStart == 0)
												feverStart = System.currentTimeMillis();
											initImages();
										}
									}
								}
							};
							timer2.schedule(timerTask2, 200, 6); 
						} // if
						else {
							System.out.println("더블 점프는 실행되지 않습니다.");
						}
					} // if - space
					if(e.getKeyCode() == KeyEvent.VK_B) {
						if(bomb) {
							if(obs1xl >= 0 && obs1xr <= 1000) {
								obs1xl = 1000 + obs1xl;
							}
							
							if(obs2xl >= 0 && obs2xr <= 1000) {
								obs2xl = 1000 + obs2xl;
							}
							
							if(obs3xl >= 0 && obs3xr <= 1000) {
								obs3xl = 1000 + obs3xl;
							}
							bomb = false;
						}
					}
				}
			});

	    } // AnimatingPanel
        

        @Override
        protected void paintComponent(Graphics g) {
        	super.paintComponent(g);

            g.fillRect(0, 0, getWidth(), getHeight());
    		
            g.drawImage(backgroundImage, bg1xl, 0, backgroundImage.getWidth(), backgroundImage.getHeight(), this);
            g.drawImage(backgroundImage2, bg2xl, 0, backgroundImage2.getWidth(), backgroundImage2.getHeight(), this);
            g.drawImage(tile, tile1xl, 30, tile.getWidth(), tile.getHeight(), this);
            g.drawImage(tile2, tile2xl, 30, tile.getWidth(), tile.getHeight(), this);
            
            if(feverNum != 10) {
	            g.drawImage(obstacle, obs1xl, obsy, obstacle.getWidth(), obstacle.getHeight(), this);
	            g.drawImage(obstacle1, obs2xl, obsy, obstacle1.getWidth(), obstacle1.getHeight(), this);
	            g.drawImage(obstacle2, obs3xl, -195, 60, 280, this);
	            g.drawImage(item, item1xl, itemy, 75, 75, this);
	            g.drawImage(item2, item2xl, itemy, 75, 75, this);
            }
            
            g.drawImage(coin, coin1xl, coiny, 50, 50, this);
            g.drawImage(coin2, coin2xl, coiny, 50, 50, this);
            
            g.drawImage(heart, heart1xl, hearty, 50, 50, this);
            g.drawImage(heart2, heart2xl, hearty, 50, 50, this);

            g.drawImage(coinSi, 810, 50, 50, 50, this);
            g.drawImage(heartSi, 50, 50, 50, 50, this);
            g.drawImage(itemSi, 810, 120, 50, 50, this);
            
            g.drawImage(runnerImage, 80, y, runnerImage.getWidth(this), runnerImage.getHeight(this), this);
            
            int fontSize = 80;
            
			// 체력이 0일때 게임오버 -> 결과 창 실행해야 함

			if(health == 0) {
				System.out.println("Game Over");
				scoreView sV = new scoreView();
			}
		
            //score Text
            g.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
            g.setColor(Color.WHITE);
            g.drawString(score + "", 420, 100);
            
            // heart(체력) Text
            g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
            g.setColor(Color.WHITE);
            g.drawString(health + " %", 110, 90);
            
            // coin(체력) Text
            g.setFont(new Font("", Font.PLAIN, 40));
            g.setColor(Color.WHITE);
            g.drawString(wealth + "", 880, 90);
            
            // 피버 Text
            g.setFont(new Font("", Font.PLAIN, 40));
            g.setColor(Color.WHITE);
            g.drawString(feverNum + "", 880, 160);
            
         // 확인용
//			System.out.println("your health: " + health); // 체력 출력
//			System.out.println("score: " + score); // 점수 출력
//			System.out.println("savetime: " + savetime); // 지나간 시간 출력
//			System.out.println("wealth: " + wealth); // 현재 보유 자산 출력
            
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(1000, 600);
        }
        
        private void initImagePoints() {
        	bg1xl = 0; // 첫번재 background의 x좌표
        	bg2xl = 2000; // 두 번재 background의 x좌표
        	bg1xr = 2000; // 첫번재 background의 x좌표의 끝 (이미지 오른쪽 좌표?)
        	bg2xr = 4000; // 첫번재 background의 x좌표의 끝 (이미지 오른쪽 좌표?)
        	
        	tile1xl = 0;
        	tile2xl = 2000;
        	tile1xr = 2000; 
        	tile2rx = 4000;
        	

        	//장애물(칼)
        	obsy = 350;
        	obs1xl = 1000;
        	obs1xr =  obs1xl + obstacle.getWidth();
        	obs1yl = 350;
        	obs1yr = obs1yl + obstacle.getWidth();
        	
        	obs2xl = 1500;
        	obs2xr =  obs2xl + obstacle1.getWidth();
        	obs2yl = 350;
        	obs2yr = obs2yl + obstacle1.getWidth();
        	
        	obsy2 = -195;
        	obs3xl = 1500;
        	obs3xr = obs3xl + 60;
        	obs3yl = 300;
        	obs3yr = obs3yl + 60;

        	
        	//코인
        	coiny = 200;
        	coin1xl = 1000; // coin의 x좌표
        	coin1xr = coin1xl + 50; 
        	coin1yl = 200;
        	coin1yr = coin1yl + 50;
        	
        	coin2xl = 1500; // coin2의 x좌표  (뒤에 +200으로 사이 간격 조작)
        	coin2xr = coin2xl + 50;
        	coin2yl = 200;
        	coin2yr = coin2yl + 50;
        	
        	// heart
        	hearty = 300;
        	heart1xl = 10000;
        	heart1xr = heart1xl + 50;
        	heart1yl = 300;
        	heart1yr = heart1yl + 50;
        	
        	heart2xl = 20000;
        	heart2xr = heart2xl + 50;
        	heart2yl = 300;
        	heart2yr = heart2yl + 50;
        	
        	//item
        	itemy = 270;
        	item1xl = 1500;
        	item1xr = item1xl + 75;
        	item1yl = 270;
        	item1yr = item1yl + 75;
        	
        	item2xl = 2500;
        	item2xr = item2xl + 75;
        	item2yl = 270;
        	item2yr = item2yl + 75;

        	// tile이랑 bg이랑 속도 다르게 계산
        }
        
        private void initImages() {
            try {

            	if(feverNum == 10) {
               		backgroundImage = ImageIO.read(new File("imgs/feverBg.png"));
	                backgroundImage2 = ImageIO.read(new File("imgs/feverBg.png")); 
	                tile = ImageIO.read(new File("imgs/tile1.png"));
                    tile2 = ImageIO.read(new File("imgs/tile1.png"));
               }
               else {
            	   switch(map) {
	            	   case 1:
	            		   tile = ImageIO.read(new File("imgs/tile2.png"));
	                       tile2 = ImageIO.read(new File("imgs/tile2.png"));
	    	                backgroundImage = ImageIO.read(new File("imgs/background.png"));
	    	                backgroundImage2 = ImageIO.read(new File("imgs/background.png")); 
	            		   break;
	            	   case 2:
	            		   tile = ImageIO.read(new File("imgs/tile3.png"));
	                       tile2 = ImageIO.read(new File("imgs/tile3.png"));
	    	                backgroundImage = ImageIO.read(new File("imgs/map2.jpg"));
	    	                backgroundImage2 = ImageIO.read(new File("imgs/map2.jpg")); 
	            		   break;
	            	   case 3:
	            		   tile = ImageIO.read(new File("imgs/tile4.png"));
	                       tile2 = ImageIO.read(new File("imgs/tile4.png"));
	    	                backgroundImage = ImageIO.read(new File("imgs/map4.jpg"));
	    	                backgroundImage2 = ImageIO.read(new File("imgs/map4.jpg")); 
	            		   break;
            	   }		
	                coinSi = ImageIO.read(new File("imgs/coin.png"));
	                heartSi = ImageIO.read(new File("imgs/heart.png"));
	                item = ImageIO.read(new File("imgs/item.png"));
               }
            	
            	if(barrier) runnerImage = new ImageIcon("imgs/chickBarrier" + charNum + ".gif").getImage();
            	else runnerImage = new ImageIcon("imgs/chick" + charNum + ".gif").getImage();
                
                coinSi = ImageIO.read(new File("imgs/coin.png"));
                heartSi = ImageIO.read(new File("imgs/heart.png"));
                itemSi = ImageIO.read(new File("imgs/item.png"));
                
            } catch (Exception ex) {
            	System.out.println("img_error");
                ex.printStackTrace();
            }
            
       } // initImages
    
	    private void moveBackground() {
	    	if(bg1xr < 0) { 
	    		System.out.println("@@");
	    		bg1xl = 2000 - INCREMENTBG; 
	    		bg1xr = 4000 - INCREMENTBG; 
	    	}

	    	if(bg2xr < 0) { 
	    		System.out.println("!!");
	    		bg2xl = 2000 - INCREMENTBG; 
	    		bg2xr = 4000 - INCREMENTBG;
	    	}
	    	
//	    	System.out.println("bg1xl : " + bg1xl + ", bg2xl : " + bg2xl);
	    	bg1xl -= INCREMENTBG; // bg1의 왼쪽 x좌표 감소
	    	bg2xl -= INCREMENTBG; // bg2의 왼쪽 y좌표 감소
	    	bg1xr -= INCREMENTBG; // bg1의 오른쪽 x좌표 감소
	    	bg2xr -= INCREMENTBG; // bg2의 오른쪽 y좌표 감소


	    	if(obs1xr == 0) { 
	    		 obs1 = null;
	    	}
	    	
			now = System.currentTimeMillis(); // 현재 시간을 입력 받음
			fever = System.currentTimeMillis(); // 나중에 if문으로 조절
			
	    	if((fever - feverStart)/1000 == 7) {
            	feverNum = 0;
            	initImages();
            }

			// 5초가 지날 때 마다 score가 50씩 증가함
			savetime = (int) ((now-start) / 1000); // 프로그램 시작 후 지난 시간을 초 단위로 받음
			score = scoreNum * (savetime/5); // 지난 시간을 time으로 받고 5초마다 50씩 오르게 함

	    } //moveBackground
	    
	    private void moveTile() {

	    	if(tile2rx < 0) {
	    		tile2xl = 2000- INCREMENTTILE;
	    		tile2rx = 4000- INCREMENTTILE;
	    	}
	    	if(tile1xr < 0) {
	    		tile1xl = 2000- INCREMENTTILE;
	    		tile1xr = 4000- INCREMENTTILE;
	    	}
	    	tile1xl -= INCREMENTTILE; // tile1 왼쪽 x좌표 감소
	    	tile2xl -= INCREMENTTILE; // tile2 왼쪽 x좌표 감소
	    	tile1xr -= INCREMENTTILE; // tile1 오른쪽x좌표 감소
	    	tile2rx -= INCREMENTTILE; // tile1 오른쪽x좌표 감소
	    
	    } // moveTile

	    public void initCoinImgs() {
	    	try {
	    		coin = ImageIO.read(new File("imgs/coin.png"));
	    		coin2 = ImageIO.read(new File("imgs/coin.png"));
	    	} catch (IOException e) {
	    		System.out.println("coin 이미지 에러");
	    		e.printStackTrace();
	    	}
	    	
	    }

	    public void moveCoin() {
//	    	System.out.println("coin2xl : " + coin2xl + "/ coin2xr : " + coin2xr + "/ coin2yl : " + coin2yl + " / coin2yr : " + coin2yr);
//	    	System.out.println("coin1xl : " + coin1xl + "/ coin1xr : " + coin1xr + "/ coin1yl : " + coin1yl + " / coin1yr : " + coin1yr);
	    	
	    	coin1xl -= INCREMENTTILE;
	    	coin2xl -= INCREMENTTILE;
	    	
	    	coin1xr = coin1xl + 50; 
        	coin2xr = coin2xl + 50; 
	    	
	    	if(coin1xl < -50) { 
	    		coin1xl = 1000;
	    		coin1xr = coin1xl + 50;
	    		initCoinImgs();
				repaint();
	    	}
	    	if(coin2xl < -50) { 
	    		coin2xl = 1000;
	    		coin2xr = coin2xl + 50;
	    		initCoinImgs();
				repaint();
	    	}

	    }
	    
	    public void initHeartImgs() {
	    	try {
	    		heart = ImageIO.read(new File("imgs/heart.png"));
	    		heart2 = ImageIO.read(new File("imgs/heart.png"));
			} catch (IOException e) {
				System.out.println("coin 이미지 에러");
				e.printStackTrace();
			}
	    	
	    }
	    public void moveHeart() {
//	    	System.out.println("heart2xl : " + heart2xl + " / heart2xr : " + heart2xr + " / heart2yl : " + heart2yl + " / heart2yr : " + heart2yr  );
	    	
	    	heart1xl -= INCREMENTTILE;
	    	heart2xl -= INCREMENTTILE;
	       	heart1xr = heart1xl + 50;
	       	heart2xr = heart2xl + 50;
	    	
	    	if(heart1xl < -50) { 
	    		heart1xl = 20000;
	    		heart1xr = heart1xl + 50;
	    		initHeartImgs();
	    		repaint();
	    	}
	    	if(heart2xl < -50) { 
	    		heart2xl = 20000;
	    		heart2xr = heart2xl + 50;
	    		initHeartImgs();
	    		repaint();
	    	}
	    	
	    }
	    
	    public void initObstacleImgs() { // 장애물 이미지 생성
	    	try {
	    		int rand1 = (int)((Math.random()*3) +1);
	    		int rand2 = (int)((Math.random()*3) + 4);
				obstacle = ImageIO.read(new File("imgs/obstacle0"+ rand1 +".png"));
				obstacle1 = ImageIO.read(new File("imgs/obstacle0"+ rand1 +".png"));
				obstacle2 = ImageIO.read(new File("imgs/obstacle0"+ rand2 +".png"));
			} catch (IOException e) {
				System.out.println("장애물 이미지 에러");
				e.printStackTrace();
			}
	    }
	    

	    public void initObstaclePoints() { //위치값 (장애물 왼쪽 이동 좌표)
//	    	System.out.println( "obs1xl : " +  obs1xl + "/ obs1xr : " +  obs1xr+ "/ obs1yl : " +  obs1yl+ "/ obs1yr : " +  obs1yr);
//	    	System.out.println( "obs2xl : " +  obs2xl + "/ obs2xr : " +  obs2xr+ "/ obs2yl : " +  obs2yl+ "/ obs2yr : " +  obs1yr);
//	    	System.out.println( "obs3xl : " +  obs3xl + "/ obs3xr : " +  obs3xr+ "/ obs3yl : " +  obs3yl+ "/ obs3yr : " +  obs1yr);
        	
	    	if(feverNum != 10) {
	    	obs1xl -= INCREMENTTILE;
	    	obs2xl -= INCREMENTTILE;
	    	obs3xl -= INCREMENTTILE;

	    	obs1xr =  obs1xl + obstacle.getWidth();
	    	obs2xr =  obs2xl + obstacle1.getWidth();
	    	obs3xr =  obs3xl + 60;
	    	
//	    	if(obs1xl+obstacle.getWidth() < 0) {	
	    	if(obs1xr < 0) {
				obs1xl = 1000;
				obs1xr = obs1xl + obstacle.getWidth();
				initObstacleImgs();
				repaint();
	    	} //obs1
	    	
	    	if(obs2xr < 0) {
				obs2xl = 1000;
				obs2xr = obs2xl + obstacle1.getWidth();
				initObstacleImgs();
				repaint();
	    	} //obs2
	    	
	    	if(obs3xr < 0) {
				obs3xl = 1000;
				obs3xr = obs3xl +  60;
				initObstacleImgs();
				repaint();
	    	} //obs3
	    }
  } //initObstaclePoints
	    
	    public void initItemImgs() {
	    	try {
				item = ImageIO.read(new File("imgs/item.png"));
				item2 = ImageIO.read(new File("imgs/item.png"));
			} catch (IOException e) {
				System.out.println("아이템 이미지 에러");
				e.printStackTrace();
			}
	    }
	    
	    public void moveItem() {
//	    	System.out.println("item1xl : " + item1xl + " / item1xr : " + item1xr + " / item1yl : " + item1yl + " / item1yr :" + item1yr);
//	    	System.out.println("item2xl : " + item2xl + " / item2xr : " + item2xr + " / item2yl : " + item2yl + " / item2yr :" + item2yr);
	    	
	    	if(feverNum != 10) {
		    	if(item == null) {
		    		item1xl = item1xl + 2500;
		    		initItemImgs();
		    	}
		    	if(item2 == null) {
		    		item2xl = item2xl + 2500;
		    		initItemImgs();
		    	}
			    else {
			    	item1xl -= INCREMENTTILE;
			    	item2xl -= INCREMENTTILE;
			    	item1xr = item1xl + item.getWidth();
			    	item2xr = item2xl + item2.getWidth();
			    	
			    	if(item1xl < -50) { 
			    		item1xl = 10000;
			    		item1xr = item1xl + 75;
			    		initItemImgs();
			    		repaint();
			    	}
			    	if(item2xl < -50) { 
			    		item2xl = 10000;
			    		item2xr = item2xl + 75;
			    		initItemImgs();
			    		repaint();
			    	}
		    	}
	    	}
	    }
	    
	   	public void CrashCheck(int y) { // 충돌 확인
	   		int chxl = 80, chxr = 80 + runnerImage.getWidth(this), chyl = y, chyr = y + runnerImage.getHeight(this);
//	   		System.out.println("chxl : " + chxl + "/ chxr : " + chxr + "/ chyl : " + chyl + " / chyr : " + chyr);

	   		// 체력회복 아이템 획득 이벤트
    		if((chxl < heart1xr) && (heart1xl < chxr) && (chyl < heart1yr) && (heart1yl < chyr)) {
    			if(heart != null) health += 10; // 체력을 증가 시켜줌
    			heart = null;
    		}
    		
    		if((chxl < heart2xr) && (heart2xl < chxr) && (chyl < heart2yr) && (heart2yl < chyr)) {
    			if(heart != null) health += 10; // 체력을 증가 시켜줌
    			heart = null;
    		}

    		if((chxl < obs1xr) && (obs1xl < chxr) && (chyl < obs1yr) && (obs1yl < chyr)) {
    			if(barrier == false)
    				health -= damage;
    			else {
        			System.out.println("barrier: " + barrier);
        			barrier = false;
    	    		initImages();
        		}
    		}
    		
    		if((chxl < obs2xr) && (obs2xl < chxr) && (chyl < obs2yr) && (obs2yl < chyr)) {
    			if(barrier == false)
    				health -= damage;
    			else {
        			System.out.println("barrier: " + barrier);
        			barrier = false;
    	    		initImages();
        		}
    		}
    		
    		if((chxl < obs3xr) && (obs3xl < chxr) && (chyl < obs3yr) && (obs3yl < chyr)) {
    			if(barrier == false)
    				health -= damage;
    			else {
        			barrier = false;
    	    		initImages();
        		}
    		}
    		
    		// coin 획득 이벤트
    		if((chxl < coin1xr) && (coin1xl < chxr) && (chyl < coin1yr) && (coin1yl < chyr)) {
    			if(coin != null) wealth += coinNum;
    			coin = null;
    		}
    		
    		if((chxl < coin2xr) && (coin2xl < chxr) && (chyl < coin2yr) && (coin2yl < chyr)) {
    			if(coin2 != null) wealth += coinNum;
    			coin2 = null;
    		}
    		
    		// fever item 이벤트
    		if((chxl < item1xr) && (item1xl < chxr) && (chyl < item1yr) && (item1yl < chyr)) {
    			if(item != null) feverNum += 1;
    			item = null;
    		}
    		
    		if((chxl < item2xr) && (item2xl < chxr) && (chyl < item2yr) && (item2yl < chyr)) {
    			if(item2 != null) feverNum += 1;
    			item2 = null;
    		}
    		
    	} // CrashChck
    	
	
	}// AnimatingPanel
	
	public static void main(String[] args) {

		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new startView();
            }
        });
	}
}