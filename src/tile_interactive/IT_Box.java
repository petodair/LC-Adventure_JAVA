package tile_interactive;

import java.awt.Color;

import entity.Entity;
import main.GamePanel;

public class IT_Box extends InteractiveTile{

	GamePanel gp;
	
	public IT_Box(GamePanel gp, int col, int row) {
		super(gp, col, row);
		this.gp = gp;
		
		name = "caixa";
		
		down1 = setup("/tiles_interactive/caixa", gp.tileSize, gp.tileSize);
		destructible = true;
		life = 5;
	}
	public boolean isCorrectItem(Entity entity) {
		boolean isCorrectItem = false;
		
		if(entity.currentWeapon.type == type_incomum) {
			isCorrectItem = true;
		}
		
		return isCorrectItem;
	}
	public void playSE() {

	}
	public InteractiveTile getDestroyedForm() {
		InteractiveTile tile = new IT_Broken_Box(gp, worldX/gp.tileSize, worldY/gp.tileSize);
		return tile;
	}
	public Color getParticleColor() {
		Color color = new Color(142,81,13);
		return color;
	}
	public int getParticleSize() {
		int size = 6;
		return size;
	}
	public int getParticleSpeed() {
		int speed = 1;
		return speed;
	}
	public int getParticleMaxLife() {
		int maxLife = 20;
		return maxLife;
	}
}
