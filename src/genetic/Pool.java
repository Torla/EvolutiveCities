package genetic;

import java.io.Serializable;
import java.util.*;

public class Pool<E extends Evolutive> implements Serializable{

	private Set<E> evolutives = new HashSet<>();
	private Set<E> beingUsed = new HashSet<>();

	public void add(E e){
		evolutives.add(e);
	}

	public Collection<E> getPop(int n){
		Collection<E> ret;
		List<E> list = new LinkedList<E>(evolutives);
		list.removeAll(beingUsed);
		Collections.shuffle(list);
		ret = list.subList(0,n);
		beingUsed.addAll(ret);
		return ret;
	}

	public void generation(List<E> rank,int survaivorNum,int sonPerSurvaivor){
		beingUsed.removeAll(rank);
		List<E> winners = rank.subList(0,survaivorNum);
		List<E> losers = rank.subList(survaivorNum,rank.size());
		evolutives.removeAll(losers);
		for (E e:winners) {
			for(int i=0;i<sonPerSurvaivor;i++) {
				evolutives.add((E) e.copyMutated());
			}
		}
	}

	public  int getPopSize(){
		return evolutives.size();
	}
	public Set<E> getPop(){
		return evolutives;
	}

	public void resetUsed(){
		beingUsed.clear();
	}

}
