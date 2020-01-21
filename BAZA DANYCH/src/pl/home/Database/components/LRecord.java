package pl.home.Database.components;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

public class LRecord {
	private Map<String, Object> mMap;

	public LRecord(Map<String, Object> pmMap) {
		setMap(pmMap);
	}

	public Map<String, Object> getMap() {
		return mMap;
	}

	public void setMap(Map<String, Object> pmMap) {
		mMap = pmMap;
	}

	public Integer getAsInteger(String pmNazwa) {
		return mMap != null ? Integer.parseInt(mMap.get(pmNazwa).toString()) : 0;
	}

	public String getAsString(String pmNazwa) {
		return mMap.get(pmNazwa).toString();
	}

	public Date getAsDate(String pmNazwa) {
		return (Date) mMap.get(pmNazwa);
	}

	public Timestamp getAsTimestamp(String pmNazwa) {
		return (Timestamp) mMap.get(pmNazwa);
	}
}
