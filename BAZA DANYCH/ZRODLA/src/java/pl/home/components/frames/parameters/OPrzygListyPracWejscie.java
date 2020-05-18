package pl.home.components.frames.parameters;

import java.util.Collections;
import java.util.List;

import ProjektGlowny.commons.Frames.ParametryWejscia;
import Wydruki.PrzygotowanieDanych.PracownikDTO;

public class OPrzygListyPracWejscie implements ParametryWejscia {

	private List<PracownikDTO> mLista;
	private String mNazwa;

	public List<PracownikDTO> getLista() {
		return mLista;
	}

	public String getNazwa() {
		return mNazwa;
	}

	private OPrzygListyPracWejscie(Builder builder) {
		this.mLista = builder.mLista;
		this.mNazwa = builder.mNazwa;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private List<PracownikDTO> mLista = Collections.emptyList();
		private String mNazwa = "Wybierz pracowników";

		private Builder() {
		}

		public Builder withLista(List<PracownikDTO> lista) {
			this.mLista = lista;
			return this;
		}

		public Builder withNazwa(String nazwa) {
			this.mNazwa = nazwa;
			return this;
		}

		public OPrzygListyPracWejscie build() {
			return new OPrzygListyPracWejscie(this);
		}
	}
}
