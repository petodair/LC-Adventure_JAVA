package object;

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

}

