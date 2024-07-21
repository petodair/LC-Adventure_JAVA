package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Porta extends Entity {

	public OBJ_Porta(GamePanel gp) {

		super(gp);

		name = "Porta";
		down1 = setup("/objects/door", gp.tileSize, gp.tileSize);
		collision = true;
	}

}
