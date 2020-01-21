package Frames.dbAccess.Components;

import java.util.HashMap;

public class ScriptParams extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	public void add(String pmNazwa, Object pmObiekt) {
		put(pmNazwa, pmObiekt);
	}
}
