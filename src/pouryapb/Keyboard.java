package pouryapb;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Keyboard extends KeyAdapter{

	private String word = "";
	private boolean lineOn = false;
	private String line = "";
	private long startTime = System.currentTimeMillis();
	
	public void keyTyped(KeyEvent e) {
		
		if (e.getKeyChar() != KeyEvent.VK_BACK_SPACE) {
			word += e.getKeyChar();
		}
		else {
			try {
				word = word.substring(0, word.length() - 1);
			} catch (Exception e2) {}
		}
	}
	
	public String getInput() {
		return word;
	}
	
	public void render(Graphics g) {
		
		long endTime = System.currentTimeMillis();
		
		if (endTime - startTime >= 500) {
			line = (lineOn ? " |" : "");
			lineOn = !lineOn;
			startTime = System.currentTimeMillis();
		}
		
		g.setColor(Color.black);
		g.drawString("Input: ", 75, 420);
		g.drawString(word + line, 110, 418);
	}
	
	public void clear() {
		word = "";
	}
}
