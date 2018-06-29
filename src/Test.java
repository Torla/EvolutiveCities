import Entity.building.Field;
import Entity.building.House;
import Entity.building.Keep;
import Game.World;
import city.City;
import graphics.Graphics;
import match.Match;

import java.util.LinkedList;

public class Test {
	public static void main(String[] args) throws InterruptedException {
		World world=new World();
		City city=new City(world,10,0);
		new House(world,city,0,0);
		new Field(world,city,1,0);
		new Field(world,city,1,1);
		new House(world,city,0,1);
		new Keep(world,city,2,2);
		new Keep(world,city,2,3);
		LinkedList<City> c = new LinkedList<>();
		c.add(city);
		Match match= new Match(c);

		Graphics.setWorld(world);
		Graphics.setMatch(match);
		Graphics.start();



		Thread t = new Thread(match);
		t.start();
		t.join();
	}
}
