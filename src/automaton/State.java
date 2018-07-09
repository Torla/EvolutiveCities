package automaton;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class State implements Serializable{
	private int ID;
	private Map<Integer,Edge> edgeMap = new HashMap<>();

	private final Edge defaultEdge = new Edge(this, StackAction.PUSH, 0, new AutomatonOutputValues() {
		@Override
		public AutomatonOutputValues getValue(int n) {
			return this;
		}

		@Override
		public int valuesNumber() {
			return 1;
		}
	});

	public void setID(int ID) {
		this.ID = ID;
	}

	public int getID() {
		return ID;
	}

	public Map<Integer, Edge> getEdgeMap() {
		return edgeMap;
	}

	public void addEdge(Integer stackTop, Edge edge){
		edgeMap.put(stackTop,edge);
	}

	public Edge getEdge(int stackTop){
		return edgeMap.get(stackTop);
	}

	@Override
	public String toString() {
		return ID + "->" +edgeMap.toString() + "\n";
	}
}
