import automaton.Automaton;
import automaton.Edge;
import automaton.StackAction;
import automaton.State;
import city.Action;

public class Test {
	public static void main(String[] args) {
		Automaton automaton = new Automaton();
		State state = new State();
		State state1 = new State();
		state.addEdge(0,new Edge(state, StackAction.PUSH,1, Action.CURSOR_NORD));
		state.addEdge(1,new Edge(state1, StackAction.NULL,0, Action.CURSOR_NORD));
		state1.addEdge(1,new Edge(state1,StackAction.POP,0,Action.BUILD_HOUSE));
		state1.addEdge(0,new Edge(state,StackAction.PUSH,0,Action.CURSOR_SUD));
		automaton.setInitialState(state);
		automaton.addState(state1);
		automaton.reset();
		automaton.stackPush(0);
		System.out.println(automaton);
		while(true){
			System.out.println(automaton.next());
		}
	}

	/*
	public static void main(String[] args) throws InterruptedException {
		World world=new World();
		City city=new City(world,0,0);
		City city1=new City(world,10,10);
		new House(world,city,0,0);
		new Field(world,city,1,0);
		new Field(world,city,1,1);
		new House(world,city,0,1);
		new Field(world,city,1,2);
		new House(world,city,2,2);
		new Field(world,city,3,3);
		new House(world,city,3,2);

		LinkedList<City> c = new LinkedList<>();
		c.add(city);
		c.add(city1);
		Match match= new Match(c);

		Graphics.setWorld(world);
		Graphics.setMatch(match);
		Graphics.start();



		Thread t = new Thread(match);
		t.start();
		t.join();
		System.out.println(match.rank());
	}
	*/
}
