package Wydruki.ListaPlac;

import lombok.Getter;

@Getter
public enum ListaPlacColumnEnum {

	PRACOWNIK(0, "Pracownik"),
	KWOTA_Z_P(1, "Kwota za pracę"),
	KWOTA_Z_CH(2, "Kwota za chorobę"),
	KWOTA_Z_U(3, "Kwota za urlopy"),
	RAZEM(4, "Razem");

	private Integer mId;
	private String mColumnName;

	ListaPlacColumnEnum(Integer pmId, String pmColumnName) {
		mId = pmId;
		mColumnName = pmColumnName;
	}

}
