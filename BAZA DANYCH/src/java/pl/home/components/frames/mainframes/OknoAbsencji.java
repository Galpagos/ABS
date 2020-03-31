package pl.home.components.frames.mainframes;

import java.time.LocalDate;

import Absencja.ObslugaAbsencji;
import Absencja.WalidatorAbsenci;
import Datownik.Data;
import Datownik.Interval;
import Enums.SLRodzajeAbsencji;
import Wydruki.PrzygotowanieDanych.AbsencjaDTO;
import pl.home.ListaPlac.SLEkwiwalentZaUrlop;
import pl.home.components.frames.parameters.OAbsencjiWejscie;
import pl.home.components.frames.src.SrcOknoAbsencji;

public class OknoAbsencji extends SrcOknoAbsencji {

	private static final long serialVersionUID = 1L;
	private AbsencjaDTO mAbsencja;
	private ObslugaAbsencji mObslugaAbsencji;
	private WalidatorAbsenci mWalidator;

	public OknoAbsencji(OAbsencjiWejscie pmParams) {
		super(pmParams);
	}

	@Override
	protected void readParams() {
		mAbsencja = mParams.getAbsencja();
	}

	@Override
	protected void budujOkno() {
		super.budujOkno();
		mObslugaAbsencji = new ObslugaAbsencji();
		mWalidator = new WalidatorAbsenci();
	}

	@Override
	protected void odswiezKontrolki() {

		super.odswiezKontrolki();
		cbRodzajAbsencji.setSelectedItem(mAbsencja.getRodzaj());
		cbProcent.setSelectedItem(mAbsencja.getProcent());
		mDataOd.setValue(Data.DateFromLocalDate(mAbsencja.getOkres().getStart()));
		mDataDo.setValue(Data.DateFromLocalDate(mAbsencja.getOkres().getEnd()));
	}

	@Override
	public void zapiszAbsencje() {

		LocalDate lvOd = mDataOd.getDateValue();
		LocalDate lvDo = mDataDo.getDateValue();

		if (!mWalidator.czyPrawidloweDaty(lvOd, lvDo))
			return;
		AbsencjaDTO lvNowaAbsencja = AbsencjaDTO.builder().setId(mAbsencja.getId())
				.setIdPracownika(mAbsencja.getIdPracownika());
		lvNowaAbsencja.setOkres(new Interval(lvOd, lvDo));
		lvNowaAbsencja.setRodzaj((SLRodzajeAbsencji) cbRodzajAbsencji.getSelectedItem());
		lvNowaAbsencja.setProcent((SLEkwiwalentZaUrlop) cbProcent.getSelectedItem());

		if (mWalidator.czyWystepujeAbsencjaWOkresie(lvNowaAbsencja))
			return;
		AbsencjaDTO lvUsuwana = mObslugaAbsencji.pobierzAbsencjePoId(mAbsencja.getId());
		mObslugaAbsencji.usunAbsencje(mAbsencja.getId(), true);
		if (mWalidator.czyPrzekraczaLimity(lvNowaAbsencja)) {
			mObslugaAbsencji.dodajAbsencje(lvUsuwana);
		} else {
			mWalidator.czyDniL4Ciagiem(lvNowaAbsencja);
			mObslugaAbsencji.dodajAbsencje(lvNowaAbsencja);
			mAbsencja.setOkres(lvNowaAbsencja.getOkres());
			mAbsencja.setRodzaj(lvNowaAbsencja.getRodzaj());
			mAbsencja.setProcent(lvNowaAbsencja.getProcent());
			dispose();
		}
	}

}
