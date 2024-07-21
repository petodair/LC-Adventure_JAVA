package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

	GamePanel gp;

	public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
	// DEGUB
	boolean checkDrawTime = false;

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

			if (gp.ui.titleScreenState == 0) {
				if (code == KeyEvent.VK_W) {
					gp.ui.commandNum--;
					if (gp.ui.commandNum < 0) {
						gp.ui.commandNum = 2;
					}
				}
				if (code == KeyEvent.VK_S) {
					gp.ui.commandNum++;
					if (gp.ui.commandNum > 2) {
						gp.ui.commandNum = 0;
					}
				}
				if (code == KeyEvent.VK_ENTER) {
					if (gp.ui.commandNum == 0) {
						gp.ui.titleScreenState = 1;
						gp.stopMusic();
					}
					if (gp.ui.commandNum == 1) {
						// LATER
					}
					if (gp.ui.commandNum == 2) {
						System.exit(0);
					}
				}
			} else if (gp.ui.titleScreenState == 1) {
				if (code == KeyEvent.VK_W) {
					gp.ui.commandNum--;
					if (gp.ui.commandNum < 0) {
						gp.ui.commandNum = 1;
					}
				}
				if (code == KeyEvent.VK_S) {
					gp.ui.commandNum++;
					if (gp.ui.commandNum > 1) {
						gp.ui.commandNum = 0;
					}
				}
				if (code == KeyEvent.VK_ENTER) {
					gp.ui.titleScreenState = 2;
					gp.campoNome.setFocusable(true);
					
					gp.ui.commandNum = 0;
					gp.stopMusic();
				}
			} 
			else if (gp.ui.titleScreenState == 2) {

			}

			else if (gp.ui.titleScreenState == 3) {
				
				if (code == KeyEvent.VK_A) {
					gp.ui.commandNum ++;
					if (gp.ui.commandNum > 1) {
						gp.ui.commandNum = 0;
					}
				}
				
				if (code == KeyEvent.VK_D) {
					gp.ui.commandNum ++;
					if (gp.ui.commandNum > 1) {
						gp.ui.commandNum = 0;
					}
				}
				
				if (code == KeyEvent.VK_ESCAPE) {
					gp.ui.titleScreenState = 0;
				}

				if (code == KeyEvent.VK_ENTER) {
					if (gp.ui.commandNum == 0) {
						gp.gameState = gp.playState;
						gp.ui.dialogoInicial();
					}
					if (gp.ui.commandNum == 1) {
						gp.ui.titleScreenState = 2;
						gp.campoNome.setFocusable(true);
					}
				}
			}
		}

		// PLAY STATE
		else if (gp.gameState == gp.playState) {

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
			if (code == KeyEvent.VK_C) {
				gp.gameState = gp.characterState;
			}
			if (code == KeyEvent.VK_L) {
				gp.ui.currentDialogue ="Oh não! Um blueberdito, cuidado\n"
						+ "eles são bem perigosos! tenha algo\n"
						+ "para se defender antes se aproximar";
				gp.gameState = gp.dialogueState;
			}

			// DEBUG
			if (code == KeyEvent.VK_T) {
				if (checkDrawTime == false) {
					checkDrawTime = true;
				} else if (checkDrawTime == true) {
					checkDrawTime = false;
				}
			}
		}

		// PAUSE STATE
		else if (gp.gameState == gp.pauseState) {
			if (code == KeyEvent.VK_ESCAPE) {
				gp.gameState = gp.playState;
			}
		}

		// DIALOGUE STATE
		else if (gp.gameState == gp.dialogueState) {
			if (code == KeyEvent.VK_ENTER) {
				gp.gameState = gp.playState;
			}
		}
		else if(gp.gameState == gp.characterState) {
			if (code == KeyEvent.VK_C) {
				gp.gameState = gp.playState;
			}
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

	}

}
