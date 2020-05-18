package pl.home.components.frames.mainframes;

import Frames.dbAccess.Frames.OknoSprawozdan.ObslugaOknaSprawozdan;
import pl.home.components.frames.parameters.OSprawozdanWejscie;
import pl.home.components.frames.src.SrcOknoSprawozdan;

public class OknoSprawozdan extends SrcOknoSprawozdan {

	private static final long serialVersionUID = 1L;
	private ObslugaOknaSprawozdan mObsluga;

	public OknoSprawozdan(OSprawozdanWejscie pmParams) {
		super(pmParams);
	}

	@Override
	protected void budujOkno() {
		super.budujOkno();
		mObsluga = new ObslugaOknaSprawozdan();
	}

	@Override
	protected void wywolajSprawozdanieRoczne() {
		mObsluga.sprawozdanieRoczne();
		dispose();
	}

	@Override
	protected void wywolajSprawozdanieMiesieczne() {
		mObsluga.sprawozdanieMiesieczne();
		dispose();
	}

	@Override
	protected void wywolajSprawozdanieRoczneUrlopy() {
		mObsluga.sprawozdanieRoczneUrlopy();
		dispose();
	}

	@Override
	protected void wywolajListeObecnosci() {
		mObsluga.generujListeObecnosci();
		dispose();
	}

	@Override
	protected void wywolajListePlac() {

		mObsluga.generujListePlac();
		dispose();
	}

}
