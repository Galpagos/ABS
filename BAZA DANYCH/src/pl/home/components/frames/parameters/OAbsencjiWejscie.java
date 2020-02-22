package pl.home.components.frames.parameters;

import Frames.dbAccess.Components.ParametryWejscia;
import Wydruki.PrzygotowanieDanych.AbsencjaDTO;

public class OAbsencjiWejscie implements ParametryWejscia {
	AbsencjaDTO mAbsencja;

	public AbsencjaDTO getAbsencja() {
		return mAbsencja;
	}

	private OAbsencjiWejscie(Builder builder) {
		this.mAbsencja = builder.mAbsencja;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private AbsencjaDTO mAbsencja;

		private Builder() {
		}

		public Builder withAbsencja(AbsencjaDTO absencja) {
			this.mAbsencja = absencja;
			return this;
		}

		public OAbsencjiWejscie build() {
			return new OAbsencjiWejscie(this);
		}
	}

}
