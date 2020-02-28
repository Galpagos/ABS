package pl.home.ListaPlac;

import java.time.YearMonth;

import Datownik.Interval;
import Datownik.LicznikDaty;
import pl.home.Database.components.AccessDB;

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
