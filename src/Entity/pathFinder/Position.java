package Entity.pathFinder;

public class Position{
	public int x;
	public int y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}


	@Override
	public int hashCode() {
		return x+y;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Position)) return false;
		return ((Position) obj).x==x && ((Position) obj).y==y;
	}

	@Override
	public String toString() {
		return "(" + x +"," + y + ")";
	}
}
