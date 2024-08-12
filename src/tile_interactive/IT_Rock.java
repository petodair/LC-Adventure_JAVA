package tile_interactive;

import entity.Entity;
import main.GamePanel;

public class IT_Rock extends InteractiveTile {

	GamePanel gp;

	public IT_Rock(GamePanel gp, int col, int row) {
		super(gp, col, row);
		this.gp = gp;

		name = "pedra";
		down1 = setup("/tiles_interactive/pedra", gp.tileSize, gp.tileSize);
		destructible = true;
	}

	public boolean isCorrectItem(Entity entity) {
		boolean isCorrectItem = false;

		if (entity.currentWeapon.type == type_axe) {
			isCorrectItem = true;
		}

		return isCorrectItem;
	}

}
