package pl.home.DniWolne;

import java.time.LocalDate;

import lombok.Data;

@Data
public class DzienWolnyDTO {

	private Integer IdTabeli;
	private String opis;
	private LocalDate data;
}
