package genetic;

import java.io.Serializable;
import java.util.*;

public class Pool<E extends Evolutive> implements Serializable{

	private Set<E> evolutives = new HashSet<>();

	public void add(E e){
		evolutives.add(e);
	}

	public Collection<E> getPop(int n){
		List<E> list = new LinkedList<E>(evolutives);
		Collections.shuffle(list);
		return list.subList(0,n);
	}

	public void generation(List<E> rank,int survaivorNum,int sonPerSurvaivor){
		List<E> winners = rank.subList(0,survaivorNum);
		List<E> losers = rank.subList(survaivorNum,rank.size());
		evolutives.removeAll(losers);
		for (E e:winners) {
			for(int i=0;i<sonPerSurvaivor;i++) {
				evolutives.add((E) e.copyMutated());
			}
		}
	}
}
