package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import entity.PlayerM;
import entity.PlayerW;

public class KeyHandler implements KeyListener {

	GamePanel gp;

	public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shotKeyPressed;
	// DEGUB
	boolean showDebugText = false;

	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {

		int code = e.getKeyCode();

		// TITLE STATE
		if (gp.gameState == gp.titleState) {
			titleState(code);
		}
		// PLAY STATE
		else if (gp.gameState == gp.playState) {
			playState(code);
		}
		// PAUSE STATE
		else if (gp.gameState == gp.pauseState) {
			pauseState(code);
		}
		// DIALOGUE STATE
		else if (gp.gameState == gp.dialogueState) {
			dialogueState(code);
		}
		// CHARACTER STATE
		else if (gp.gameState == gp.characterState) {
			characterState(code);
		}
	}

	public void titleState(int code) {

		// TELA DE INÍCIO
		if (gp.ui.titleScreenState == 0) {
			if (code == KeyEvent.VK_W) {
				gp.ui.commandNum--;
				gp.playSE(7);
				if (gp.ui.commandNum < 0) {
					gp.ui.commandNum = 2;
				}
			}
			if (code == KeyEvent.VK_S) {
				gp.ui.commandNum++;
				gp.playSE(7);
				if (gp.ui.commandNum > 2) {
					gp.ui.commandNum = 0;
				}
			}
			if (code == KeyEvent.VK_ENTER) {
				if (gp.ui.commandNum == 0) {
					gp.ui.titleScreenState = 1;
					gp.playSE(1);
				}
				if (gp.ui.commandNum == 1) {
					// LATER
				}
				if (gp.ui.commandNum == 2) {
					System.exit(0);
				}
			}
		}

		// TELA DE SELEÇÃO DE PERSONAGEM
		else if (gp.ui.titleScreenState == 1) {
			if (code == KeyEvent.VK_W) {
				gp.ui.commandNum--;
				gp.playSE(7);
				if (gp.ui.commandNum < 0) {
					gp.ui.commandNum = 1;
				}
			}
			if (code == KeyEvent.VK_S) {
				gp.ui.commandNum++;
				gp.playSE(7);
				if (gp.ui.commandNum > 1) {
					gp.ui.commandNum = 0;
				}
			}
			if (code == KeyEvent.VK_ENTER) {
				gp.ui.titleScreenState = 2;
				gp.campoNome.setFocusable(true);
				
				if(gp.ui.commandNum == 0) {gp.player = new PlayerM(gp, this);}
				if(gp.ui.commandNum == 1) {gp.player = new PlayerW(gp, this);}
				gp.playSE(1);

				gp.ui.commandNum = 0;
			}
		}
		// TELA DE NOME
		else if (gp.ui.titleScreenState == 2) {

		}
		// TELA DE CONFIRMAÇÃO
		else if (gp.ui.titleScreenState == 3) {

			if (code == KeyEvent.VK_A) {
				gp.ui.commandNum++;
				gp.playSE(7);
				if (gp.ui.commandNum > 1) {
					gp.ui.commandNum = 0;					
				}
			}

			if (code == KeyEvent.VK_D) {
				gp.ui.commandNum++;
				gp.playSE(7);
				if (gp.ui.commandNum > 1) {
					gp.ui.commandNum = 0;
				}
			}

			if (code == KeyEvent.VK_ESCAPE) {
				gp.ui.titleScreenState = 0;
			}

			if (code == KeyEvent.VK_ENTER) {
				if (gp.ui.commandNum == 0) {
					gp.playSE(1);
					gp.gameState = gp.playState;
					gp.ui.dialogoInicial();
					gp.playMusic(0);
				}
				if (gp.ui.commandNum == 1) {
					gp.ui.titleScreenState = 2;
					gp.campoNome.setFocusable(true);
				}
			}
		}

	}

	public void playState(int code) {

		if (code == KeyEvent.VK_W) {
			upPressed = true;
		}
		if (code == KeyEvent.VK_S) {
			downPressed = true;
		}
		if (code == KeyEvent.VK_A) {
			leftPressed = true;
		}
		if (code == KeyEvent.VK_D) {
			rightPressed = true;
		}
		if (code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.pauseState;
		}
		if (code == KeyEvent.VK_ENTER) {
			enterPressed = true;
		}
		if (code == KeyEvent.VK_F) {
			shotKeyPressed = true;
		}
		if (code == KeyEvent.VK_C) {
			gp.gameState = gp.characterState;
		}
		if (code == KeyEvent.VK_L) {
			gp.ui.currentDialogue = "Oh não! Um blueberdito, cuidado\n" + "eles são bem perigosos! tenha algo\n"
					+ "para se defender antes se aproximar";
			gp.gameState = gp.dialogueState;
		}

		// DEBUG
		if (code == KeyEvent.VK_T) {
			if (showDebugText == false) {
				showDebugText = true;
			} else if (showDebugText == true) {
				showDebugText = false;
			}
		}
		// if(code == KeyEvent.VK_R) {
		// gp.tileM.loadMap("/maps/mapaInicial.txt");
		// }
	}

	public void pauseState(int code) {

		if (code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.playState;
		}
	}

	public void dialogueState(int code) {

		if (code == KeyEvent.VK_ENTER) {
			gp.gameState = gp.playState;
		}
	}

	public void characterState(int code) {

		if (code == KeyEvent.VK_C) {
			gp.gameState = gp.playState;
		}
		if (code == KeyEvent.VK_W) {
			if(gp.ui.slotRow != 0) {
				gp.ui.slotRow--;
				gp.playSE(7);
			}
		}
		if (code == KeyEvent.VK_A) {
			if(gp.ui.slotCol != 0) {
				gp.ui.slotCol--;
				gp.playSE(7);
			}
		}
		if (code == KeyEvent.VK_S) {
			if(gp.ui.slotRow != 1) {
				gp.ui.slotRow++;
				gp.playSE(7);
			}
		}
		if (code == KeyEvent.VK_D) {
			if(gp.ui.slotCol != 4) {
				gp.ui.slotCol++;
				gp.playSE(7);
			}
		}
		if (code == KeyEvent.VK_ENTER) {
			gp.player.selectItem();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

		int code = e.getKeyCode();

		if (code == KeyEvent.VK_W) {
			upPressed = false;
		}
		if (code == KeyEvent.VK_S) {
			downPressed = false;
		}
		if (code == KeyEvent.VK_A) {
			leftPressed = false;
		}
		if (code == KeyEvent.VK_D) {
			rightPressed = false;
		}
		if (code == KeyEvent.VK_F) {
			shotKeyPressed = false;
		}

	}

}
