package pl.home.components.frames.parameters;

import java.util.Collections;
import java.util.List;

import ProjektGlowny.commons.Frames.ParametryWyjscia;
import Wydruki.PrzygotowanieDanych.PracownikDTO;

public class OPrzygListyPracWyjscie extends ParametryWyjscia {

	List<PracownikDTO> mLista;

	public List<PracownikDTO> getLista() {
		return mLista;
	}

	private OPrzygListyPracWyjscie(Builder builder) {
		this.mLista = builder.mLista;
		super.setAccepted(builder.accepted);
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private List<PracownikDTO> mLista = Collections.emptyList();
		private boolean accepted = false;

		private Builder() {
		}

		public Builder withLista(List<PracownikDTO> lista) {
			this.mLista = lista;
			return this;
		}

		public Builder withAccepted(boolean accepted) {
			this.accepted = accepted;
			return this;
		}

		public OPrzygListyPracWyjscie build() {
			return new OPrzygListyPracWyjscie(this);
		}
	}
}