package pl.home.DniWolne;

import java.time.LocalDate;

import lombok.Data;

@Data
public class DzienWolnyDTO {

	private Integer mIdTabeli;
	private String mOpis;
	private LocalDate mData;
}
