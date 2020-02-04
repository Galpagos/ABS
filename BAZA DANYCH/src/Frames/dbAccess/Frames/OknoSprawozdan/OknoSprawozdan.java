package Frames.dbAccess.Frames.OknoSprawozdan;

import Frames.dbAccess.Components.ScriptParams;

public class OknoSprawozdan extends SrcOknoSprawozdan {

	private static final long serialVersionUID = 1L;
	private ObslugaOknaSprawozdan mObsluga;

	public OknoSprawozdan(ScriptParams pmParams) {
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
