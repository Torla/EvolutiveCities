import Game.World;
import city.City;

import java.text.CharacterIterator;
import java.util.*;
import java.util.stream.Collectors;

public class Match implements Runnable{

	private static final int maxTurn = 100000;
	Set<City> cities=null;

	public Match(Collection<City> cities) {
		this.cities = new HashSet<>(cities);
	}

	@Override
	public void run() {
		for(int turn=0;turn<maxTurn;turn++){
			cities.forEach(City::turn);
			System.out.println(cities);
		}
	}



	public Collection<City> getCities() {
		return cities;
	}

	List<City> rank(){
		return cities.stream().sorted((x,y)->Integer.compare(x.getPopulation(),y.getPopulation())).collect(Collectors.toList());
	}
}
