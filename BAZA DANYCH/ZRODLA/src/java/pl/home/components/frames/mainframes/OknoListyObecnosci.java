package pl.home.components.frames.mainframes;

import Wydruki.ListaObecnosci.ListaObecnosci;
import pl.home.components.frames.parameters.ListaObecnosciWejscie;
import pl.home.components.frames.src.SrcListaObecnosci;

public class OknoListyObecnosci extends SrcListaObecnosci {

	private static final long serialVersionUID = 1L;

	public OknoListyObecnosci(ListaObecnosciWejscie pmParams) {
		super(pmParams);
	}

	@Override
	protected void wydrukuj() {
		new ListaObecnosci(mDane);
	}

}
