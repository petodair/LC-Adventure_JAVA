package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_PositiveEnergy extends Entity{
	
	GamePanel gp;

	public OBJ_PositiveEnergy(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		name = "Energia Positiva";
		image = setup("/objects/positiveEnergy_full", gp.tileSize, gp.tileSize);
		image2 = setup("/objects/positiveEnergy_half", gp.tileSize, gp.tileSize);
		image3 = setup("/objects/positiveEnergy_blank", gp.tileSize, gp.tileSize);
	}

}
