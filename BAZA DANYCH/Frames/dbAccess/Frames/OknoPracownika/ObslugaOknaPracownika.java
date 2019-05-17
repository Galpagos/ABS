package dbAccess.Frames.OknoPracownika;

import Enums.Komunikat;
import Enums.SLRodzajeAbsencji;
import Grupy.GrupaDTO;
import Grupy.ObslugaGrup;
import dbAccess.Absencja;
import dbAccess.AbsencjaBean;
import dbAccess.dbAccess;
import dbAccess.Components.ComboPicker;
import dbAccess.Frames.Absencja.OknoAbsencji;

public class ObslugaOknaPracownika
{
	InterfejsOknaPracownika mOkno;
	OknoPracownikaRepository mRepo;

	public ObslugaOknaPracownika(InterfejsOknaPracownika pmOknoPracownika)
	{
		mOkno = pmOknoPracownika;
		mRepo = new OknoPracownikaRepository();

	}

	public void DodajAbsencje()
	{
		Absencja lvAbsencja = new Absencja();
		long lvTime = System.currentTimeMillis();
		java.sql.Date lvCurrentDate = new java.sql.Date(lvTime);
		SLRodzajeAbsencji lvRodzajAbs = SLRodzajeAbsencji.urlop_wypoczynkowy;

		lvAbsencja.setId(dbAccess.GetNextID(AbsencjaBean.NazwaTabeli));
		lvAbsencja.setIdPracownika(mOkno.getPracownika().getLvID());
		lvAbsencja.setDataOd(lvCurrentDate);
		lvAbsencja.setDataDo(lvCurrentDate);
		lvAbsencja.setRodzajAbsencji(lvRodzajAbs);

		new OknoAbsencji(lvAbsencja, mOkno.getPracownika());
		mOkno.odswiezTabele();
	}

	public void ModyfikujAbsencje()
	{
		if (mOkno.getZaznaczenieTabeli() >= 0)
		{
			new OknoAbsencji(mOkno.getAbsencjeZTabeli(), mOkno.getPracownika());
			mOkno.odswiezTabele();
		} else
		{
			Komunikat.oknoPracownikaBrakZaznaczeniaWTabeli.pokaz();
		}
	}

	public void UsunAbsencje()
	{
		if (mOkno.getZaznaczenieTabeli() >= 0)
		{
			int lvIdAbsencji = mOkno.getAbsencjeZTabeli().getId();
			mRepo.usunAbsencje(lvIdAbsencji);
			mOkno.odswiezTabele();
		} else
		{
			Komunikat.oknoPracownikaBrakZaznaczeniaWTabeli.pokaz();
		}
	}

	public String grupyPracownika()
	{
		return "Nale\u017Cy do grup: " + ObslugaGrup.getGrupyPracownikaText(mOkno.getPracownika().getLvID());
	}

	public void przypiszGrupe()
	{

		ComboPicker lvCB = new ComboPicker();
		lvCB.setObsluga(new ObslugaGrup());

		GrupaDTO lvGrupa = (GrupaDTO) lvCB.ustawGrupy();
		if (lvGrupa != null)
		{
			ObslugaGrup.ustawGrupePracownikowi(mOkno.getPracownika().getLvID(), lvGrupa);
		}
		mOkno.getLblGrupy().setText(grupyPracownika());
	}

	public void usunGrupe()
	{
		ComboPicker lvCB = new ComboPicker();
		lvCB.setObsluga(new ObslugaGrup());

		GrupaDTO lvGrupa = (GrupaDTO) lvCB.ustawGrupy();
		if (lvGrupa != null)
		{
			ObslugaGrup.usunGrupePracownikowi(mOkno.getPracownika().getLvID(), lvGrupa);
		}
		mOkno.getLblGrupy().setText(grupyPracownika());
	}
}
