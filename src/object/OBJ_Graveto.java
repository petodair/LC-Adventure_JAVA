package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Graveto extends Entity{

	public OBJ_Graveto(GamePanel gp) {
		super(gp);
		name = "Graveto";
		down1 = setup("/objects/graveto", gp.tileSize, gp.tileSize);
		attackValue = 1;
		attackArea.width = 36;
		attackArea.height = 36;
		detalhes = "[" + name + "]\n"
				+ "É um simples graveto, porém\n"
				+ "aparenta ser bem resistente.\n"
				+ "\n"
				+ "Poderia ele ser usado como\n"
				+ "um meio de defesa?";
		type = type_incomum;
		
	}

}
