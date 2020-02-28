package Frames.dbAccess.Frames.OknoGlowne;

import dbAccesspl.home.Database.Table.Zestawienie.DniWolneColumns;
import dbAccesspl.home.Database.Table.Zestawienie.QueryBuilder;
import pl.home.DniWolne.DzienWolnyDTO;

class RepositoryOknaGlownego {

	void zapiszNowyDzienWolny(DzienWolnyDTO pmDzien) {
		QueryBuilder.INSERT()//
				.set(DniWolneColumns.ID_tabeli, QueryBuilder.getNextId(DniWolneColumns.ID_tabeli))//
				.set(DniWolneColumns.Data, pmDzien.getData())//
				.execute();

	}

}
