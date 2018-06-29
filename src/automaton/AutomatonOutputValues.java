package automaton;

import java.io.Serializable;

public interface AutomatonOutputValues extends Serializable {
	AutomatonOutputValues getValue(int n);

	int valuesNumber();
}