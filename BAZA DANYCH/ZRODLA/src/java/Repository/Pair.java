package Repository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Pair<T, S> {

	T a;
	S b;
	public T getKey() {
		return a;
	}

	public S getValue() {
		return b;
	}
}
