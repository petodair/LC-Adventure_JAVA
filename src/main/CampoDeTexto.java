package main;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

public class CampoDeTexto extends JTextField{
	
	private static final long serialVersionUID = 1L;
	GamePanel gp;
	
	public CampoDeTexto(GamePanel gp){
		this.gp = gp;
		
		this.setBounds(300,gp.tileSize*4, 200, 80);
		this.setFont(gp.ui.maruMonica);
		this.setFont(this.getFont().deriveFont(52F));
		this.setBackground(Color.black);
		this.setForeground(Color.white);
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setDocument(new JTextFieldLimit(10));
		
	}

}
