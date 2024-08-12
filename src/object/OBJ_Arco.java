package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Arco extends Entity{

	public OBJ_Arco(GamePanel gp) {
		super(gp);
		name = "Arco";
		down1 = setup("/objects/arco", gp.tileSize, gp.tileSize);
		attackValue = 1;
		detalhes = "[" + name + "]";
		type = type_bow;
		projectile = new OBJ_FlechaProjectile(gp);
	}

}
