package pl.home.components.frames.parameters;

import ProjektGlowny.commons.Frames.ParametryWyjscia;

import Wydruki.PrzygotowanieDanych.AbsencjaDTO;

public class OAbsencjiWyjscie extends ParametryWyjscia {

	private AbsencjaDTO mAbsencja;

	private OAbsencjiWyjscie(Builder builder) {
		super.setAccepted(builder.accepted);
		mAbsencja = builder.mAbsencja;
	}

	public AbsencjaDTO getAbsencja() {
		return mAbsencja;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private boolean accepted;
		private AbsencjaDTO mAbsencja;
		private Builder() {
		}

		public Builder withAccepted(boolean accepted) {
			this.accepted = accepted;
			return this;
		}

		public Builder withAbsencja(AbsencjaDTO pmAbsencja) {
			this.mAbsencja = pmAbsencja;
			return this;
		}

		public OAbsencjiWyjscie build() {
			return new OAbsencjiWyjscie(this);
		}
	}

}
