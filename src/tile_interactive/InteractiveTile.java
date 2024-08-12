package tile_interactive;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import entity.Entity;
import main.GamePanel;

public class InteractiveTile extends Entity {

	GamePanel gp;
	public boolean destructible = false;

	public InteractiveTile(GamePanel gp, int col, int row) {
		super(gp);
		this.gp = gp;

		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;
	}

	public boolean isCorrectItem(Entity entity) {
		boolean isCorrectItem = false;
		return isCorrectItem;
	}

	public void playSE() {

	}

	public InteractiveTile getDestroyedForm() {
		InteractiveTile tile = null;
		return tile;
	}

	public void update() {

		if (invencible == true) {
			invencibleCounter++;
			if (invencibleCounter > 20) {
				invencible = false;
				invencibleCounter = 0;
			}
		}
	}
	
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		int screenY = worldY - gp.player.worldY + gp.player.screenY;

		// DESENHA APENAS OS BLOCOS AO REDOR PLAYER
		// PARA ECONOMIZAR PROCESSAMENTO
		if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
				&& worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
				&& worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
				&& worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

			g2.drawImage(down1, screenX, screenY, null);

		}
	}

}
