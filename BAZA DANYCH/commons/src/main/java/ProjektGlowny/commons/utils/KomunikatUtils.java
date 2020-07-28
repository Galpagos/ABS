package ProjektGlowny.commons.utils;

import ProjektGlowny.commons.Frames.Komunikat;

import java.util.HashMap;
import java.util.Map;

public class KomunikatUtils {

	public static final String KOMUNIKAT = "KOMUNIKAT";
	public static final String TYTUL = "TYTUL";

	public static Map<String, String> przygotujMapeZKomunikatu(Komunikat pmKomunikat, String... pmArgs) {
		Map<String, String> lvMap = new HashMap<>();
		String lvKomunikat = pmKomunikat.getKomunikat();
		String lvTytul = pmKomunikat.getTytul();
		for (int i = 0; i < pmArgs.length; i++) {
			lvKomunikat = lvKomunikat.replaceFirst(Komunikat.ARG, pmArgs[i]);
			lvTytul = lvTytul.replaceFirst(Komunikat.ARG, pmArgs[i]);
		}
		lvMap.put(KOMUNIKAT, lvKomunikat);
		lvMap.put(TYTUL, lvTytul);
		return lvMap;
	}

	public static Komunikat przygotujKomunikat(Komunikat pmKomunikat, String... pmArgs) {
		Map<String, String> lvMap = przygotujMapeZKomunikatu(pmKomunikat, pmArgs);
		return new Komunikat() {

			@Override
			public String getTytul() {
				return lvMap.get(TYTUL);
			}

			@Override
			public String getKomunikat() {
				return lvMap.get(KOMUNIKAT);
			}
		};
	}

}
