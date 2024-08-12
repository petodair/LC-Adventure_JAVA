package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Casca_Blueberdito extends Entity{

	GamePanel gp;
	
	public OBJ_Casca_Blueberdito(GamePanel gp) {
		super(gp);
		
		this.gp = gp;
		name = "Casca de blueberdito";
		value = 2;
		detalhes = "["+ name +"]\nBem resistentes.";
		down1 = setup("/objects/casco_blueberdito", gp.tileSize, gp.tileSize);
	}

}
