package audios;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class BgmItem {
	Clip clip;
	
	public void music() {
		try {
			AudioInputStream stream = AudioSystem.getAudioInputStream(new File("audio/item.    ")); // ������ bgm �ֱ�!!!
			clip = AudioSystem.getClip();
			clip.open(stream);
			clip.start();
		}
		
		catch(Exception e) {
			e.printStackTrace();
		}
	} // music

}
