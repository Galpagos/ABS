package ProjektGlowny.commons.Frames;

import java.time.LocalDate;

public interface InterfejsAbstractOkno {

	void info(Komunikat pmKomunikat, String... pmArgs);
	boolean ask(Komunikat pmPytanie, String... pmArgs);
	String askString(Komunikat pmKomunikat, String... pmArgs);
	void err(Komunikat pmKomunikat, String... pmArgs);
	LocalDate askLocalDate();
	Integer askInt(AskIntParams pmDefault, Komunikat pmKomunikat, String... pmArgs);
	Integer askDouble(AskIntParams pmDefault, Komunikat pmKomunikat, String[] pmArgs);

}
