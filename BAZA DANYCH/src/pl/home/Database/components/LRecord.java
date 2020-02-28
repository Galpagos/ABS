package pl.home.Database.components;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import Datownik.Data;
import dbAccesspl.home.Database.Table.Zestawienie.SystemTables;

public class LRecord extends HashMap<SystemTables, Object> {

	private static final long serialVersionUID = 1L;
	private Map<Integer, SystemTables> mIndeksy;

	public LRecord() {
		mIndeksy = new HashMap<Integer, SystemTables>();
	}

	@Override
	public Object put(SystemTables pmPole, Object pmObject) {
		super.put(pmPole, pmObject);
		mIndeksy.put(mIndeksy.size(), pmPole);
		return pmObject;
	}

	public Integer getAsInteger(SystemTables pmNazwa) {
		return get(pmNazwa) == null ? null : Integer.parseInt(get(pmNazwa).toString());
	}

	public String getAsString(SystemTables pmNazwa) {
		if (get(pmNazwa) == null)
			return "";

		return get(pmNazwa).toString();
	}

	public Date getAsDate(SystemTables pmNazwa) {
		if (get(pmNazwa) == null)
			return null;
		return (Date) get(pmNazwa);
	}

	public Timestamp getAsTimestamp(SystemTables pmNazwa) {
		if (get(pmNazwa) == null)
			return null;
		return (Timestamp) get(pmNazwa);
	}

	public Object getValue(int pmIndex) {
		return get(mIndeksy.get(pmIndex));
	}

	public LocalDate getAsLocalDate(SystemTables pmDataUrodzenia) {

		return Data.LocalDateFromDate(getAsDate(pmDataUrodzenia));
	}

	public String getColumnName(int pmCol) {
		if (pmCol > mIndeksy.size())
			return "  ";
		return mIndeksy.get(pmCol).getColumnName();
	}

	public SystemTables getColumn(int pmCol) {
		return mIndeksy.get(pmCol);
	}
}
