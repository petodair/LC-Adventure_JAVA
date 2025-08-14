package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Sword extends Entity{

	public OBJ_Sword(GamePanel gp) {
		super(gp);
		name = "Espada estranha";
		down1 = setup("/objects/010", gp.tileSize, gp.tileSize);
		attackValue = 1;
		attackArea.width = 36;
		attackArea.height = 36;
		detalhes = "[" + name + "]\n"
				+ "Tem uma espécie de núcleo\n"
				+ "nela, mas não está ativo.\n"
				+ "Passa um estranho sentimento\n"
				+ "de raiva e perca, de onde será\n"
				+ "que ela veio?";
		type = type_incomum;
	}

}
