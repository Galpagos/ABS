package pl.home.Database.components;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import Frames.dbAccess.Components.SilentException;
import dbAccesspl.home.Database.Table.Zestawienie.SystemTables;
import dbAccesspl.home.Database.Table.Zestawienie.ZestawienieColumns;

public class LRecordSet extends ArrayList<LRecord> {

	int mIndex = 0;
	private static final long serialVersionUID = 2479458688075804811L;

	public Integer getAsInteger(SystemTables pmNazwa) {
		return this.get(mIndex).getAsInteger(pmNazwa);
	}

	public String getAsString(SystemTables pmNazwa) {
		return this.get(mIndex).getAsString(pmNazwa);
	}

	public Timestamp getAsTimestamp(SystemTables pmNazwa) {
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

	public LocalDate getAsLocalDate(SystemTables pmDataUrodzenia) {

		return this.get(mIndex).getAsLocalDate(pmDataUrodzenia);
	}

	public Date getAsDate(ZestawienieColumns pmDataUrodzenia) {

		return this.get(mIndex).getAsDate(pmDataUrodzenia);
	}

	public Integer getAsInteger(ZestawienieColumns pmUrlopNalezny) {

		return get(mIndex).getAsInteger(pmUrlopNalezny);
	}
}