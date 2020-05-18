package pl.home.ListaPlac;

import java.time.YearMonth;

import ProjektGlowny.commons.utils.Interval;

interface ListaPlacRepository {

	public Integer getLiczbaDniRoboczychWMiesiacu(YearMonth pmRokMiesiac);

	public Integer ileDniRoboczych(Interval pmOkres);

}
