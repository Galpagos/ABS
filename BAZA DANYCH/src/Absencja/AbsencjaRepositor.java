package Absencja;

import java.util.Date;

import PrzygotowanieDanych.AbsencjaDTO;

public interface AbsencjaRepositor
{

	Object[][] getAbsencjePracownika(int pmId);

	Object[][] getAbsencjePoId(int pmId);

	int ileDniWolnych(Date pmDate, Date pmDate2);

	void dodajAbsencje(AbsencjaDTO pmAbs);

	void usunAbsencje(int pmID);

	int zliczAbsencjePracownikaWOkresie(AbsencjaDTO pmAbsencja);

}
