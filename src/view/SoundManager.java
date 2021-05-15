package view;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import javafx.scene.media.AudioClip;

/**
 * handles all the game sounds
 * @author Sungin Hwang
 *
 */
public class SoundManager {
	
	public static Clip clip;
	
	//sound effect volume
	public static double vol = 0.5;
	//backgroundmusic volume
	public static double vo = 0.8;

	//sound effect mute
	public static boolean efmu = true;
	//backgroundmusic mute
	public static boolean bamu = true;

	/**
	 * handles sound effect
	 * @param sound Sound file path to load
	 */
	public static void playSound(String sound) {	
		
		AudioClip clip = new AudioClip(sound);	
		clip.setVolume(vol);
		
		if(efmu) {
			clip.play();
		}else {
			clip.stop();
		}
	}
	/**
	 * handles backgroundmusic
	 * @param music Sound file path to load
	 */
	public static void music(String music) {
		
		if(clip != null  && clip.isActive()) {
			clip.stop();
		}
		
		if(clip != null  && music == "") {
			clip.stop();
		}
		
		try {
			
			File file = new File(music);
			
			AudioInputStream stream = AudioSystem.getAudioInputStream(file);
			clip = AudioSystem.getClip();
			clip.open(stream);
			FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

			float range = volume.getMaximum() - volume.getMinimum();
			float gain = (range * (float) vo) + volume.getMinimum();
			
			volume.setValue(gain);
			if(bamu == true) {
				
				clip.loop(Clip.LOOP_CONTINUOUSLY);				
				clip.start();
					
			}else {
				
				clip.stop();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
