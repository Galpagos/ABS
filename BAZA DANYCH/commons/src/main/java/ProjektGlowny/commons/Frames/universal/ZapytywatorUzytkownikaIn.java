package ProjektGlowny.commons.Frames.universal;

import ProjektGlowny.commons.Frames.AskIntParams;
import ProjektGlowny.commons.Frames.Komunikat;
import ProjektGlowny.commons.Frames.ParametryWejscia;

import lombok.Data;

@Data
public class ZapytywatorUzytkownikaIn implements ParametryWejscia {

	private ZapytyniaUzytkownika mKontekst;
	private Komunikat mKomunikat;
	private String mDefaultText;
	private AskIntParams mIntParams;
}
