package Frames.dbAccess.Frames.Absencja;

import java.util.Date;

import Absencja.ObslugaAbsencji;
import Absencja.WalidatorAbsenci;
import Datownik.JodaTime;
import Enums.SLRodzajeAbsencji;
import Frames.dbAccess.Components.ScriptParams;
import Wydruki.PrzygotowanieDanych.AbsencjaDTO;

public class OknoAbsencji extends SrcOknoAbsencji {

	private static final long serialVersionUID = 1L;
	private AbsencjaDTO mAbsencja;
	private ObslugaAbsencji mObslugaAbsencji;
	private WalidatorAbsenci mWalidator;

	public OknoAbsencji(ScriptParams pmParams) {
		super(pmParams);
	}

	@Override
	protected void readParams() {
		mAbsencja = (AbsencjaDTO) mParams.get(OknoAbsencjiParams.ABSENCJA_DTO);
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
		mDataOd.setValue(mAbsencja.getOkres().getStart().toDate());
		mDataDo.setValue(mAbsencja.getOkres().getEnd().toDate());
	}

	@Override
	protected void zapiszAbsencje() {
		super.zapiszAbsencje();

		Date lvOd = (Date) mDataOd.getValue();
		Date lvDo = (Date) mDataDo.getValue();

		if (!mWalidator.czyPrawidloweDaty(lvOd, lvDo))
			return;
		AbsencjaDTO lvNowaAbsencja = AbsencjaDTO.builder().setId(mAbsencja.getId())
				.setIdPracownika(mAbsencja.getIdPracownika());
		lvNowaAbsencja.setOkres(JodaTime.okresOdDo(lvOd, lvDo));
		lvNowaAbsencja.setRodzaj((SLRodzajeAbsencji) cbRodzajAbsencji.getSelectedItem());

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
			dispose();
		}
	}
}
