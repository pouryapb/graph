package pouryapb;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Vertices extends Objects{

	public String name;
	
	public Vertices(int x, int y, String name) {
		super(x, y, ID.V);
		
		this.name = name;
	}

	public void tick() {
		
	}

	public void render(Graphics g) {
		
		g.setColor(Color.decode("#006699"));
		g.fillOval(x, y, 25, 25);
		
		g.setColor(Color.white);
		g.drawString(name, x + 9, y + 15);
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, 25, 25);
	}

	
}
