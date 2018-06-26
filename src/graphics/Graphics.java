package graphics;

import Game.World;
import javafx.stage.Screen;

import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Graphics implements Runnable {

	private static Graphics graphics = new Graphics();
	private static Window window = new Window();
	private static World world;

	private static int screenPosX=0;
	private static int screenPosY=0;

	private static Thread thread;

	private Graphics(){};

	public static void setWorld(World world){
		Graphics.world = world;
	}

	public static void moveScreen(int x,int y){
		screenPosX+=x;
		screenPosY+=y;
	}

	public static void start(){
		thread= new Thread(graphics);
		thread.start();
	}

	@Override
	public void  run() {
		while(true){
			long time=System.currentTimeMillis();
			for (Showable x:world.getShowable().stream()
					.filter(x->x.getPositionX()>=screenPosX && x.getPositionX()<screenPosX+Options.screenTilesW && x.getPositionY()>=screenPosY && x.getPositionY()<screenPosY+Options.screenTilesH)
					.collect(Collectors.toList())
			     ) {
				window.setTile(x.getTile(),x.getPositionX()-screenPosX,x.getPositionY()-screenPosY);
				System.out.println(screenPosX);
			}
			window.repaint();
			try {
				Thread.sleep(1000/Options.frameRate - (System.currentTimeMillis()-time));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
