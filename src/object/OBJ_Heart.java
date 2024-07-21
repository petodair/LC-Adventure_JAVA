package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity{

	public OBJ_Heart(GamePanel gp) {

		super(gp);

		name = "Coração";
		image = setup("/objects/coracao", gp.tileSize, gp.tileSize);
		image2 = setup("/objects/meio_coracao", gp.tileSize, gp.tileSize);
		image3 = setup("/objects/coracao_vazio", gp.tileSize, gp.tileSize);
			
	}

}
