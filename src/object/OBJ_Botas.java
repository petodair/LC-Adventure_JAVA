package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Botas extends Entity{

	public OBJ_Botas(GamePanel gp) {

		super(gp);

		name = "Botas";
		down1 = setup("/objects/boots", gp.tileSize, gp.tileSize);
	}

}
