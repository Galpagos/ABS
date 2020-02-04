package pl.home.Database.components;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LRecord extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;
	private Map<Integer, String> mIndeksy;

	public LRecord() {
		mIndeksy = new HashMap<Integer, String>();
	}

	@Override
	public Object put(String pmString, Object pmObject) {
		super.put(pmString, pmObject);
		mIndeksy.put(mIndeksy.size() + 1, pmString);
		return pmObject;
	}

	public Integer getAsInteger(String pmNazwa) {
		return Integer.parseInt(get(pmNazwa).toString());
	}

	public String getAsString(String pmNazwa) {
		return get(pmNazwa).toString();
	}

	public Date getAsDate(String pmNazwa) {
		return (Date) get(pmNazwa);
	}

	public Timestamp getAsTimestamp(String pmNazwa) {
		return (Timestamp) get(pmNazwa);
	}

	public Object getValue(int pmIndex) {
		return get(mIndeksy.get(pmIndex));
	}
}
