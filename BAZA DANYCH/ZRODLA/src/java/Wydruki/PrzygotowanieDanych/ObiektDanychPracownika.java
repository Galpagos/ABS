package Wydruki.PrzygotowanieDanych;

import java.time.LocalDate;

import enums.EtatPracownika;
import lombok.Data;

@Data
public class ObiektDanychPracownika {

	private LocalDate mDataUrodzenia;
	private EtatPracownika mEtat;
	private Integer mUrlop;
	private Integer mIdPracownika;
	private String mNazwa;
}
