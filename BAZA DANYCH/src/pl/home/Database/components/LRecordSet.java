package pl.home.Database.components;

import java.sql.Timestamp;
import java.util.ArrayList;

import Frames.dbAccess.Components.SilentException;

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

	public LRecord getRecord() {
		if (size() > mIndex)
			return get(mIndex);
		return new LRecord();
	}

	public void setIndex(int pmIndex) throws SilentException {
		if (size() < mIndex)
			throw new SilentException("Indeks poza Tabela");
		mIndex = pmIndex;
	}
}