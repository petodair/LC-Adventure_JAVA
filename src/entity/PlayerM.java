package entity;

import main.GamePanel;
import main.KeyHandler;

public class PlayerM extends Player {

	public PlayerM(GamePanel gp, KeyHandler keyH) {
		super(gp, keyH);
		faceImage1 = setup("/player/playerM/face/charm_face1", gp.tileSize*2,gp.tileSize*2);
		gender = man;
	}

	public void getPlayerImage() {
		up1 = setup("/player/playerM/charm_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/player/playerM/charm_up_2", gp.tileSize, gp.tileSize);
		down1 = setup("/player/playerM/charm_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/player/playerM/charm_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/player/playerM/charm_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/player/playerM/charm_left_2", gp.tileSize, gp.tileSize);
		right1 = setup("/player/playerM/charm_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/player/playerM/charm_right_2", gp.tileSize, gp.tileSize);
	}

	public void getPlayerAttackImage() {

		if (currentWeapon != null) {
			if (currentWeapon.name.equals("Graveto")) {
				attackUp1 = setup("/player/playerM/graveto/charm_attack_up1", gp.tileSize, gp.tileSize * 2);
				attackUp2 = setup("/player/playerM/graveto/charm_attack_up2", gp.tileSize, gp.tileSize * 2);
				attackDown1 = setup("/player/playerM/graveto/charm_attack_down1", gp.tileSize, gp.tileSize * 2);
				attackDown2 = setup("/player/playerM/graveto/charm_attack_down2", gp.tileSize, gp.tileSize * 2);
				attackLeft1 = setup("/player/playerM/graveto/charm_attack_left1", gp.tileSize * 2, gp.tileSize);
				attackLeft2 = setup("/player/playerM/graveto/charm_attack_left2", gp.tileSize * 2, gp.tileSize);
				attackRight1 = setup("/player/playerM/graveto/charm_attack_right1", gp.tileSize * 2, gp.tileSize);
				attackRight2 = setup("/player/playerM/graveto/charm_attack_right2", gp.tileSize * 2, gp.tileSize);
			}
			else if  (currentWeapon.name.equals("Arco")) {
				attackUp1 = setup("/player/playerM/arco/charm_bow_up1", gp.tileSize, gp.tileSize * 2);
				attackUp2 = setup("/player/playerM/arco/charm_bow_up2", gp.tileSize, gp.tileSize * 2);
				attackDown1 = setup("/player/playerM/arco/charm_bow_down1", gp.tileSize, gp.tileSize * 2);
				attackDown2 = setup("/player/playerM/arco/charm_bow_down2", gp.tileSize, gp.tileSize * 2);
				attackLeft1 = setup("/player/playerM/arco/charm_bow_left1", gp.tileSize * 2, gp.tileSize);
				attackLeft2 = setup("/player/playerM/arco/charm_bow_left2", gp.tileSize * 2, gp.tileSize);
				attackRight1 = setup("/player/playerM/arco/charm_bow_right1", gp.tileSize * 2, gp.tileSize);
				attackRight2 = setup("/player/playerM/arco/charm_bow_right2", gp.tileSize * 2, gp.tileSize);
			}
		}
	}

}
