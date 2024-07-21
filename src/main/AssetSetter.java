package main;

import enemies.MON_Blueberdito;
import object.OBJ_Graveto;

public class AssetSetter {
	
	GamePanel gp;

	public AssetSetter(GamePanel gp) {
		
		this.gp = gp;
	}
	
	public void setObject() {
		
		gp.obj[0] = new OBJ_Graveto(gp);
		gp.obj[0].worldX = gp.tileSize * 19;
		gp.obj[0].worldY = gp.tileSize * 57;
		
	}
	public void setNPC() {
	   //gp.npc[0] = new NPC_OldMan(gp);
	   //gp.npc[0].worldX = gp.tileSize*9;
	   //gp.npc[0].worldY = gp.tileSize*13;
	}
	public void setMonster() {
		gp.monster[0] = new MON_Blueberdito(gp);
		gp.monster[0].worldX = gp.tileSize * 28;
		gp.monster[0].worldY = gp.tileSize * 18;
		
		gp.monster[1] = new MON_Blueberdito(gp);
		gp.monster[1].worldX = gp.tileSize * 46;
		gp.monster[1].worldY = gp.tileSize * 42;
	}
	
	

}
