package Absencja;

import java.time.LocalDate;

import Wydruki.PrzygotowanieDanych.AbsencjaDTO;

public interface AbsencjaRepositor {

	Object[][] getAbsencjePracownika(int pmId);

	Object[][] getAbsencjePoId(int pmId);

	int ileDniWolnych(LocalDate pmLocalDate, LocalDate pmLocalDate2);

	void dodajAbsencje(AbsencjaDTO pmAbs);

	void usunAbsencje(int pmID);

	int zliczAbsencjePracownikaWOkresie(AbsencjaDTO pmAbsencja);

}
