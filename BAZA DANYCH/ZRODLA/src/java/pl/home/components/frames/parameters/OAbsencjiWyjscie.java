package pl.home.components.frames.parameters;

import ProjektGlowny.commons.Frames.ParametryWyjscia;

public class OAbsencjiWyjscie extends ParametryWyjscia {

	private OAbsencjiWyjscie(Builder builder) {
		super.setAccepted(builder.accepted);
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private boolean accepted;

		private Builder() {
		}

		public Builder withAccepted(boolean accepted) {
			this.accepted = accepted;
			return this;
		}

		public OAbsencjiWyjscie build() {
			return new OAbsencjiWyjscie(this);
		}
	}

}
