package ProjektGlowny.commons.DbBuilder;

import ProjektGlowny.commons.Components.SilentException;

import java.util.ArrayList;
import java.util.Date;

import java.sql.Timestamp;
import java.time.LocalDate;

public class LRecordSet extends ArrayList<LRecord> {

	int mIndex = 0;
	private static final long serialVersionUID = 2479458688075804811L;

	public Integer getAsInteger(SystemTables pmNazwa) {
		if (mIndex >= size())
			return null;
		return this.get(mIndex).getAsInteger(pmNazwa);
	}

	public String getAsString(SystemTables pmNazwa) {
		if (mIndex >= size())
			return null;
		return this.get(mIndex).getAsString(pmNazwa);
	}

	public Timestamp getAsTimestamp(SystemTables pmNazwa) {
		if (mIndex >= size())
			return null;
		return this.get(mIndex).getAsTimestamp(pmNazwa);
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

	public LocalDate getAsLocalDate(SystemTables pmData) {
		if (mIndex >= size())
			return null;
		return this.get(mIndex).getAsLocalDate(pmData);
	}

	public Date getAsDate(SystemTables pmData) {
		if (mIndex >= size())
			return null;
		return this.get(mIndex).getAsDate(pmData);
	}
}