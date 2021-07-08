package pouryapb;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import pouryapb.G.Edge;

public class GraphDesigner extends MouseAdapter {
	
	private Handler handler;
	private boolean edge = false;
	private boolean firstClick = false;
	private int mouseX1, mouseY1, mouseX2, mouseY2;
	private boolean mouseOver = false;
	private G graph;
	private Keyboard keyboard;
	private String V1 = "";
	
	public GraphDesigner(Handler handler, G graph, Keyboard keyboard) {
		this.handler = handler;
		this.graph = graph;
		this.keyboard = keyboard;
	}
	
	public void mouseClicked(MouseEvent e) {
		
		int x = e.getX();
		int y = e.getY();
		
		if ((x > 20 && x < 590) && (y > 20 && y < 390)) {
			
			if (!edge) {
				handler.addObject(new Vertices(x - 25 / 2, y - 25 / 2, keyboard.getInput()));
				graph.addVertex(keyboard.getInput());
				keyboard.clear();
			}
			if (edge) {
				
				// cancel connecting
				if (firstClick) {
					boolean f = false;
					for (int i = 0; i < handler.objects.size(); i++) {
						Objects obj = handler.objects.get(i);
						
						if (obj.getId() != ID.V || !obj.getBounds().intersects(new Rectangle(x, y, 1, 1))) {
							f = true;
						}
						else {
							f = false;
							break;
						}
					}
					if (f) {
						firstClick = false;
						return;
					}
				}
				
				// drawing edges
				for (int i = 0; i < handler.objects.size(); i++) {
					Objects obj = handler.objects.get(i);
					
					if (obj.getId() == ID.V && obj.getBounds().intersects(new Rectangle(x, y, 1, 1))) {

						if (!firstClick) {
							firstClick = true;
							mouseX1 = obj.getX() + 25 / 2;
							mouseY1 = obj.getY() + 25 / 2;
							V1 = ((Vertices)obj).name;
						}
						else {
							firstClick = false;
							EdgeObject ed = new EdgeObject(mouseX1, mouseY1, obj.getX() + 25 / 2, obj.getY() + 25 / 2, keyboard.getInput());
							handler.addObject(ed);
							graph.connnect(V1, ((Vertices)obj).name, Integer.parseInt(keyboard.getInput()), ed);
							keyboard.clear();
						}
					}
				}
			}
		}
		
		// reset button
		if (x > 535 && x < 595 && y > 410 && y < 440) {
			handler.clear();
			graph.clear();
		}
		
		// prim's button
		if (x > 15 && y > 440 && x < 75 && y < 470) {
			LinkedList<Edge> pickedEdges = graph.prim();
			
			for (int i = 0; i < handler.objects.size(); i++) {	 
				 Objects obj = handler.objects.get(i);
				 
				 if (obj.getId() == ID.E) {
					 
					 boolean remove = true;
					 
					 for (int j = 0; j < pickedEdges.size(); j++) {
						 if (obj == pickedEdges.get(j).getEdgeObject()) {
							 remove = false;
							 break;
						 }
					 }
					 
					 if (remove) {
						 handler.removeObject(obj);
						 i--;
					 }
				 }
			 }
		}
		
		// boruvka's button
		if (x > 15 && y > 490 && x < 75 && y < 520) {
			LinkedList<Edge> pickedEdges = graph.boruvka();

			for (int i = 0; i < handler.objects.size(); i++) {	 
				Objects obj = handler.objects.get(i);

				if (obj.getId() == ID.E) {

					boolean remove = true;

					for (int j = 0; j < pickedEdges.size(); j++) {
						if (obj == pickedEdges.get(j).getEdgeObject()) {
							remove = false;
							break;
						}
					}

					if (remove) {
						handler.removeObject(obj);
						i--;
					}
				}
			}
		}
	}
	
	public void mouseMoved(MouseEvent e) {
		
		mouseX2 = e.getX();
		mouseY2 = e.getY();
		
		if (e.getX() > 535 && e.getX() < 595 && e.getY() > 410 && e.getY() < 440) {
			mouseOver = true;
		}
		else {
			mouseOver = false;
		}
	}
	
	public void render(Graphics g) {
		
		// drawING an edge
		if (firstClick) {
			g.setColor(Color.decode("#006699"));
			((Graphics2D)g).setStroke(new BasicStroke(5));
			g.drawLine(mouseX1, mouseY1, mouseX2, mouseY2);
		}
		
		// reset button
		g.setColor(Color.decode("#aaaaaa"));
		g.fillRect(535, 410, 60, 30);
		g.setColor(Color.black);
		g.drawString("Reset", 548, 430);
		
		// reset button feedback
		if (mouseOver) {
			g.setColor(Color.decode("#707070"));
			g.drawRect(535, 410, 60, 30);
		}
		
		// prim's button
		g.setColor(Color.decode("#0006699"));
		g.fillRect(15, 440, 60, 30);
		g.setColor(Color.white);
		g.drawString("Prim", 32, 460);
		
		// boruvka's button
		g.setColor(Color.decode("#0006699"));
		g.fillRect(15, 490, 60, 30);
		g.setColor(Color.white);
		g.drawString("Boruvka", 22, 510);
	}

	public void setEdge(boolean b) {
		edge = b;
	}
}
