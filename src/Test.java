import Entity.building.Field;
import Entity.building.House;
import Entity.building.Keep;
import Game.World;
import city.City;
import graphics.Graphics;
import graphics.JGameCanvas;
import graphics.Tile;
import graphics.Window;

import javax.swing.*;
import java.util.LinkedList;
import java.util.Random;

public class Test {
	public static void main(String[] args) throws InterruptedException {
		World world=new World();
		City city=new City(world);
		new House(world,city,0,0);
		new Field(world,city,1,0);
		new Field(world,city,1,1);
		new House(world,city,0,1);
		new Keep(world,city,2,2);
		new Keep(world,city,2,3);
		Graphics.setWorld(world);
		Graphics.start();
		LinkedList<City> c = new LinkedList<>();
		c.add(city);
		Match match= new Match(c);
		Thread t = new Thread(match);
		t.start();
		t.join();
	}
}
