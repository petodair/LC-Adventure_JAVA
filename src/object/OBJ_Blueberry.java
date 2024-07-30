package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Blueberry extends Entity{
	
	GamePanel gp;

	public OBJ_Blueberry(GamePanel gp) {
		super(gp);
		
		this.gp = gp;
		
		value = 5;
		
		type = type_consumable;
		name = "Blueberry";
		down1 = setup("/objects/blueberry", gp.tileSize, gp.tileSize);
		detalhes = "["+ name +"]\nFruta pequena e nutritiva,\n"
				+ "chega a ser revigorante se\n"
				+ "alimentar delas.";
	}
	public void use(Entity entity) {
		
		gp.gameState = gp.dialogueState;
		gp.ui.currentDialogue = "Você consumiu uma " + name + "!";
		entity.life += value;
		if(gp.player.life > gp.player.maxLife) {
			gp.player.life = gp.player.maxLife;
		}
		gp.playSE(3);
	}
	

}
