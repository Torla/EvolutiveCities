package graphics;

import java.util.Random;

public class Graphics implements Runnable {

	private static Graphics graphics = new Graphics();
	private static Window window = new Window();

	private static Thread thread;

	private Graphics(){};

	public static void start(){
		thread= new Thread(graphics);
		thread.start();
	}

	@Override
	public void  run() {
		while(true){
			long time=System.currentTimeMillis();
			// TODO: 26/06/2018 map to screen
			window.setTile(Tile.values()[new Random().nextInt(20)],new Random().nextInt(20),new Random().nextInt(20));
			window.repaint();
			try {
				Thread.sleep(1000/Options.frameRate - (System.currentTimeMillis()-time));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
