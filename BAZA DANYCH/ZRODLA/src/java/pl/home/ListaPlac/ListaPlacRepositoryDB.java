package pl.home.ListaPlac;

import java.time.YearMonth;

import Datownik.LicznikDaty;
import ProjektGlowny.commons.DbBuilder.AccessDB;
import ProjektGlowny.commons.utils.Interval;

class ListaPlacRepositoryDB extends AccessDB implements ListaPlacRepository {

	@Override
	public Integer getLiczbaDniRoboczychWMiesiacu(YearMonth pmRokMiesiac) {

		return Integer.valueOf(LicznikDaty.ileDniRobotnych(pmRokMiesiac));
	}

	@Override
	public Integer ileDniRoboczych(Interval pmOkres) {

		return LicznikDaty.ileDniRoboczych(pmOkres);
	}

}
