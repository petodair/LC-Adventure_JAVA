package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Chave extends Entity{
	
	public OBJ_Chave(GamePanel gp) {
		super(gp);
		
		name = "Chave";
		down1 = setup("/objects/key", gp.tileSize, gp.tileSize);
		
	}

}
