package pouryapb;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;

public class Graph extends Canvas implements Runnable {

	private static final long serialVersionUID = 1821870185956677299L;

	private boolean running = false;
	private Thread thread;
	private Handler handler;
	private GraphDesigner gd;
	private CheckBox cb;
	private G graph = new G();
	private Keyboard keyboard= new Keyboard();

	public Graph() {

		new Window(600, 600, "Graph", this);
		
		handler = new Handler();
		gd = new GraphDesigner(handler, graph, keyboard);
		cb = new CheckBox(15, 410, gd);
		
		this.addMouseListener(gd);
		this.addMouseMotionListener(gd);
		this.addMouseListener(cb);
		this.addKeyListener(keyboard);

	}

	public synchronized void start() {
		if (running)
			return;

		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public void run() {

		long lastTime = System.nanoTime();
		double amountOfTicks = 0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int updates = 0;
		int frames = 0;
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				tick();
				updates++;
				delta--;
			}
			render();
			frames++;

			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				System.out.println("FPS: " + frames + " TICKS: " + updates);
				frames = 0;
				updates = 0;
			}
		}
	}

	private void tick() {

		handler.tick();
	}

	private void render() {

		BufferStrategy bs = this.getBufferStrategy();

		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		//////////////////////////////////

		g.setColor(Color.decode("#eeeeee"));
		g.fillRect(0, 0, getWidth(), getHeight());
		
		g.setColor(Color.decode("#858585"));
		g.drawRect(10, 10, 590, 390);

		gd.render(g);
		keyboard.render(g);
		cb.render(g);
		handler.render(g);

		//////////////////////////////////
		g.dispose();
		bs.show();
	}
	
	public static void main(String[] args) {
		
		new Graph();
	}

}
