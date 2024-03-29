package pl.home.ListaPlac;

import static pl.home.ListaPlac.ListaPlacConst.KWOTA_MIESIECZNA;

import ProjektGlowny.commons.utils.Interval;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.YearMonth;

import Datownik.LicznikDaty;
import Pracownik.PracownikRepository;
import Wydruki.PrzygotowanieDanych.AbsencjaDTO;
import Wydruki.PrzygotowanieDanych.PracownikDTO;
import enums.SLRodzajeAbsencji;
import pl.home.absencje.ObslugaAbsencji;

public class ListaPlac {
	YearMonth mRokMiesiac;
	private final BigDecimal KWOTA_ZA_DZIEN = KWOTA_MIESIECZNA//
			.multiply(BigDecimal.valueOf(0.8629))//
			.setScale(8, RoundingMode.HALF_UP)//
			.divide(BigDecimal.valueOf(30), 8, RoundingMode.HALF_UP);

	private ListaPlacRepository mRepo;
	private ObslugaAbsencji mObslugaAbs;
	private PracownikRepository mPracownikRepo;

	public ListaPlac(YearMonth pmYearMonth) {
		mRokMiesiac = pmYearMonth;
		mRepo = new ListaPlacRepositoryDB();
		mObslugaAbs = new ObslugaAbsencji();
		mPracownikRepo = new PracownikRepository();
	}

	void setListaPlacRepository(ListaPlacRepository pmRepo) {
		mRepo = pmRepo;
	}

	public List<MiesiecznaPlacaPracownika> wyliczWyplate(List<PracownikDTO> pmLista) {
		return pmLista//
				.stream()//
				.map(lvPracownik -> wyliczWyplatePracownika(lvPracownik))//
				.collect(Collectors.toList());
	}

	private MiesiecznaPlacaPracownika wyliczWyplatePracownika(PracownikDTO pmPracownik) {

		MiesiecznaPlacaPracownika lvWyplata = przygotujDaneDoWyliczeniaWyplaty(pmPracownik);
		BigDecimal lvKwotaMiesieczna = KWOTA_MIESIECZNA.multiply(pmPracownik.getEtatBD());

		pmPracownik//
				.getListaAbsencji()//
				.forEach(lvAbsencja -> wyliczKwoteZAbsencji(lvAbsencja, lvWyplata));

		BigDecimal lvKwotaZaPrace = lvKwotaMiesieczna//
				.divide(BigDecimal.valueOf(liczbaDniRoboczychWMiesiacu()), 8, RoundingMode.HALF_UP)//
				.multiply(BigDecimal.valueOf(liczbaDniPrzepracowanych(pmPracownik)));

		lvWyplata.setKwotaZaPrace(lvKwotaZaPrace);
		return lvWyplata;
	}

	private void wyliczKwoteZAbsencji(AbsencjaDTO pmAbsencja, MiesiecznaPlacaPracownika pmWyplata) {

		if (LISTA_URLOPY.contains(pmAbsencja.getRodzaj()) && pmAbsencja.getOkres() != null)
			wyliczKwoteUrlopy(pmAbsencja, pmWyplata);
		if (LISTA_CHOROBA.contains(pmAbsencja.getRodzaj()) && pmAbsencja.getOkres() != null)
			wyliczKwoteChoroby(pmAbsencja, pmWyplata);
	}

	private void wyliczKwoteChoroby(AbsencjaDTO pmAbsencja, MiesiecznaPlacaPracownika pmWyplata) {
		BigDecimal lvKwotaZaDzien = pmWyplata.getPracownik().getId() == 43 ? KWOTA_ZA_DZIEN.multiply(BigDecimal.valueOf(0.25)) : KWOTA_ZA_DZIEN; // specjalna
																																					// obsluga
																																					// dla
																																					// pracownika

		pmWyplata.addKwotaChorobowa(//
				lvKwotaZaDzien//
						.multiply(pmAbsencja.getProcent().getProcent()).setScale(2, RoundingMode.HALF_UP)//
						.multiply(BigDecimal.valueOf(LicznikDaty.liczbaDniWAbsencjach(Arrays.asList(pmAbsencja)))));

	}

	private void wyliczKwoteUrlopy(AbsencjaDTO pmAbsencja, MiesiecznaPlacaPracownika pmWyplata) {
		BigDecimal lvKwotaMiesieczna = KWOTA_MIESIECZNA.multiply(pmWyplata.getPracownik().getEtatBD());

		pmWyplata.addKwotaZaUrlopy(//
				lvKwotaMiesieczna//
						.divide(BigDecimal.valueOf(liczbaDniRoboczychWMiesiacu()), 8, RoundingMode.HALF_UP)//
						.multiply(pmAbsencja.getProcent().getProcent())//
						.multiply(BigDecimal.valueOf(mRepo.ileDniRoboczych(pmAbsencja.getOkres()))));

	}

	private MiesiecznaPlacaPracownika przygotujDaneDoWyliczeniaWyplaty(PracownikDTO pmPracownik) {
		if (pmPracownik.getListaAbsencji() == null)
			pmPracownik.setListaAbsencji(mObslugaAbs.getAbsencjePracownika(pmPracownik.getId()));

		pmPracownik.setEtat(mPracownikRepo.getEtatPracownika(pmPracownik.getId()));
		LicznikDaty.filtrujAbsencjePoOkresie(pmPracownik.getListaAbsencji(), new Interval(mRokMiesiac));
		return new MiesiecznaPlacaPracownika().setPracownik(pmPracownik);
	}

	private final List<SLRodzajeAbsencji> LISTA_URLOPY = Arrays.asList(//
			SLRodzajeAbsencji.urlop_wypoczynkowy, //
			SLRodzajeAbsencji.urlop_ojcowski, //
			SLRodzajeAbsencji.urlop_okolicznosciowy, //
			SLRodzajeAbsencji.opieka2dni, //
			SLRodzajeAbsencji.urlop_w_pracy, //
			SLRodzajeAbsencji.NB);

	private final List<SLRodzajeAbsencji> LISTA_CHOROBA = Arrays.asList(// \
			SLRodzajeAbsencji.L_4, //
			SLRodzajeAbsencji.opieka_na_dziecko, //
			SLRodzajeAbsencji.opieka_na_kogos, //
			SLRodzajeAbsencji.szpital, //
			SLRodzajeAbsencji.ciaza, //
			SLRodzajeAbsencji.urlop_macierzynski, //
			SLRodzajeAbsencji.urlop_rodzicielski, //
			SLRodzajeAbsencji.swiadczenie_rehab, //
			SLRodzajeAbsencji.wypadek);

	private Integer liczbaDniPrzepracowanych(PracownikDTO pmPracownik) {

		return liczbaDniRoboczychWMiesiacu() - liczbaDniUrlopu(pmPracownik, Arrays.asList(SLRodzajeAbsencji.values()), true);
	}

	private Integer liczbaDniUrlopu(PracownikDTO pmPracownik, List<SLRodzajeAbsencji> pmLista, boolean pmCzyRobocze) {

		LicznikDaty.filtrujAbsencjePoOkresie(pmPracownik.getListaAbsencji(), new Interval(mRokMiesiac));

		List<AbsencjaDTO> lvListaOdfiltrowana = odfiltrujPoRodzaju(pmPracownik.getListaAbsencji(), pmLista);

		if (pmCzyRobocze)
			return LicznikDaty.ileDniRobotnych(lvListaOdfiltrowana);
		else
			return LicznikDaty.liczbaDniWAbsencjach(lvListaOdfiltrowana);
	}

	private List<AbsencjaDTO> odfiltrujPoRodzaju(List<AbsencjaDTO> pmList, List<SLRodzajeAbsencji> pmListaRodzajow) {
		List<AbsencjaDTO> lvListaOdfiltrowana = pmList.stream()//
				.filter(lvAbs -> pmListaRodzajow.contains(lvAbs.getRodzaj()))//
				.filter(lvAbsencja -> lvAbsencja.getOkres() != null)//
				.collect(Collectors.toList());
		return lvListaOdfiltrowana;
	}

	Integer liczbaDniRoboczychWMiesiacu() {
		return mRepo.getLiczbaDniRoboczychWMiesiacu(mRokMiesiac);

	}
}
