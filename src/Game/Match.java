package Game;


import Entity.building.Building;
import Entity.unity.Unit;
import city.City;
import graphics.Showable;

import java.util.*;
import java.util.stream.Collectors;

public class Match implements Runnable{


	public void setCities(Set<City> cities) {
		this.cities = cities;
	}


	private static  int sleep = 0;


	public static int getSleep() {

		return sleep;
	}

	public static void setSleep(int sleep) {

		Match.sleep = sleep>=0?sleep:0;
	}

	private static final int maxTurn = 500;

	private Set<City> cities=null;

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
		return cities.stream()
				.sorted(Comparator.comparingInt(City::getPopulation)
						.thenComparingInt(City::getFood)
						.thenComparingLong(x->x.getWorld().getEntities().stream().filter(y->y.getOwner()==x).count())
						.reversed())
				.collect(Collectors.toList());
	}

	public Set<Showable> getShowable(){
		return cities.stream().flatMap(x->x.getShowable().stream()).collect(Collectors.toSet());
	}
}
