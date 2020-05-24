package ProjektGlowny.commons.Frames;

import java.time.LocalDate;

public interface InterfejsAbstractOkno {

	void info(Komunikat pmKomunikat, String... pmArgs);
	boolean ask(Komunikat pmPytanie, String... pmArgs);
	String askString(Komunikat pmKomunikat, String... pmArgs);
	void err(Komunikat pmKomunikat, String... pmArgs);
	LocalDate askLocalDate();

}
