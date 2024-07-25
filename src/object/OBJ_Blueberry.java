package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Blueberry extends Entity{

	public OBJ_Blueberry(GamePanel gp) {
		super(gp);
		
		type = type_consumable;
		name = "Blueberry";
		down1 = setup("/objects/blueberry", gp.tileSize, gp.tileSize);
		detalhes = "["+ name +"]\nFruta pequena e nutritiva,\n"
				+ "chega a ser revigorante se\n"
				+ "alimentar delas.";
	}
	
	

}
