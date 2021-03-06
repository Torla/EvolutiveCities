package game;


import Entity.Entity;
import city.City;
import graphics.Showable;
import world.World;

import java.util.*;
import java.util.stream.Collectors;

public class Match implements Runnable{


	private static final int maxTurn = 10000;

	private World world;

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



	private Set<City> cities=null;

	public Match(World world,Collection<City> cities) {
		this.world=world;
		this.cities = new HashSet<>(cities);
	}

	@Override
	public void run() {
		Collection<City> aliveCities = new ArrayList<>(cities);
		for(int turn=0;turn<maxTurn;turn++){
			new LinkedList<Entity>(world.getEntities()).forEach(Entity::turn);
			aliveCities.forEach(City::turn);
			if(turn>maxTurn/100+1){
				aliveCities = aliveCities.stream()
						.filter(x ->x.getFood()+x.getPopulation()>0 && x.getWorld().getEntities().stream().anyMatch(z -> z.getOwner() == x))
						.collect(Collectors.toList());
			}
			if(aliveCities.size()<=1) break;
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
