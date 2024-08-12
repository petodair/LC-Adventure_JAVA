package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Golden_Coin extends Entity {

	GamePanel gp;

	public OBJ_Golden_Coin(GamePanel gp) {
		super(gp);

		this.gp = gp;

		type = type_pickupOnly;
		name = "Moeda de Ouro";
		value = 5;
		down1 = setup("/objects/golden_coin", gp.tileSize, gp.tileSize);
	}

	public void use(Entity entity) {

		gp.playSE(1);
		gp.ui.addMessage("+" + value + "moeda(s)");
		gp.player.coin += value;
	}

}
