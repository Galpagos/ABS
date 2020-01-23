package Frames.dbAccess.Components;

import javax.swing.JDialog;

public abstract class AbstractOkno extends JDialog {

	private static final long serialVersionUID = 1L;
	protected ScriptParams mParams;

	public AbstractOkno(ScriptParams pmParams) {
		mParams = pmParams;
		setModalityType(ModalityType.APPLICATION_MODAL);

		readParams();
		budujOkno();
		przypiszMetody();
		odswiezKontrolki();
		onOpen();
		setVisible(true);
	}

	protected abstract void readParams();

	protected abstract void onOpen();

	protected abstract void przypiszMetody();

	protected abstract void odswiezKontrolki();

	protected abstract void budujOkno();
}
