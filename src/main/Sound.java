package main;

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
	
	//CLASSE PARA ABRIR E IMPORTAR ARQUIVOS DE ÁUDIO
	Clip clip;
	
	//USADO PARA ARMAZENAR O CAMINHO DOS ARQUIVOS DE SOM
	URL soundURL[] = new URL[30];
	
	FloatControl fc;
	int volumeScale = 3;
	float volume;
	
	public Sound() {
		
		soundURL[0] = getClass().getResource("/sound/ruinsV2.wav");
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
			//ALTERAR VOLUME
			fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
			checkVolume();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void play() {
		
		clip.start();
	}
	
	public void loop() {
		
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	public void stop( ) {
		
		clip.stop();
	}
	public void checkVolume() {
		
		//O VOLUME VAI DE -80F ATÉ 6F, PORÉM ABAIXO DE -30 JÁ NÃO SE PODE OUVIR PRATICAMENTE NADA
		
		switch(volumeScale) {
		case 0: volume = -80f; break;
		case 1: volume = -20f; break;
		case 2: volume = -12f; break;
		case 3: volume = -5f; break;
		case 4: volume = 1f; break;
		case 5: volume = 6f; break;
		}
		fc.setValue(volume);
	}

}
