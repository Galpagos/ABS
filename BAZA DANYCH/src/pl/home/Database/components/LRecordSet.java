package pl.home.Database.components;

import java.sql.Timestamp;
import java.util.ArrayList;

public class LRecordSet extends ArrayList<LRecord> {

	int mIndex = 0;
	private static final long serialVersionUID = 2479458688075804811L;

	public Integer getAsInteger(String pmNazwa) {
		return this.get(mIndex).getAsInteger(pmNazwa);
	}

	public String getAsString(String pmNazwa) {
		return this.get(mIndex).getAsString(pmNazwa);
	}

	public Timestamp getAsTimestamp(String pmNazwa) {
		return this.get(0).getAsTimestamp(pmNazwa);
	}
}