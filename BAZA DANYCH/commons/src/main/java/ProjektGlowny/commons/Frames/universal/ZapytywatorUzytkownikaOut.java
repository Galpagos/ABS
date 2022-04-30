package ProjektGlowny.commons.Frames.universal;

import ProjektGlowny.commons.Frames.ParametryWyjscia;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ZapytywatorUzytkownikaOut extends ParametryWyjscia {

	private Integer mWartoscInt;
	private Double mWartoscDouble;
	private String mWarString;
}
