package object;

import java.awt.Color;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class OBJ_FlechaProjectile extends Projectile{
	
	GamePanel gp;

	public OBJ_FlechaProjectile(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = "Flecha Disparada";
		speed = 10;
		maxLife = 40;
		life = maxLife;
		attack = 2;
		useCost = 1;
		alive = false;
		getImage();
	}
	public void getImage() {
		up1 = setup("/projectile/flecha_up", gp.tileSize, gp.tileSize);
		down1 = setup("/projectile/flecha_down", gp.tileSize, gp.tileSize);
		left1 = setup("/projectile/flecha_left", gp.tileSize, gp.tileSize);
		right1 = setup("/projectile/flecha_right", gp.tileSize, gp.tileSize);
	}
	public boolean haveResource(Entity user) {
		boolean haveResource = false;
		if(user.flechas >= useCost) {
			haveResource = true;
		}
		return haveResource;
	}
	public void subtractResource(Entity user) {
		user.flechas -= useCost;
	}
	public Color getParticleColor() {
		Color color = new Color(212,207,42);
		return color;
	}
	public int getParticleSize() {
		int size = 10;
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
