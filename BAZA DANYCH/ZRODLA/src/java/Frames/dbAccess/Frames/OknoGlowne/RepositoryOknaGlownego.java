package Frames.dbAccess.Frames.OknoGlowne;

import ProjektGlowny.commons.DbBuilder.QueryBuilder;
import dbAccesspl.home.Database.Table.Zestawienie.DniWolneColumns;
import pl.home.DniWolne.DzienWolnyDTO;

class RepositoryOknaGlownego {

	void zapiszNowyDzienWolny(DzienWolnyDTO pmDzien) {
		QueryBuilder.INSERT()//
				.set(DniWolneColumns.ID_DN_WN, QueryBuilder.getNextId(DniWolneColumns.ID_DN_WN))//
				.set(DniWolneColumns.Data, pmDzien.getData())//
				.execute();

	}

}
