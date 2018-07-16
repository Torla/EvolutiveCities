import Entity.building.BanditCamp;
import Entity.building.Field;
import Entity.building.House;
import Entity.building.Keep;
import automaton.AutomatonOutputValues;
import city.Action;
import evolutiveAutomaton.EvolutiveAutomaton;
import game.Match;
import world.World;
import automaton.Automaton;
import city.City;
import graphics.Graphics;
import world.terrain.Road;
import world.terrain.TerrainBuilder;
import world.terrain.*;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;

public class Test {
	public static void main(String[] args) {
		System.out.println("start test");
		World world = new World();
		City city = new City(world,new EvolutiveAutomaton(Action.values()),0,0);

		world.reset();


		Graphics.setWorld(world);
		Graphics.start();



		Collection<City> cities = new LinkedList<>();
		cities.add(city);


		new Match(world,cities).run();

	}

}
