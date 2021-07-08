package pouryapb;

import java.awt.Graphics;
import java.util.LinkedList;

public class Handler {
	
	public LinkedList<Objects> objects = new LinkedList<Objects>();
	
	public void addObject(Objects object) {
		objects.add(object);
	}
	
	public void removeObject(Objects object) {
		objects.remove(object);
	}
	
	public void clear() {
		objects.clear();
	}
	
	public void tick() {
		
		for (Objects obj : objects) {
			obj.tick();
		}
	}
	
	public void render(Graphics g) {

		try {

			for (int i = 0; i < objects.size(); i++) {
				objects.get(i).render(g);
			}
		} catch (Exception e) {}
	}

}
