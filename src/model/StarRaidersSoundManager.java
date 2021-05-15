package model;

import java.net.URI;

import javafx.scene.media.AudioClip;

public class StarRaidersSoundManager {
	
	public static void playSound(URI sound, double vol,boolean ef) {	
		
		AudioClip clip = new AudioClip(sound.toString());	
		clip.setVolume(vol);
		
		if(ef) {
			clip.play();
		}else {
			clip.stop();
		}
	}
}