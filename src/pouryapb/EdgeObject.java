package pouryapb;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class EdgeObject extends Objects {
	
	private int eX, eY;
	String weight;
	private String color = "#006699";

	public EdgeObject(int x, int y, int eX, int eY, String weight) {
		super(x, y, ID.E);
		
		this.eX = eX;
		this.eY = eY;
		this.weight = weight;
	}

	public void tick() {
		
	}
	
	public void disable() {
		color = "#ff0000";
	}
	
	public void render(Graphics g) {
		
		double theta = Math.atan2(eY - y, eX - x);
		double length = Math.sqrt(Math.pow((x - eX), 2) + Math.pow((y - eY), 2));
		
		g.setColor(Color.decode(color));
		((Graphics2D)g).setStroke(new BasicStroke(5));
		
		g.drawLine((int)(10 * Math.cos(theta) + x), (int)(10 * Math.sin(theta) + y), (int)((length / 2 - 12) * Math.cos(theta) + x), (int)((length / 2 - 12) * Math.sin(theta) + y));
		g.drawLine((int)((length / 2 + 12) * Math.cos(theta) + x), (int)((length / 2 + 12) * Math.sin(theta) + y), (int)(-10 * Math.cos(theta) + eX), (int)(-10 * Math.sin(theta) + eY));
		
		g.setColor(Color.black);
		g.drawString(weight, x + (eX - x) / 2 - 5, y + (eY - y) / 2 + 5);
	}
	
	public Rectangle getBounds() {
		return null;
	}

}
