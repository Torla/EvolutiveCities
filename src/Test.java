import Entity.building.House;
import Game.World;
import graphics.Graphics;
import graphics.JGameCanvas;
import graphics.Tile;
import graphics.Window;

import javax.swing.*;

public class Test {
	public static void main(String[] args) {
		World world=new World();
		new House(world,0,0);
		new House(world,-5,-5);
		new House(world,0,4);
		new House(world,0,5);
		//new House(world,5,5);
		//new House(world,-5,-5);
		Graphics.setWorld(world);
		Graphics.start();
	}
}
