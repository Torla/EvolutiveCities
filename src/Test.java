import Entity.building.Field;
import Entity.building.House;
import Entity.building.Keep;
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
		new Keep(world,1,5);
		new Field(world,0,1);
		new Field(world,1,1);
		new Field(world,1,0);
		new Field(world,1,0);
		Graphics.setWorld(world);
		Graphics.start();
	}
}
