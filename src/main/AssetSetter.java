package main;

import enemies.MON_Blueberdito;
import object.OBJ_Arco;
import object.OBJ_Blueberry;
import object.OBJ_Graveto;
import tile_interactive.IT_Box;
import tile_interactive.IT_Rock;

public class AssetSetter {
	
	GamePanel gp;

	public AssetSetter(GamePanel gp) {
		
		this.gp = gp;
	}
	
	public void setObject() {
		int i = 0;
		gp.obj[i] = new OBJ_Graveto(gp);
		gp.obj[i].worldX = gp.tileSize*19;
		gp.obj[i].worldY = gp.tileSize*57;
		i++;
		gp.obj[i] = new OBJ_Blueberry(gp);
		gp.obj[i].worldX = gp.tileSize*28;
		gp.obj[i].worldY = gp.tileSize*18;
		i++;
		gp.obj[i] = new OBJ_Arco(gp);
		gp.obj[i].worldX = gp.tileSize*26;
		gp.obj[i].worldY = gp.tileSize*18;
		i++;
	}
	public void setNPC() {
	   //gp.npc[0] = new NPC_OldMan(gp);
	   //gp.npc[0].worldX = gp.tileSize*9;
	   //gp.npc[0].worldY = gp.tileSize*13;
	}
	public void setMonster() {
		int i = 0;
		gp.monster[i] = new MON_Blueberdito(gp);
		gp.monster[i].worldX = gp.tileSize*46;
		gp.monster[i].worldY = gp.tileSize*42;
		i++;
		gp.monster[i] = new MON_Blueberdito(gp);
		gp.monster[i].worldX = gp.tileSize*48;
		gp.monster[i].worldY = gp.tileSize*41;
		i++;
	}
	public void setInteractiveTile() {
		int i = 0;
		gp.iTile[i] = new IT_Box(gp, 40, 42);i++;
		gp.iTile[i] = new IT_Rock(gp, 25, 20);i++;
	}
}
