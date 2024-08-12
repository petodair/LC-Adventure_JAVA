package object;

import java.awt.Color;

import entity.Projectile;
import main.GamePanel;

public class OBJ_BShot extends Projectile{
	
	GamePanel gp;

	public OBJ_BShot(GamePanel gp) {
		super(gp);
		
		this.gp = gp;
		
		name = "Bshot";
		speed = 8;
		maxLife = 80;
		life = maxLife;
		attack = 2;
		useCost = 1;
		alive = false;
		getImage();
	}
	public void getImage() {
		up1 = setup("/projectile/Bshot", gp.tileSize, gp.tileSize);
		down1 = setup("/projectile/Bshot", gp.tileSize, gp.tileSize);
		left1 = setup("/projectile/Bshot", gp.tileSize, gp.tileSize);
		right1 = setup("/projectile/Bshot", gp.tileSize, gp.tileSize);
	}
	public Color getParticleColor() {
		Color color = new Color(30,82,141);
		return color;
	}
	public int getParticleSize() {
		int size = 4;
		return size;
	}
	public int getParticleSpeed() {
		int speed = 1;
		return speed;
	}
	public int getParticleMaxLife() {
		int maxLife = 20;
		return maxLife;
	}


}

