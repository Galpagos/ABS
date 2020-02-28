package pl.home.ListaPlac;

import java.math.BigDecimal;
import java.math.RoundingMode;

import Wydruki.PrzygotowanieDanych.PracownikDTO;

public class MiesiecznaPlacaPracownika {

	private BigDecimal mKwotaChorobowa = BigDecimal.ZERO.setScale(2);
	private BigDecimal mKwotaZaPrace = BigDecimal.ZERO.setScale(2);
	private BigDecimal mKwotaZaUrlopy = BigDecimal.ZERO.setScale(2);
	private PracownikDTO mPracownik;

	public BigDecimal getKwotaChorobowa() {
		return mKwotaChorobowa.setScale(2, RoundingMode.HALF_UP);
	}

	MiesiecznaPlacaPracownika addKwotaChorobowa(BigDecimal pmKwotaChorobowa) {
		this.mKwotaChorobowa = mKwotaChorobowa.add(pmKwotaChorobowa);
		return this;
	}

	public BigDecimal getKwotaZaPrace() {
		return mKwotaZaPrace.setScale(2, RoundingMode.HALF_UP);
	}

	MiesiecznaPlacaPracownika setKwotaZaPrace(BigDecimal pmKwotaZaPrace) {
		this.mKwotaZaPrace = pmKwotaZaPrace;
		return this;
	}

	public BigDecimal getKwotaZaUrlopy() {
		return mKwotaZaUrlopy.setScale(2, RoundingMode.HALF_UP);
	}

	MiesiecznaPlacaPracownika addKwotaZaUrlopy(BigDecimal pmKwotaZaUrlopy) {
		this.mKwotaZaUrlopy = mKwotaZaUrlopy.add(pmKwotaZaUrlopy);
		return this;
	}

	public PracownikDTO getPracownik() {
		return mPracownik;
	}

	MiesiecznaPlacaPracownika setPracownik(PracownikDTO pmPracownik) {
		this.mPracownik = pmPracownik;
		return this;
	}

	public BigDecimal getKwotaRazem() {
		return (mKwotaChorobowa.add(mKwotaZaPrace).add(mKwotaZaUrlopy)).setScale(2, RoundingMode.HALF_UP);
	}

}
