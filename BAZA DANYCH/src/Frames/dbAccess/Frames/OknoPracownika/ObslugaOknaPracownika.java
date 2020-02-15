package Frames.dbAccess.Frames.OknoPracownika;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

import Absencja.ObslugaAbsencji;
import Datownik.Interval;
import Enums.Komunikat;
import Enums.SLRodzajeAbsencji;
import Frames.dbAccess.Components.ComboPicker;
import Frames.dbAccess.Components.ScriptParams;
import Frames.dbAccess.Frames.Absencja.OknoAbsencji;
import Frames.dbAccess.Frames.Absencja.OknoAbsencjiParams;
import Grupy.GrupaDTO;
import Grupy.ObslugaGrup;
import Pracownik.ObslugaPracownka;
import Pracownik.PracownikRepository;
import Wydruki.PrzygotowanieDanych.AbsencjaDTO;
import Wydruki.PrzygotowanieDanych.PracownikDTO;
import dbAccess.AbsencjaBean;
import dbAccess.dbAccess;

public class ObslugaOknaPracownika {
	InterfejsOknaPracownika mOkno;

	PracownikRepository mRepoPracownika = new PracownikRepository();
	ObslugaPracownka mObslugaPracownika = new ObslugaPracownka();
	ObslugaAbsencji mObslugaAbsencji = new ObslugaAbsencji();

	public ObslugaOknaPracownika(InterfejsOknaPracownika pmOknoPracownika) {
		mOkno = pmOknoPracownika;
	}

	public void DodajAbsencje() {
		AbsencjaDTO lvAbsencja = new AbsencjaDTO();
		SLRodzajeAbsencji lvRodzajAbs = SLRodzajeAbsencji.urlop_wypoczynkowy;

		lvAbsencja.setId(dbAccess.GetNextID(AbsencjaBean.NazwaTabeli));
		lvAbsencja.setIdPracownika(mOkno.getPracownika().getId());
		lvAbsencja.setOkres(new Interval(new Date(), new Date()));
		lvAbsencja.setRodzaj(lvRodzajAbs);

		mObslugaAbsencji.modyfikujAbsencje(lvAbsencja);
	}

	public void ModyfikujAbsencje() {
		if (mOkno.getZaznaczenieTabeli() < 0) {
			Komunikat.oknoPracownikaBrakZaznaczeniaWTabeli.pokaz();
		} else {
			ScriptParams lvParams = new ScriptParams();
			lvParams.add(OknoAbsencjiParams.ABSENCJA_DTO, mOkno.getAbsencjeZTabeli());
			new OknoAbsencji(lvParams);
		}
	}

	public void UsunAbsencje() {
		if (mOkno.getZaznaczenieTabeli() < 0) {
			Komunikat.oknoPracownikaBrakZaznaczeniaWTabeli.pokaz();
			return;
		}
		if (!Komunikat.PotwierdzenieOperacjiUsuniecia())
			return;

		int lvIdAbsencji = mOkno.getAbsencjeZTabeli().getId();
		mObslugaAbsencji.usunAbsencje(lvIdAbsencji, true);

	}

	public String grupyPracownika() {
		return "Nale\u017Cy do grup: " + ObslugaGrup.getGrupyPracownikaText(mOkno.getPracownika().getId());
	}

	public void przypiszGrupe() {

		ComboPicker lvCB = new ComboPicker();
		lvCB.setObsluga(new ObslugaGrup());

		GrupaDTO lvGrupa = (GrupaDTO) lvCB.ustawGrupy();
		if (lvGrupa != null) {
			ObslugaGrup.ustawGrupePracownikowi(mOkno.getPracownika().getId(), lvGrupa);
		}

	}

	public void usunGrupe() {
		ComboPicker lvCB = new ComboPicker();
		lvCB.setObsluga(new ObslugaGrup());

		GrupaDTO lvGrupa = (GrupaDTO) lvCB.ustawGrupy();
		if (lvGrupa != null) {
			ObslugaGrup.usunGrupePracownikowi(mOkno.getPracownika().getId(), lvGrupa);
		}
	}

	public void ustawDateUrodzenia() {
		new JOptionPane();
		String lvUrlop = JOptionPane.showInputDialog("Podaj datê RRRR-MM-DD");
		if (lvUrlop == null)
			return;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date lvData = null;
			lvData = sdf.parse(lvUrlop);
			mObslugaPracownika.ustawDateUrodzeniaPracownikowi(mOkno.getPracownika().getId(), lvData);
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null, "Podaj datê w odpowiednim formacie!");
		}
	}

	public String getDataUrodzenia(PracownikDTO pmPracownik) {
		return mObslugaPracownika.getDataUrodzeniaTxt(pmPracownik);

	}

	public String getUrlop(PracownikDTO pmPracownik) {
		return mObslugaPracownika.getUrlopTxt(pmPracownik);
	}

	public void ustawUrlopNalezny() {
		new JOptionPane();
		String lvUrlop = JOptionPane.showInputDialog("Podaj Wartoœæ");
		int lvWartosc;
		try {
			lvWartosc = Integer.parseInt(lvUrlop);
			mObslugaPracownika.ustawUrlopNalezny(mOkno.getPracownika().getId(), lvWartosc);

		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Podaj Wartosc Liczbow¹!");
		}

	}
}
