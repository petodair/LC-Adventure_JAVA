package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Bau extends Entity{
	
	public OBJ_Bau(GamePanel gp) {
		
       super(gp);
		
		name = "Baú";
		down1 = setup("/objects/chest", gp.tileSize, gp.tileSize);
	}

}
