package ProjektGlowny.commons.Frames;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.SwingConstants;

public abstract class AbstractOkno<T1 extends ParametryWejscia, T2 extends ParametryWyjscia> extends JDialog {

	private static final long serialVersionUID = 1L;
	protected T1 mParams;
	protected boolean mAccepted;

	protected JButton mokButton;
	protected JButton mcancelButton;

	public AbstractOkno(T1 pmParams) {
		mParams = pmParams;
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 927, 473);
		createOkButton();

		createCancelButton();

		readParams();
		budujOkno();
		przypiszMetody();
		onOpen();
		odswiezKontrolki();
		setVisible(true);
	}

	private void createCancelButton() {
		mcancelButton = new JButton("Wyjście");
		mcancelButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		mcancelButton.setVerticalAlignment(SwingConstants.BOTTOM);
		mcancelButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
		mcancelButton.setActionCommand("Cancel");
		mcancelButton.addActionListener(e -> {
			mAccepted = false;
			beforeClose();
			dispose();
		});
	}

	private void createOkButton() {
		mokButton = new JButton("OK");
		mokButton.setActionCommand("OK");
		mokButton.addActionListener(e -> {
			if (validateForm()) {
				mAccepted = true;
				beforeClose();
				dispose();
			}
		});
	}

	protected boolean validateForm() {
		return true;
	}

	protected abstract void beforeClose();

	protected abstract void onOpen();

	protected abstract void przypiszMetody();

	protected abstract void odswiezKontrolki();

	protected abstract void budujOkno();

	protected abstract T2 budujWyjscie();

	public T2 get() {
		T2 lvWyjscie = budujWyjscie();
		lvWyjscie.setAccepted(mAccepted);
		return lvWyjscie;
	}

	protected void readParams() {
	}

}
