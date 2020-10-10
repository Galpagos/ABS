package dbAccesspl.home.Database.Table.Zestawienie;

import java.sql.Timestamp;

import enums.SLRodzajeAbsencji;

import java.util.function.Function;

import ProjektGlowny.commons.DbBuilder.SystemTables;
import ProjektGlowny.commons.enums.InterfejsSlownika;
import pl.home.ListaPlac.SLEkwiwalentZaUrlop;

public enum AbsencjeColumns implements SystemTables {

	ID_ABS(Integer.class, "Identyfikator"), //
	ID_pracownika(Integer.class, "Identyfikator pracownika"), //
	Od_kiedy(Timestamp.class, "Data początku okresu"), //
	Do_kiedy(Timestamp.class, "Data końca okresu"), //
	EKWIWALENT(String.class, "Procent ekwiwalentu", SLEkwiwalentZaUrlop::getByKod), //
	RODZAJ(String.class, "Rodzaj absencji", SLRodzajeAbsencji::getByKod);

	private Class<?> mKlasa;
	private String mColumnName;
	private Function<String, InterfejsSlownika> mOpis;

	<T> AbsencjeColumns(Class<T> cls, String pmNazwa) {
		this(cls, pmNazwa, null);
	}

	<T> AbsencjeColumns(Class<T> cls, String pmNazwa, Function<String, InterfejsSlownika> pmOpis) {
		mColumnName = pmNazwa;
		mKlasa = cls;
		mOpis = pmOpis;
	}

	@Override
	public Function<String, InterfejsSlownika> getOpisFunkcja() {
		return mOpis;
	}

	@Override
	public Class<?> getKlasa() {
		return mKlasa;
	}

	@Override
	public String getTableName() {

		return "Absencje";
	}

	@Override
	public String getColumnName() {

		return mColumnName;
	}

}
