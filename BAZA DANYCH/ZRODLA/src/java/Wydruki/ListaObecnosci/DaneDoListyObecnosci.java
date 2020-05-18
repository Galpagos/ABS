package Wydruki.ListaObecnosci;

import java.time.LocalDate;
import java.util.List;

import Wydruki.PrzygotowanieDanych.PracownikDTO;
import lombok.Data;

@Data
public class DaneDoListyObecnosci {
	private List<PracownikDTO> mListaLewa;
	private List<PracownikDTO> mListaPrawa;
	private LocalDate mData;
	private String mNaglowek;
	private String mStopka;
	private Integer mWysokoscWiersza;
}
