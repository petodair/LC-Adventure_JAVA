package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	
	//CLASSE PARA ABRIR E IMPORTAR ARQUIVOS DE ÁUDIO
	Clip clip;
	
	//USADO PARA ARMAZENAR O CAMINHO DOS ARQUIVOS DE SOM
	URL soundURL[] = new URL[30];
	
	public Sound() {
		
		soundURL[0] = getClass().getResource("/sound/overworld2.wav");
		soundURL[1] = getClass().getResource("/sound/coletar_item.wav");
		soundURL[2] = getClass().getResource("/sound/levelup.wav");
		soundURL[3] = getClass().getResource("/sound/useItem.wav");
		soundURL[4] = getClass().getResource("/sound/attack.wav");
		soundURL[5] = getClass().getResource("/sound/hit_monster.wav");
		soundURL[6] = getClass().getResource("/sound/hit_player.wav");
		soundURL[7] = getClass().getResource("/sound/select.wav");
		soundURL[8] = getClass().getResource("/sound/energy.wav");
		
		
	}
	
	//REPRODUZIR ARQUIVO DE AUDIO EM JAVA
	public void setFile(int i) {
		
		try {
			
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void play() {
		
		clip.start();
	}
	
	public void loop() {
		
		clip.loop(clip.LOOP_CONTINUOUSLY);
	}
	public void stop( ) {
		
		clip.stop();
	}

}
