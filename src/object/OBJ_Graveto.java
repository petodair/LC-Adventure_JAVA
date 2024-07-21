package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Graveto extends Entity{

	public OBJ_Graveto(GamePanel gp) {
		super(gp);
		name = "Graveto";
		down1 = setup("/objects/graveto", gp.tileSize, gp.tileSize);
		attackValue = 1;
		defenseValue = 1;
		
	}

}
