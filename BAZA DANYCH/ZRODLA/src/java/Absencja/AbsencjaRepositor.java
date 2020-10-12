package Absencja;

import java.time.LocalDate;

import Wydruki.PrzygotowanieDanych.AbsencjaDTO;

interface AbsencjaRepositor {

	int ileDniWolnych(LocalDate pmLocalDate, LocalDate pmLocalDate2);

	int zliczAbsencjePracownikaWOkresie(AbsencjaDTO pmAbsencja);

}
