package pl.home.absencje;

public enum WalidatorAbsencjiFactory {
	INSTANCE;

	public WalidatorAbsencji create() {
		return new WalidatorAbsencji().setAbsencjeRepository(new AbsencjeRepositoryDB());
	}
}
