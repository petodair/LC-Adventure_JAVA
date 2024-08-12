package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class Entity {

	GamePanel gp;
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1,
			attackRight2;
	public BufferedImage image, image2, image3;
	public boolean collision = false;
	String dialogues[] = new String[20];

	// RETÂNGULO DE COLISÃO
	public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
	public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
	public int solidAreaDefaultX, solidAreaDefaultY;

	// STATE
	public int worldX, worldY;
	public String direction = "down";
	public int spriteNum = 1;
	int dialogueIndex = 0;
	public boolean collisionOn = false;
	public boolean invencible = false;
	boolean attacking = false;
	public boolean alive = true;
	public boolean dying = false;
	boolean hpBarOn = false;
	boolean damageInfoOn = false;
	public int receivedDamage;

	// COUNTER
	public int spriteCounter = 0;
	public int actionLockCounter = 0;
	public int invencibleCounter = 0;
	public int shotAvailableCounter = 0;
	int dyingCounter = 0;
	int hpBarCounter = 0;
	int damageInfoCounter = 0;

	// ATRIBUTOS DO PERSONAGEM
	public String name;
	public int speed;
	public int maxLife;
	public int life;
	public int maxPositiveEnergy;
	public int positiveEnergy;
	public int level;
	public int strength;
	public int energy;
	public int inspiration;
	public int dexterity;
	public int attack;
	public int defense;
	public int exp;
	public int nextLevelExp;
	public int coin;
	public int flechas;
	public Entity currentWeapon;
	public Entity currentShield;
	public Projectile projectile;
	public int gender;
	public int man = 1;
	public int woman = 2;

	// ATRIBUTOS DO ITEM
	public int attackValue;
	public int defenseValue;
	public String detalhes = "";
	public int useCost;
	public int value;
	
	//TIPO
	public int type; // 0 = player, 1 = npc, 2 = monster
	public final int type_player = 0;
	public final int type_npc = 1;
	public final int type_monster = 2;
	public final int type_sword = 3;
	public final int type_axe = 4;
	public final int type_shield = 5;
	public final int type_incomum = 6;
	public final int type_consumable = 7;
	public final int type_pickupOnly = 8;
	public final int type_bow = 9;

	public Entity(GamePanel gp) {
		this.gp = gp;
	}

	public void setAction() {
	}

	public void damageReaction() {
	}

	public void speak() {

		if (dialogues[dialogueIndex] == null) {
			dialogueIndex = 0;
		}
		gp.ui.currentDialogue = dialogues[dialogueIndex];
		dialogueIndex++;

		switch (gp.player.direction) {
		case "up":
			direction = "down";
			break;
		case "down":
			direction = "up";
			break;
		case "left":
			direction = "right";
			break;
		case "right":
			direction = "left";
			break;
		}

	}
	
	public void use(Entity entity) {
		
	}
	
	public void checkDrop() {}
	
	public void dropItem(Entity droppedItem) {
		for(int i = 0; i < gp.obj.length; i++) {
			if(gp.obj[i] == null) {
				gp.obj[i] = droppedItem;
				gp.obj[i].worldX = worldX; //POSIÇAO DO MONSTRO DERRORTADO
				gp.obj[i].worldY = worldY;
				break;
			}
		}
	}
	
	public Color getParticleColor() {
		Color color = null;
		return color;
	}
	public int getParticleSize() {
		int size = 0;
		return size;
	}
	public int getParticleSpeed() {
		int speed = 0;
		return speed;
	}
	public int getParticleMaxLife() {
		int maxLife = 0;
		return maxLife;
	}
	public void generateParticle(Entity generator, Entity target) {
		
		Color color = generator.getParticleColor();
		int size = generator.getParticleSize();
		int speed = generator.getParticleSpeed();
		int maxLife = generator.getParticleMaxLife();
		
		Particle p1 = new Particle(gp, target, color, size, speed, maxLife, -2, -1);
		Particle p2 = new Particle(gp, target, color, size, speed, maxLife, 2, -1);
		Particle p3 = new Particle(gp, target, color, size, speed, maxLife, -2, 1);
		Particle p4 = new Particle(gp, target, color, size, speed, maxLife, 2, 1);
		gp.particleList.add(p1);
		gp.particleList.add(p2);
		gp.particleList.add(p3);
		gp.particleList.add(p4);
	}

	public void update() {

		setAction();

		collisionOn = false;
		gp.cChecker.checkTile(this);
		gp.cChecker.checkObject(this, false);
		gp.cChecker.checkEntity(this, gp.npc);
		gp.cChecker.checkEntity(this, gp.monster);
		gp.cChecker.checkEntity(this, gp.iTile);
		boolean contactPlayer = gp.cChecker.checkPlayer(this);

		// SE FOR TIPO 2(MONSTRO) TOCAR NO PLAYER
		if (this.type == type_monster && contactPlayer == true) {
			damagePlayer(attack);
		}

		// SE A COLISÃO FOR FALSA O PLAYER PODE SE MOVER
		if (collisionOn == false) {
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
		
		//DEIXA INVENCÍVEL POR 40 QUADROS
		if (invencible == true) {
			invencibleCounter++;
			if (invencibleCounter > 40) {
				invencible = false;
				invencibleCounter = 0;
			}
		}
		if (shotAvailableCounter < 30) {
			shotAvailableCounter++;
		}
	}
    public void damagePlayer(int attack) {
    	
    	if (gp.player.invencible == false) {
			gp.playSE(6);

			int damage = attack - gp.player.defense;
			if (damage < 0) {
				damage = 0;
			}
			gp.player.life -= damage;
			gp.player.invencible = true;
		}
    }
	public void draw(Graphics2D g2) {

		BufferedImage image = null;
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;

		// DESENHA APENAS OS BLOCOS AO REDOR PLAYER
		// PARA ECONOMIZAR PROCESSAMENTO
		if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
				&& worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
				&& worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
				&& worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

			switch (direction) {
			case "up":
				if (spriteNum == 1) {
					image = up1;
				}
				if (spriteNum == 2) {
					image = up2;
				}
				break;
			case "down":
				if (spriteNum == 1) {
					image = down1;
				}
				if (spriteNum == 2) {
					image = down2;
				}
				break;
			case "left":
				if (spriteNum == 1) {
					image = left1;
				}
				if (spriteNum == 2) {
					image = left2;
				}
				break;
			case "right":
				if (spriteNum == 1) {
					image = right1;
				}
				if (spriteNum == 2) {
					image = right2;
				}
				break;
			}

			// MONSTER HP BAR
			if (type == 2 && hpBarOn == true) {

				double oneScale = (double) gp.tileSize / maxLife;
				double hpBarValue = oneScale * life;

				// BARRINHA DO FUNDO
				g2.setColor(new Color(35, 35, 35));
				g2.fillRect(screenX - 1, screenY - 16, gp.tileSize + 2, 10);

				// QUANTIDADE DE HP
				g2.setColor(new Color(235, 0, 20));
				g2.fillRect(screenX, screenY - 15, (int) hpBarValue, 8);

				// DANO RECEBIDO

				if (damageInfoOn == true) {
					g2.setFont(g2.getFont().deriveFont(15F));
					g2.setColor(new Color(235, 0, 20));
					g2.drawString("-" + receivedDamage, screenX + 17, screenY - 18);
					
					damageInfoCounter++;
					
					if (damageInfoCounter > 100) {
						damageInfoCounter = 0;
						damageInfoOn = false;
					}
				}

				hpBarCounter++;

				// BARRA DESAPARECE APÓS 10 SEGUNDOS
				if (hpBarCounter > 600) {
					hpBarCounter = 0;
					hpBarOn = false;
				}
				
			}

			if (invencible == true) {
				hpBarOn = true;
				hpBarCounter = 0;
				changeAlpha(g2, 0.4F);
			}
			if (dying == true) {
				dyingAnimation(g2);
			}

			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);

			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

		}
	}

	public void dyingAnimation(Graphics2D g2) {

		dyingCounter++;

		// INTERVALO DA ANIMAÇÃO
		int i = 5;

		if (dyingCounter <= i) {
			changeAlpha(g2, 0f);
		}
		if (dyingCounter > i && dyingCounter <= i * 2) {
			changeAlpha(g2, 1f);
		}
		if (dyingCounter > i * 2 && dyingCounter <= i * 3) {
			changeAlpha(g2, 0f);
		}
		if (dyingCounter > i * 3 && dyingCounter <= i * 4) {
			changeAlpha(g2, 1f);
		}
		if (dyingCounter > i * 4 && dyingCounter <= i * 5) {
			changeAlpha(g2, 0f);
		}
		if (dyingCounter > i * 5 && dyingCounter <= i * 6) {
			changeAlpha(g2, 1f);
		}
		if (dyingCounter > i * 6 && dyingCounter <= i * 7) {
			changeAlpha(g2, 0f);
		}
		if (dyingCounter > i * 7 && dyingCounter <= i * 8) {
			changeAlpha(g2, 1f);
		}
		if (dyingCounter > i * 8) {
			alive = false;
		}
	}

	public void changeAlpha(Graphics2D g2, float alphaValue) {

		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
	}

	public BufferedImage setup(String imagePath, int width, int height) {

		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;

		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath + ".png"));
			image = uTool.scaleImage(image, width, height);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

}
