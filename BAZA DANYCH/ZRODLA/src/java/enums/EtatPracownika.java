package enums;

import ProjektGlowny.commons.enums.InterfejsSlownika;

import java.util.stream.Stream;

public enum EtatPracownika implements InterfejsSlownika {

	PELNY_ETAT("1", "pełny etat"),
	POLOWA("2", "1/2 etatu"),
	CWIERC("4", "1/4 etatu");

	private String mKod;
	private String mOpis;

	EtatPracownika(String pmKod, String pmOpis) {
		mKod = pmKod;
		mOpis = pmOpis;
	}

	@Override
	public String getKod() {
		return mKod;
	}

	public static EtatPracownika getByKod(String pmKod) {
		return Stream.of(values())//
				.filter(lvVal -> lvVal.getKod().equals(pmKod))//
				.findAny()//
				.orElse(EtatPracownika.PELNY_ETAT);
	}

	@Override
	public String getOpis() {
		return mOpis;
	}

	@Override
	public String toString() {
		return getOpis();
	}
}
