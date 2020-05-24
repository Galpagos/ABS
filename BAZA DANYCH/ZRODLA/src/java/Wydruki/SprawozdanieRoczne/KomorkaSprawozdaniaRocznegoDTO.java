package Wydruki.SprawozdanieRoczne;

import java.util.Map;
import java.util.Map.Entry;

import enums.SLRodzajeAbsencji;

public class KomorkaSprawozdaniaRocznegoDTO {
	private Map<SLRodzajeAbsencji, Long> mMapa;

	public Map<SLRodzajeAbsencji, Long> getMapa() {
		return mMapa;
	}

	public void setMapa(Map<SLRodzajeAbsencji, Long> pmMapa) {
		mMapa = pmMapa;
	}

	@Override
	public String toString() {
		StringBuilder lvS = new StringBuilder();
		lvS.append(getValue() + "<X>");
		lvS.append("<html>");
		for (Entry<SLRodzajeAbsencji, Long> entry : mMapa.entrySet()) {
			lvS.append("<p>" + entry.getKey() + " = " + entry.getValue() + "</p>");
		}
		lvS.append("</html>");
		return lvS.toString();

	}

	public int getValue() {
		int lvSuma = 0;
		for (Entry<SLRodzajeAbsencji, Long> entry : mMapa.entrySet()) {
			lvSuma += entry.getValue();
		}

		return lvSuma;
	}
}
