package Game;

import Game.World;
import city.City;
import graphics.Showable;
import graphics.Tile;

import java.text.CharacterIterator;
import java.util.*;
import java.util.stream.Collectors;

public class Match implements Runnable{

	private static final int sleep = 100;

	private static final int maxTurn = 300;
	Set<City> cities=null;

	public Match(Collection<City> cities) {
		this.cities = new HashSet<>(cities);
	}

	@Override
	public void run() {
		for(int turn=0;turn<maxTurn;turn++){
			cities.forEach(City::turn);
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}



	public Collection<City> getCities() {
		return cities;
	}

	public List<City> rank(){
		return cities.stream().sorted((y,x)->Integer.compare(x.getPopulation(),y.getPopulation())).collect(Collectors.toList());
	}

	public Set<Showable> getShowable(){
		return cities.stream().flatMap(x->x.getShowable().stream()).collect(Collectors.toSet());
	}
}
