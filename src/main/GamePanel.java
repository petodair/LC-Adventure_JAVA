package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;
import javax.swing.JTextField;

import entity.Entity;
import entity.Player;
import tile.TileManager;
import tile_interactive.InteractiveTile;

public class GamePanel extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L;

	// CONFIGURAÇÕES DA TELA
	final int originalTileSize = 16; // 16x16 tile
	final int scale = 3;

	public final int tileSize = originalTileSize * scale; // 48x48 tile
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;

	public final int screenWidth = tileSize * maxScreenCol; // 960 pixels
	public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

	// CONFIGURAÇÕES MUNDIAIS
	public final int maxWorldCol = 100;
	public final int maxWorldRow = 100;

	// PARA TELA CHEIA
	int screenWidth2 = screenWidth;
	int screenHeight2 = screenHeight;
	BufferedImage tempScreen;
	Graphics2D g2;

	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;

	Thread gameThread;

	TileManager tileM = new TileManager(this);
	public KeyHandler keyH = new KeyHandler(this);

	Sound music = new Sound();
	Sound se = new Sound();

	public CollisionChecker cChecker = new CollisionChecker(this);

	// CLASSE PARA INSTANCIAR OBJETOS E COLOCA-LOS NO MAPA
	public AssetSetter aSetter = new AssetSetter(this);

	public UI ui = new UI(this);
	public EventHandler eHandler = new EventHandler(this);

	JTextField campoNome = new CampoDeTexto(this);

	// ENTIDADES E OBJETOS
	public Player player = new Player(this, keyH);
	public Entity obj[] = new Entity[10];
	public Entity npc[] = new Entity[10];
	public Entity monster[] = new Entity[20];
	public InteractiveTile iTile[] = new InteractiveTile[50];
	public ArrayList<Entity> projectileList = new ArrayList<>();
	public ArrayList<Entity> particleList = new ArrayList<>();
	ArrayList<Entity> entityList = new ArrayList<>();

	// ESTADO DO JOGO
	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;
	public final int characterState = 4;

	// FPS
	int FPS = 59;

	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}

	public void setupGame() {

		aSetter.setObject();
		aSetter.setNPC();
		aSetter.setMonster();
		aSetter.setInteractiveTile();
		gameState = titleState;

		tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB_PRE);
		g2 = (Graphics2D) tempScreen.getGraphics();
		
		setFullScreen();

		this.add(campoNome);
		campoNome.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				campoNome.setFocusable(false);
				if (campoNome.getText().isEmpty()) {
					player.nome = "Viajante";
				} else {
					player.nome = campoNome.getText();
				}
				ui.titleScreenState = 3;

			}
		});

	}
	public void setFullScreen() {
		
		//PEGAR TAMANHO DA TELA DO DISPOSITIVO
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		gd.setFullScreenWindow(Main.window);
		
		//PEGAR LARGURA E ALTURA DA TELA CHEIA
		screenWidth2 = Main.window.getWidth();
		screenHeight2 = Main.window.getHeight();
	}
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {

		// Controle de FPS
		double drawInterval = 1000000000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;

		while (gameThread != null) {

			currentTime = System.nanoTime();

			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;

			if (delta >= 1) {
				update();
				drawToTempScreen();
				drawToScreen();
				delta--;
				drawCount++;
			}

			// if(timer >= 1000000000) {
			// System.out.println("FPS "+drawCount);
			//
			// drawCount = 0;
			// timer = 0;
			// }
		}

	}

	public void update() {

		if (gameState == playState) {
			// PLAYER
			player.update();
			// NPC
			for (int i = 0; i < npc.length; i++) {
				if (npc[i] != null) {
					npc[i].update();
				}
			}
			for (int i = 0; i < monster.length; i++) {
				if (monster[i] != null) {
					if (monster[i].alive == true && monster[i].dying == false) {
						monster[i].update();
					}
					if (monster[i].alive == false) {
						monster[i].checkDrop();
						monster[i] = null;
					}

				}
			}
			for (int i = 0; i < projectileList.size(); i++) {
				if (projectileList.get(i) != null) {
					if (projectileList.get(i).alive == true) {
						projectileList.get(i).update();
					}
					if (projectileList.get(i).alive == false) {
						projectileList.remove(i);
					}

				}
			}
			for (int i = 0; i < particleList.size(); i++) {
				if (particleList.get(i) != null) {
					if (particleList.get(i).alive == true) {
						particleList.get(i).update();
					}
					if (particleList.get(i).alive == false) {
						particleList.remove(i);
					}
				}
			}
			for (int i = 0; i < iTile.length; i++) {
				if (iTile[i] != null) {
					iTile[i].update();
				}
			}
		}
		if (gameState == pauseState) {

		}

	}

	public void drawToTempScreen() {

		// DEBUG
		long drawStart = 0;
		if (keyH.showDebugText == true) {
			drawStart = System.nanoTime();
		}

		// TELA DE TÍTULO
		if (gameState == titleState) {
			ui.draw(g2);

		} else {

			// AQUI SE COLOCAR OS ELEMENTOS NA TELA, CERTIFIQUE-SE
			// DE QUE O PLAYER SEJA DESENHADO POR ULTIMO PARA QUE NADA
			// O CUBRA, TIPO UM SISTEMA DE CAMADAS

			// TILE
			tileM.draw(g2);

			for (int i = 0; i < iTile.length; i++) {
				if (iTile[i] != null) {
					iTile[i].draw(g2);
				}
			}

			// ADICIONAR ENTIDADES A LISTA
			entityList.add(player);

			for (int i = 0; i < npc.length; i++) {
				if (npc[i] != null) {
					entityList.add(npc[i]);
				}
			}

			for (int i = 0; i < obj.length; i++) {
				if (obj[i] != null) {
					entityList.add(obj[i]);
				}
			}
			for (int i = 0; i < monster.length; i++) {
				if (monster[i] != null) {
					entityList.add(monster[i]);
				}
			}
			for (int i = 0; i < projectileList.size(); i++) {
				if (projectileList.get(i) != null) {
					entityList.add(projectileList.get(i));
				}
			}
			for (int i = 0; i < particleList.size(); i++) {
				if (particleList.get(i) != null) {
					entityList.add(particleList.get(i));
				}
			}

			// CLASSIFICAÇÃO
			Collections.sort(entityList, new Comparator<Entity>() {

				@Override
				public int compare(Entity e1, Entity e2) {

					int result = Integer.compare(e1.worldY, e2.worldY);
					return result;
				}

			});

		}

		// DESENHAR ENTIDADES
		for (int i = 0; i < entityList.size(); i++) {
			entityList.get(i).draw(g2);
		}
		// ESVAZIAR LISTA DE ENTIDADES
		entityList.clear();

		// UI
		ui.draw(g2);

		// DEBUG
		if (keyH.showDebugText == true) {
			long drawEnd = System.nanoTime();
			long passed = drawEnd - drawStart;

			g2.setFont(new Font("Arial", Font.PLAIN, 20));
			g2.setColor(Color.white);
			int x = 10;
			int y = 400;
			int lineHeight = 20;

			g2.drawString("WorldX " + player.worldX, x, y);
			y += lineHeight;
			g2.drawString("WorldY " + player.worldY, x, y);
			y += lineHeight;
			g2.drawString("Col " + (player.worldX + player.solidArea.x) / tileSize, x, y);
			y += lineHeight;
			g2.drawString("Row " + (player.worldY + player.solidArea.y) / tileSize, x, y);
			y += lineHeight;
			g2.drawString("Tempo de desenho: " + passed, x, y);
		}

	}
    public void drawToScreen() {
    	
    	Graphics g = getGraphics();
    	g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
    	g.dispose();
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
