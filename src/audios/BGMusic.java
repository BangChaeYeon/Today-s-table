package audios;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class BGMusic {
	Clip clip;
	int creatBgm = 0; // bgm은 한 번만 생성하여 실행할 수 있게
	
	public void music() {
		if(creatBgm == 0) {
			try {
				AudioInputStream stream = AudioSystem.getAudioInputStream(new File("audio/bg_jackpot.wav"));
				clip = AudioSystem.getClip();
				clip.open(stream);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			creatBgm = 1;
		}
		
		if(clip.isRunning()) {
			clip.stop();
		}else {
//			clip.loop(Clip.LOOP_CONTINUOUSLY);
			clip.start();
		}

		
	} // music

}
