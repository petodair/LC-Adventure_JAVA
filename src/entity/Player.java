package entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.GamePanel;
import main.KeyHandler;
import object.OBJ_Graveto;

public class Player extends Entity {

	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;

	public BufferedImage faceImage1;
	public String nome;
	public boolean attackCanceled = false;
	public ArrayList<Entity> inventory = new ArrayList<>();
	public int maxInventorySize = 10; 

	public Player(GamePanel gp, KeyHandler keyH) {

		super(gp);

		this.keyH = keyH;

		// EXIBIR O JOGADOR NO CENTRO DA TELA
		screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
		screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

		// DEFININDO O TAMANHO DA AREA DE COLISÃO
		// O IDEAL É NÃO COBRIR O SPRITE INTEIRO
		// DO PLAYER
		solidArea = new Rectangle();
		solidArea.x = 8;
		solidArea.y = 16;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		solidArea.width = 32;
		solidArea.height = 32;
		
		//attackArea.width = 36;
		//attackArea.height = 36;

		nome = "Viajante";

		setDefaultValues();
		getPlayerImage();
		getPlayerAttackImage();
		setItems();
	}

	public void setDefaultValues() {

		// POSIÇÃO INICIAL DO JOGADOR
		worldX = gp.tileSize * 21;
		worldY = gp.tileSize * 20;
		speed = 4;
		direction = "right";
		strength = 1; // Quanto mais forte for, mais dano ele irá causar
		dexterity = 1;
		exp = 0;
		nextLevelExp = 5;
		coin = 0;
		attack = getAttack();
		defense = 1;
		
		// STATUS DO JOGADOR
		level = 1;
		maxLife = 6;
		life = maxLife;
	}
	public void setItems() {
		
	}
	public int getAttack() {
		attack = 1;
		if(currentWeapon != null) {
			attackArea = currentWeapon.attackArea;
			attack = strength * currentWeapon.attackValue;
		}
		return attack;
	}

	public void getPlayerImage() {

		up1 = setup("/player/charm_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/player/charm_up_2", gp.tileSize, gp.tileSize);
		down1 = setup("/player/charm_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/player/charm_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/player/charm_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/player/charm_left_2", gp.tileSize, gp.tileSize);
		right1 = setup("/player/charm_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/player/charm_right_2", gp.tileSize, gp.tileSize);
	}

	public void getPlayerAttackImage() {
		
		if(currentWeapon != null) {
			if(currentWeapon.name.equals("Graveto")) {
				attackUp1 = setup("/player/charm_attack_up1", gp.tileSize, gp.tileSize * 2);
				attackUp2 = setup("/player/charm_attack_up2", gp.tileSize, gp.tileSize * 2);
				attackDown1 = setup("/player/charm_attack_down1", gp.tileSize, gp.tileSize * 2);
				attackDown2 = setup("/player/charm_attack_down2", gp.tileSize, gp.tileSize * 2);
				attackLeft1 = setup("/player/charm_attack_left1", gp.tileSize * 2, gp.tileSize);
				attackLeft2 = setup("/player/charm_attack_left2", gp.tileSize * 2, gp.tileSize);
				attackRight1 = setup("/player/charm_attack_right1", gp.tileSize * 2, gp.tileSize);
				attackRight2 = setup("/player/charm_attack_right2", gp.tileSize * 2, gp.tileSize);
			}
		}
	}

	public void update() {
		
		if(attacking == true) {
			attacking();
		}

		// ESSA CONDIÇAO FAZ A ANIMAÇÃO SÓ ACONTECER SE ALGUMA
		// TECLA FOR PRESSIONADA, SE QUISER QUE ELA SEJA CONINUA MESMO
		// SEM PRESSIONAR TECLAS, REMOVA ESSA CONDIÇÃO
		else if (keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true
				|| keyH.enterPressed == true) {

			if (keyH.upPressed == true) {
				direction = "up";
			} else if (keyH.downPressed == true) {
				direction = "down";
			} else if (keyH.leftPressed == true) {
				direction = "left";
			} else if (keyH.rightPressed == true) {
				direction = "right";
			}

			// CHECAR COLISÃO DO TILE
			collisionOn = false;
			gp.cChecker.checkTile(this);

			// CHECAR COLISÃO DO OBJETO
			int objIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);

			// CHECAR COLISÃO DO NPC
			int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
			interactNPC(npcIndex);

			// CHECAR COLISÃO DO MONSTRO
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			contactMonster(monsterIndex);

			// CHECAR EVENTO
			gp.eHandler.checkEvent();

			// SE A COLISÃO FOR FALSA O PLAYER PODE SE MOVER
			if (collisionOn == false && keyH.enterPressed == false) {
				switch (direction) {
				case "up":
					worldY -= speed;
					break;
				case "down":
					worldY += speed;
					break;
				case "left":
					worldX -= speed;
					break;
				case "right":
					worldX += speed;
					break;
				}
			}
			
			if(keyH.enterPressed == true && attackCanceled == false && currentWeapon != null) {
				attacking = true;
				spriteCounter = 0;
			}
			attackCanceled = false;

			gp.keyH.enterPressed = false;

			// FAZ A IMAGEM DO PLAYER MUDAR A CADA 12 QUADROS PARA 1 OU 2
			spriteCounter++;
			if (spriteCounter > 12) {
				if (spriteNum == 1) {
					spriteNum = 2;
				} else if (spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		}

		if (invencible == true) {
			invencibleCounter++;
			if (invencibleCounter > 60) {
				invencible = false;
				invencibleCounter = 0;
			}
		}
	}
	public void attacking() {
		
		spriteCounter++;
		
		if(spriteCounter <= 5) {
			spriteNum = 1;
		}
		if(spriteCounter > 5 && spriteCounter <= 25) {
			spriteNum = 2;
			
			//SALVAR ATUAL WORLDX, WORLDY, SOLID AREA
			int currentWorldX = worldX;
			int currentWorldY = worldY;
			int solidAreaWidth = solidArea.width;
			int solidAreaHeight = solidArea.height;
			
			//AJUSTAR WORLDX/Y DO PLAYER PARA A AREA DE ATAQUE
			switch(direction) {
			case "up": worldY -= attackArea.height; break;
			case "down": worldY += attackArea.height; break;
			case "left": worldX -= attackArea.width; break;
			case "right": worldX += attackArea.width; break;
			}
			
			solidArea.width = attackArea.width;
			solidArea.height = attackArea.height;
			
			int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
			damageMonster(monsterIndex);
			
			worldX = currentWorldX;
			worldY = currentWorldY;
			solidArea.width = solidAreaWidth;
			solidArea.height = solidAreaHeight;
			
		}
		if(spriteCounter > 25) {
			spriteNum = 1;
			spriteCounter = 0;
			attacking = false;
		}
		
	}
	// PEGAR OBJETO
	public void pickUpObject(int i) {
		
		String text = "";

		if (i != 999) {
			
			String objectName = gp.obj[i].name;
			
			if(inventory.size() != maxInventorySize) {
				
				inventory.add(gp.obj[i]);
				gp.obj[i] = null;
				
				if(objectName.equals("Graveto")) {
					equipWeapon(inventory.get(i));
					gp.ui.currentDialogue = "Você encontrou um graveto!\n" + "bem impressionante, não é?";
				}
				else {
					gp.ui.currentDialogue = "Você encontrou " + objectName + "!";
				}
				
			}
			else {
				gp.ui.currentDialogue = "Você não pode carregar mais nada!";
			}
			gp.gameState = gp.dialogueState;
		}
	}

	public void interactNPC(int i) {

		if (gp.keyH.enterPressed == true) {

			if (i != 999) {
				attackCanceled = true;
				gp.gameState = gp.dialogueState;
				gp.npc[i].speak();
			} 
		}
	}

	public void contactMonster(int i) {

		if (i != 999) {

			if (invencible == false && gp.monster[i].dying) {
				gp.playSE(6);
				
				int damage = gp.monster[i].attack - defense;
				if(damage < 0) {
					damage = 0;
				}
				
				life -= damage;
				invencible = true;
			}
		}
	}
	public void damageMonster(int i) {
		if (i != 999) {
			
			if(gp.monster[i].invencible == false) {
				gp.playSE(5);
				
				int damage = attack - gp.monster[i].defense;
				if(damage < 0) {
					damage = 0;
				}
				gp.monster[i].life -= damage;
				gp.ui.addMessage("causou " + damage + " de dano!");
				
				//MOSTRAR DANO CAUSADO
				gp.monster[i].receivedDamage = damage;
				gp.monster[i].damageInfoOn = true;
				
				gp.monster[i].invencible = true;
				gp.monster[i].damageReaction();
				
				if(gp.monster[i].life <= 0) {
					gp.monster[i].dying = true;
					gp.ui.addMessage("derrotou " + gp.monster[i].name + "!");
					gp.ui.addMessage("+" + gp.monster[i].exp + "exp!");
					exp += gp.monster[i].exp;
					checkLevelUp();
				}
			}
		}
	}
	
	public void checkLevelUp() {
		
		if(exp >= nextLevelExp) {
			
			level++;
			nextLevelExp = nextLevelExp*2;
			maxLife += 2;
			strength++;
			dexterity++;
			attack = getAttack();
			defense++;
			
			gp.gameState = gp.dialogueState;
			gp.ui.currentDialogue = "Você chegou ao nível " + level + "!\n"
					+ "Você está mais forte agora!";
		}
	}
	
	public void selectItem() {
		
		int itemIndex = gp.ui.getItemIndexOnSlot();
		
		if(itemIndex < inventory.size()) {
			Entity selectedItem = inventory.get(itemIndex);
			
			if(selectedItem.type == type_sword || selectedItem.type == type_axe
					|| selectedItem.type == type_incomum) {
				equipWeapon(selectedItem);
			}
			if(selectedItem.type == type_consumable) {
				
				// depois
			}
		}
	}
	
	public void equipWeapon(Entity weapon) {
		currentWeapon = weapon;
		getAttack();
		getPlayerAttackImage();
	}

	public void draw(Graphics2D g2) {

		// Desenhar quadrado estático
		// g2.setColor(Color.white);
		// g2.fillRect(x, y, gp.tileSize, gp.tileSize);

		// Desenhar imagem na tela
		BufferedImage image = null;
		
		int tempScreenX = screenX;
		int tempScreenY = screenY;

		switch (direction) {
		case "up":
			if(attacking == false) {
				if (spriteNum == 1) {image = up1;}
				if (spriteNum == 2) {image = up2;}
			}
			if(attacking == true) {
				tempScreenY = screenY - gp.tileSize;
				if (spriteNum == 1) {image = attackUp1;}
				if (spriteNum == 2) {image = attackUp2;}
			}
			break;
		case "down":
			if(attacking == false) {
				if (spriteNum == 1) {image = down1;}
				if (spriteNum == 2) {image = down2;}
			}
			if(attacking == true) {
				if (spriteNum == 1) {image = attackDown1;}
				if (spriteNum == 2) {image = attackDown2;}
			}
			break;
		case "left":
			if(attacking == false) {
				if (spriteNum == 1) {image = left1;}
				if (spriteNum == 2) {image = left2;}
			}
			if(attacking == true) {
				tempScreenX = screenX - gp.tileSize;
				if (spriteNum == 1) {image = attackLeft1;}
				if (spriteNum == 2) {image = attackLeft2;}
			}
			break;
		case "right":
			if(attacking == false) {
				if (spriteNum == 1) {image = right1;}
				if (spriteNum == 2) {image = right2;}
			}
			if(attacking == true) {
				if (spriteNum == 1) {image = attackRight1;}
				if (spriteNum == 2) {image = attackRight2;}
			}
			break;
		}

		if (invencible == true) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
		}

		g2.drawImage(image, tempScreenX, tempScreenY, null);

		// RESET ALPHA
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

		// DEBUG
		// g2.setFont(new Font("Arial", Font.PLAIN, 26));
		// g2.setColor(Color.white);
		// g2.drawString("Invensível:"+invencibleCounter, 10, 400);

		// DESENHAR AREA DE COLISÃO
		// g2.setColor(Color.red);
		// g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width,
		// solidArea.height);

	}

}
