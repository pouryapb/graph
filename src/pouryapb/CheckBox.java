package pouryapb;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CheckBox extends Objects implements MouseListener{

	private GraphDesigner gd;
	private boolean toggle = false;
	
	public CheckBox(int x, int y, GraphDesigner gd) {
		super(x, y, null);
		
		this.gd = gd;
	}

	public void tick() {
		
	}

	public void render(Graphics g) {
		
		g.setColor(Color.decode("#aaaaaa"));
		g.fillRect(x, y, 30, 15);
		
		g.setColor(Color.decode("#006699"));
		if (!toggle) {
			g.fillRect(x, y, 15, 15);
			g.setColor(Color.white);
			g.drawString("V", x + 3, y + 12);
			g.setColor(Color.black);
			g.drawString("E", x + 18, y + 12);
		}
		if (toggle) {
			g.fillRect(x + 15, y, 15, 15);
			g.setColor(Color.white);
			g.drawString("E", x + 18, y + 12);
			g.setColor(Color.black);
			g.drawString("V", x + 3, y + 12);
		}
	}

	public Rectangle getBounds() {
		return null;
	}

	public void mouseClicked(MouseEvent e) {	
		int x = e.getX();
		int y = e.getY();
		
		if ((x > this.x && x < this.x + 30) && (y > this.y && y < this.y + 15)) {
			toggle = !toggle;
			gd.setEdge(toggle);
		}
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
		
	}

	public void mouseReleased(MouseEvent e) {
		
	}

}
