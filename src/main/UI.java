package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entity.Entity;
import object.OBJ_Heart;
import object.OBJ_PositiveEnergy;

public class UI {

	GamePanel gp;
	Graphics2D g2;
	Font maruMonica, superNormal, superScribble, dinofiles, solidLinker;
	BufferedImage heart_full, heart_half, heart_blank, positiveEnergy_full, positiveEnergy_half, positiveEnergy_blank;
//	BufferedImage imagemChave;
	public boolean messageOn = false;
//	public String message = "";
//	int messageCounter = 0;
	ArrayList<String> message = new ArrayList<>();
	ArrayList<Integer> messageCounter = new ArrayList<>();
	public boolean gameFinished = false;
	public String currentDialogue = "";
	public int commandNum = 0;
	BufferedImage bg;
	public int titleScreenState = 0; // 0:primeira tela; 1:segunda tela
	public int slotCol = 0;
	public int slotRow = 0;

	double playTime;
	DecimalFormat dFormat = new DecimalFormat("#0.00");

	public UI(GamePanel gp) {

		this.gp = gp;

		// ESCOLHER A FONTA O ESTILO E O TAMANHO DA FONTE
		// QUE SERÁ MOSTRADA NA TELA

		try {
			InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
			maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
			is = getClass().getResourceAsStream("/font/Super Normal.ttf");
			superNormal = Font.createFont(Font.TRUETYPE_FONT, is);
			is = getClass().getResourceAsStream("/font/Super Scribble.ttf");
			superScribble = Font.createFont(Font.TRUETYPE_FONT, is);
			is = getClass().getResourceAsStream("/font/Dinofiles.ttf");
			dinofiles = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

//		OBJ_Chave chave = new OBJ_Chave(gp);
//		imagemChave = chave.image;
		
		// CRIAR HUD DE OBJETO
		
		Entity heart = new OBJ_Heart(gp);
		heart_full = heart.image;
		heart_half = heart.image2;
		heart_blank = heart.image3;
		Entity positiveEnergy = new OBJ_PositiveEnergy(gp);
		positiveEnergy_full = positiveEnergy.image;
		positiveEnergy_half = positiveEnergy.image2;
		positiveEnergy_blank = positiveEnergy.image3;
		

	}

	public void addMessage(String text) {

		message.add(text);
		messageCounter.add(0);
	}
	
	public void dialogoInicial() {
			gp.ui.currentDialogue = "Sua planatação de batatas do norte,\n"
					+ "sua principal fonte de alimentação, está\n"
					+ "morrendo, vai mesmo esperar isso\n"
					
					+ "acontecer, " + gp.player.nome + "?";
			gp.gameState = gp.dialogueState;
		
	}

	public void draw(Graphics2D g2) {

		this.g2 = g2;

		g2.setFont(maruMonica);
		g2.setColor(Color.white);

		// TITLE STATE
		if(gp.gameState == gp.titleState) {
			drawTitleScreen();
		}
		// PLAY STATE
		if(gp.gameState == gp.playState) {
            drawPlayerLife();
            drawMessage();
		}
		// PAUSE STATE
		if(gp.gameState == gp.pauseState) {
			drawPauseScreen();
		}
		// DIALOGUE STATE
		if(gp.gameState == gp.dialogueState) {
			drawPlayerLife();
			drawDialogueScreen();
		}
		// CHARACTER STATE
		if(gp.gameState == gp.characterState) {
			drawCharacterScreen();
			drawInventory();
		}
	}
	public void drawPlayerLife() {
		
		int x = gp.tileSize/2;
		int y = gp.tileSize/2;
		int i = 0;
		
		//DESENHAR SAÚDE MAXIMA
		while(i < gp.player.maxLife/2) {
			g2.drawImage(heart_blank, x, y, null);
			i++;
			x += gp.tileSize;
		}
		
		//RESET
	    x = gp.tileSize/2;
		y = gp.tileSize/2;
		i = 0;
		
		//DESENHAR SAÚDE ATUAL
		while(i < gp.player.life) {
			g2.drawImage(heart_half, x, y, null);
			i++;
			if(i < gp.player.life) {
				g2.drawImage(heart_full, x, y, null);
			}
			i++;
			x += gp.tileSize;
		}
		
		//DESENHA ENERGIA MAXIMA
		x = (gp.tileSize/2)-2;
		y = (int)(gp.tileSize*1.5);
		i = 0;
		
		while(i < gp.player.maxPositiveEnergy/2) {
			g2.drawImage(positiveEnergy_blank, x, y, null);
			i++;
			x += gp.tileSize;
		}
		
		x = (gp.tileSize/2)-2;
		y = (int)(gp.tileSize*1.5);
		i = 0;
		
		while(i < gp.player.positiveEnergy) {
			g2.drawImage(positiveEnergy_half, x, y, null);
			i++;
			if(i < gp.player.positiveEnergy) {
				g2.drawImage(positiveEnergy_full, x, y, null);
			}
			i++;
			x += gp.tileSize;
		}
	}
	public void drawMessage() {
		
		int messageX = gp.tileSize;
		int messageY = gp.tileSize*4;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));
		
		for(int i = 0; i < message.size(); i++) {
			
			if(message.get(i) != null) {
				
				g2.setColor(Color.black);
				g2.drawString(message.get(i), messageX+2, messageY+2);
				g2.setColor(Color.white);
				g2.drawString(message.get(i), messageX, messageY);
				
				int counter = messageCounter.get(i) + 1; //MESSAGE COUNTER++
				messageCounter.set(i, counter);
				messageY += 50;
				
				if(messageCounter.get(i) > 180) {
					message.remove(i);
					messageCounter.remove(i);
				}
			}
		}
	}
	public void drawTitleScreen() {

		if (titleScreenState == 0) {
			try {
				bg = ImageIO.read(getClass().getResourceAsStream("/background/title.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			g2.drawImage(bg, 0, 0, null);

			// TITLE NAME
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
			String text = "Título Genérico";
			int x = getXForCenteredText(text);
			int y = gp.tileSize * 3;

			// SHADOW
			Color c = new Color(159, 115, 22);
			g2.setColor(c);
			g2.drawString(text, x + 5, y + 5);
			// MAIN COLOR
			g2.setColor(Color.darkGray);
			g2.drawString(text, x, y);

			// IMAGEM DO PERSONAGEM
			// x = gp.screenWidth/2 - (gp.tileSize*2)/2;
			// y += gp.tileSize*2;
			// g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);

			// MENU
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

			text = "NOVO JOGO";
			x = getXForCenteredText(text);
			y += gp.tileSize * 4.5;
			g2.drawString(text, x, y);
			if (commandNum == 0) {
				g2.drawString(">", x - gp.tileSize, y);
			}

			text = "CARREGAR JOGO";
			x = getXForCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if (commandNum == 1) {
				g2.drawString(">", x - gp.tileSize, y);
			}

			text = "SAIR";
			x = getXForCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if (commandNum == 2) {
				g2.drawString(">", x - gp.tileSize, y);
			}
		} else if (titleScreenState == 1) {

			try {
				if (commandNum == 0) {
					bg = ImageIO.read(getClass().getResourceAsStream("/background/men_char.png"));
				} else if (commandNum == 1) {
					bg = ImageIO.read(getClass().getResourceAsStream("/background/woman_char.png"));
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			g2.drawImage(bg, 0, 0, null);

			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(42F));

			String text = "Selecione seu personagem";
			int x = getXForCenteredText(text);
			int y = gp.tileSize * 3;
			g2.drawString(text, x, y);

			g2.setColor(Color.black);

			text = "Homem";
			x = getXForCenteredText(text);
			y += gp.tileSize * 3;
			g2.drawString(text, x, y);
			if (commandNum == 0) {
				g2.drawString(">", x - gp.tileSize, y);
			}

			text = "Mulher";
			x = getXForCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if (commandNum == 1) {
				g2.drawString(">", x - gp.tileSize, y);
			}

		} else if (titleScreenState == 2) {

			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(42F));

			String text = "Insira seu nome";
			int x = getXForCenteredText(text);
			int y = gp.tileSize * 3;
			g2.drawString(text, x, y);

			gp.campoNome.grabFocus();
			gp.campoNome.repaint();

		}

		else if (titleScreenState == 3) {

			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(42F));

			String text = "Tem certeza que irá se chamar "+ gp.player.nome+"?";
			int x = getXForCenteredText(text);
			int y = gp.tileSize * 3;
			g2.drawString(text, x, y);

			text = "Sim";
			x = 300;
			y += gp.tileSize * 4.5;
			g2.drawString(text, x, y);
			if (commandNum == 0) {
				g2.drawString(">", x - gp.tileSize, y);
			}
			
			text = "Não";
			x += gp.tileSize * 3;
			g2.drawString(text, x, y);
			if (commandNum == 1) {
				g2.drawString(">", x - gp.tileSize, y);
			}

		}

	}
	public void drawDialogueScreen() {

		// JANELA
		int x = gp.tileSize * 2;
		int y = gp.tileSize / 2;
		int width = gp.screenWidth - (gp.tileSize * 4);
		int height = gp.tileSize * 4;
		drawSubWindow(x, y, width, height);

		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));
		x += gp.tileSize;
		y += gp.tileSize;

		for (String line : currentDialogue.split("\n")) {
			g2.drawString(line, x, y);
			y += 40;
		}
	}
    public void drawCharacterScreen() {
    	
    	//CRIAR O FRAME
    	final int frameX = gp.tileSize;
    	final int frameY = gp.tileSize - 20;
    	final int frameWidth = gp.tileSize*6;
    	final int frameHeight = gp.tileSize*11;
    	drawSubWindow(frameX, frameY, frameWidth, frameHeight);
    	
    	//IMAGEM DO PERSONAGEM
    	g2.setColor(Color.white);
    	g2.drawImage(gp.player.faceImage1, frameX + 165, frameY + 25, null);
    	g2.drawRoundRect(frameX + 165, frameY + 25, gp.tileSize*2, gp.tileSize*2, 10, 10);
    	
    	//TEXT
    	g2.setColor(Color.white);
    	g2.setFont(g2.getFont().deriveFont(32F));
    	
    	int textX = frameX + 20;
    	int textY = frameY + gp.tileSize;
    	final int lineHeight = 35;
    	
    	//NOMES
    	g2.drawString(gp.player.nome, textX, textY);
    	textY += lineHeight + 75;
    	g2.drawString("Level", textX, textY);
    	textY += lineHeight;
    	g2.drawString("Saúde", textX, textY);
    	textY += lineHeight;
    	g2.drawString("Força", textX, textY);
    	textY += lineHeight;
    	g2.drawString("Destreza", textX, textY);
    	textY += lineHeight;
    	g2.drawString("Ataque", textX, textY);
    	textY += lineHeight;
    	g2.drawString("Defesa", textX, textY);
    	textY += lineHeight;
    	g2.drawString("Exp", textX, textY);
    	textY += lineHeight;
    	g2.drawString("Próximo Level", textX, textY);
    	textY += lineHeight;
    	g2.drawString("Moedas", textX, textY);
    	textY += lineHeight + 20;
    	g2.drawString("Arma", textX, textY);
    	textY += lineHeight + 20;
    	
    	//VALORES
    	int tailX = (frameX + frameWidth)-30;
    	//Resetar textY
    	textY = frameY + gp.tileSize + 109;
    	String value;
    	
    	value = String.valueOf(gp.player.level);
    	textX = getXforAlignToRightText(value, tailX);
    	g2.drawString(value, textX, textY);
    	textY += lineHeight;
    	
    	value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
    	textX = getXforAlignToRightText(value, tailX);
    	g2.drawString(value, textX, textY);
    	textY += lineHeight;
    	
    	value = String.valueOf(gp.player.strength);
    	textX = getXforAlignToRightText(value, tailX);
    	g2.drawString(value, textX, textY);
    	textY += lineHeight;
    	
    	value = String.valueOf(gp.player.dexterity);
    	textX = getXforAlignToRightText(value, tailX);
    	g2.drawString(value, textX, textY);
    	textY += lineHeight;
    	
    	value = String.valueOf(gp.player.attack);
    	textX = getXforAlignToRightText(value, tailX);
    	g2.drawString(value, textX, textY);
    	textY += lineHeight;
    	
    	value = String.valueOf(gp.player.defense);
    	textX = getXforAlignToRightText(value, tailX);
    	g2.drawString(value, textX, textY);
    	textY += lineHeight;
    	
    	value = String.valueOf(gp.player.exp);
    	textX = getXforAlignToRightText(value, tailX);
    	g2.drawString(value, textX, textY);
    	textY += lineHeight;
    	
    	value = String.valueOf(gp.player.nextLevelExp);
    	textX = getXforAlignToRightText(value, tailX);
    	g2.drawString(value, textX, textY);
    	textY += lineHeight;
    	
    	value = String.valueOf(gp.player.coin);
    	textX = getXforAlignToRightText(value, tailX);
    	g2.drawString(value, textX, textY);
    	textY += lineHeight + 20;
    	
    	if(gp.player.currentWeapon != null) {
    		g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY-35, null);
    		textY += gp.tileSize;
    	}
    	else {
    		value = "X";
        	textX = getXforAlignToRightText(value, tailX);
        	g2.drawString(value, textX, textY);
        	textY += lineHeight;
    	}
    }
    
    public void drawInventory() {
    	
    	//FRAME
    	int frameX = gp.tileSize*9;
    	int frameY = gp.tileSize;
    	int frameWidth = gp.tileSize*6;
    	int frameHeight = gp.tileSize*3;
    	drawSubWindow(frameX, frameY, frameWidth, frameHeight);
    	
    	//SLOT
    	final int slotXstart = frameX + 20;
    	final int slotYstart = frameY + 20;
    	int slotX = slotXstart;
    	int slotY = slotYstart;
    	int slotSize = gp.tileSize+3;
    	
    	//DESENHAR ITEMS DO JOGADOR
    	for(int i = 0; i < gp.player.inventory.size(); i++) {
    		
    		//EQUIPE CURSOR
    		if(gp.player.inventory.get(i) == gp.player.currentWeapon || 
    				gp.player.inventory.get(i) == gp.player.currentShield) {
    			g2.setColor(new Color(240,190,90));
    			g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
    		}
    		
    		g2.drawImage(gp.player.inventory.get(i).down1, slotX, slotY, null);
    		
    		slotX += slotSize;
    		
    		if(i == 4 || i == 9 || i == 14) {
    			slotX = slotXstart;
    			slotY += slotSize;
    		}
    	}
    	
    	//CURSOR
    	int cursorX = slotXstart + (slotSize * slotCol);
    	int cursorY = slotYstart + (slotSize * slotRow);
    	int cursorWidth = gp.tileSize;
    	int cursorHeight = gp.tileSize;
    	
    	//DESENHAR CURSOR
    	g2.setColor(Color.white);
    	g2.setStroke(new BasicStroke(3));
    	g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
    	
    	//QUADRO DE DESCRIÇÃO
    	int dFrameX = frameX;
    	int dFrameY = frameY + frameHeight;
    	int dFrameWidth = frameWidth;
    	int dFrameHeight = gp.tileSize*5;
    	
    	//DESENHAR TEXTO DA DESCRIÇÃO
    	int textX = dFrameX + 20;
    	int textY = dFrameY + gp.tileSize;
    	g2.setFont(g2.getFont().deriveFont(23F));
    	
    	int itemIndex = getItemIndexOnSlot();
    	if(itemIndex < gp.player.inventory.size()) {
    		
    		drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);
    		
    		for(String line: gp.player.inventory.get(itemIndex).detalhes.split("\n")) {
    			g2.drawString(line, textX, textY);
    			textY += 32;
    		}
    	}
    }
    public int getItemIndexOnSlot() {
    	int itemIndex = slotCol + (slotRow*5);
    	return itemIndex;
    }
	public void drawSubWindow(int x, int y, int width, int height) {

		Color c = new Color(0, 0, 0, 210); // O ULTIMO NUMERO INDICA A OPACIDADE
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);

		c = new Color(255, 255, 255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);

	}

	public void drawPauseScreen() {

		g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
		String text = "PAUSA";
		int x = getXForCenteredText(text);
		int y = gp.screenHeight / 2 + 40;

		g2.drawString(text, x, y);

	}
	public int getXforAlignToRightText(String text, int tailX) {
		int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = tailX - length;
		return x;
	}
	public int getXForCenteredText(String text) {

		int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth / 2 - length / 2;
		return x;
	}

}
