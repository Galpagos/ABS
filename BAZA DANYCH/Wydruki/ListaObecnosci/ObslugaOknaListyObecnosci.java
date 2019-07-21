package ListaObecnosci;

public class ObslugaOknaListyObecnosci
{
	iDaneDoListyObecnosci mOkno;

	public ObslugaOknaListyObecnosci(iDaneDoListyObecnosci pmOkno)
	{
		mOkno = pmOkno;
	}

	public void wywolajWydruk(OknoListaObecnosci pmOknoListaObecnosci)
	{
		new ListaObecnosci(pmOknoListaObecnosci);
	}
}
