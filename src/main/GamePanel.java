package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;
import javax.swing.JTextField;

import entity.Entity;
import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{

	private static final long serialVersionUID = 1L;
	
	// CONFIGURAÇÕES DA TELA
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;
    
    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    
    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels
    
    //CONFIGURAÇÕES MUNDIAIS
    public final int maxWorldCol = 100;
    public final int maxWorldRow = 100;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;
    
    Thread gameThread;
    
    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    
    Sound music = new Sound();
    Sound se = new Sound();
    
    public CollisionChecker cChecker = new CollisionChecker(this);
    
    //CLASSE PARA INSTANCIAR OBJETOS E COLOCA-LOS NO MAPA
    public AssetSetter aSetter = new AssetSetter(this);
    
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    
    JTextField campoNome = new CampoDeTexto(this);
    
    //ENTIDADES E OBJETOS
    public Player player = new Player(this,keyH);
    public Entity[] npc = new Entity[10];
    public Entity[] monster = new Entity[20];    
    //10 SLOTS PARA OBJETOS, PODE EXIBIR ATÉ 10 OBJETOS
    //AO MESMO TEMPO, MUITOS OBJETOS PODEM DESACELERAR O JOGO
    public Entity obj[] = new Entity[10];
    ArrayList<Entity> entityList = new ArrayList<>();
    
    //ESTADO DO JOGO
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    
    //FPS	
    int FPS = 59;
    
    public GamePanel() {
    	this.setPreferredSize(new Dimension(screenWidth,screenHeight));
    	this.setBackground(Color.black);
    	this.setDoubleBuffered(true);
    	this.addKeyListener(keyH);
    	this.setFocusable(true);
    }
    
    public void setupGame() {
    	
    	aSetter.setObject();
    	aSetter.setNPC();
    	aSetter.setMonster();
    	playMusic(0);
    	gameState = titleState;
    	this.add(campoNome);
    	campoNome.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				campoNome.setFocusable(false);
				if(campoNome.getText().isEmpty()) {
					player.nome = "Viajante";
				} else {
					player.nome = campoNome.getText();
				}
				ui.titleScreenState = 3;
				
			}
		});
    	
    }
    
    public void startGameThread() {
    	gameThread = new Thread(this);
    	gameThread.start();
    }

	@Override
	public void run() {
		
		//Controle de FPS
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		while(gameThread != null) {
			
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if(delta >= 1) {
				update();
				repaint();
				delta --;
				drawCount ++;
			}
			
	//		if(timer >= 1000000000) {
	//			System.out.println("FPS "+drawCount);
	//			
	//			drawCount = 0;
	//			timer = 0;
	//      }
		}
		
		
		
	}
	
	public void update() {
		
		if(gameState == playState) {
			//PLAYER
			player.update();
			//NPC
			for(int i = 0; i < npc.length; i++) {
				if(npc[i] != null) {
					npc[i].update();
				}
			}
			for(int i = 0; i < monster.length; i++) {
				if(monster[i] != null) {
					if(monster[i].alive == true && monster[i].dying == false) {
						monster[i].update();
					}
					if(monster[i].alive == false) {
						monster[i] = null;
					}
					
				}
			}
			
		}
		if(gameState == pauseState) {
			
		}
		
	}
	
	public void paintComponent(Graphics g) {
		
		//Pintando elementos na tela
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		//DEBUG
		long drawStart = 0;
		if(keyH.checkDrawTime == true) {
			drawStart = System.nanoTime();
		}
		
		//TELA DE TÍTULO
		if(gameState == titleState) {
			ui.draw(g2);
			
		}
		else {
			
			//AQUI SE COLOCAR OS ELEMENTOS NA TELA, CERTIFIQUE-SE
			//DE QUE O PLAYER SEJA DESENHADO POR ULTIMO PARA QUE NADA
			//O CUBRA, TIPO UM SISTEMA DE CAMADAS
			
			//TILE
			tileM.draw(g2);
			
			//ADICIONAR ENTIDADES A LISTA
			entityList.add(player);
			
			for(int i = 0; i < npc.length; i++) {
				if(npc[i] != null) {
					entityList.add(npc[i]);
				}
			}
			
			for(int i = 0; i < obj.length; i++) {
				if(obj[i] != null) {
					entityList.add(obj[i]);
				}
			}
			for(int i = 0; i < monster.length; i++) {
				if(monster[i] != null) {
					entityList.add(monster[i]);
				}
			}
			
			//CLASSIFICAÇÃO
			Collections.sort(entityList, new Comparator<Entity>() {

				@Override
				public int compare(Entity e1, Entity e2) {
					
					int result = Integer.compare(e1.worldY, e2.worldY);
					return result;
				}
				
			});
			
			//UI
			ui.draw(g2);
			
		}
		
		//DESENHAR ENTIDADES
	    for(int i = 0; i < entityList.size(); i++) {
	    	entityList.get(i).draw(g2);
	    }
	    //ESVAZIAR LISTA DE ENTIDADES
	    entityList.clear();
	    
		//DEBUG
		if(keyH.checkDrawTime == true) {
			long drawEnd = System.nanoTime();
			long passed = drawEnd - drawStart;
			g2.setColor(Color.white);
			g2.drawString("Tempo de desenho: " + passed, 10, 400);
		}
		
		
		g2.dispose();
	}
	
	public void playMusic(int i) {
		music.setFile(i);
		music.play();
		music.loop();
	}
	
	public void stopMusic() {
		
		music.stop();
	}
	public void playSE(int i) {
		
		se.setFile(i);
		se.play();
	}
	
	
}