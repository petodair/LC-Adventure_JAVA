package tile_interactive;

import main.GamePanel;

public class IT_Broken_Box extends InteractiveTile {

	GamePanel gp;

	public IT_Broken_Box(GamePanel gp, int col, int row) {
		super(gp, col, row);

		this.gp = gp;

		name = "caixa destruida";

		down1 = setup("/tiles_interactive/caixa_destruida", gp.tileSize, gp.tileSize);
		
		//REMOVENDO A COLISÃO
		solidArea.x = 0;
		solidArea.y = 0;
		solidArea.width = 0;
		solidArea.height = 0;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}

}
