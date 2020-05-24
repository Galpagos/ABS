package ProjektGlowny.commons.Frames;

import ProjektGlowny.commons.Components.DatePicker;

import java.util.HashMap;
import java.util.Map;

import java.awt.Component;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

public abstract class AbstractOkno<T1 extends ParametryWejscia, T2 extends ParametryWyjscia> extends JDialog implements InterfejsAbstractOkno {

	private static final String TYTUL = "Tytul";
	private static final String KOMUNIKAT = "Komunikat";
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

	@Override
	public void info(Komunikat pmKomunikat, String... pmArgs) {
		Map<String, String> lvMapa = przygotujKomunikat(pmKomunikat, pmArgs);
		JOptionPane.showMessageDialog(null, lvMapa.get(KOMUNIKAT), lvMapa.get(TYTUL), JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public boolean ask(Komunikat pmKomunikat, String... pmArgs) {
		Map<String, String> lvMapa = przygotujKomunikat(pmKomunikat, pmArgs);
		int reply = JOptionPane.showConfirmDialog(null, lvMapa.get(KOMUNIKAT), lvMapa.get(TYTUL), JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.YES_OPTION) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void err(Komunikat pmKomunikat, String... pmArgs) {
		Map<String, String> lvMapa = przygotujKomunikat(pmKomunikat, pmArgs);
		JOptionPane.showMessageDialog(null, lvMapa.get(KOMUNIKAT), lvMapa.get(TYTUL), JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public String askString(Komunikat pmKomunikat, String... pmArgs) {
		Map<String, String> lvMapa = przygotujKomunikat(pmKomunikat, pmArgs);
		return JOptionPane.showInputDialog(null, lvMapa.get(KOMUNIKAT), lvMapa.get(TYTUL));
	}

	private Map<String, String> przygotujKomunikat(Komunikat pmKomunikat, String... pmArgs) {
		Map<String, String> lvMap = new HashMap<>();
		String lvKomunikat = pmKomunikat.getKomunikat();
		String lvTytul = pmKomunikat.getTytul();
		for (int i = 0; i < pmArgs.length; i++) {
			lvKomunikat = lvKomunikat.replaceFirst(Komunikat.ARG, pmArgs[i]);
			lvTytul = lvTytul.replaceFirst(Komunikat.ARG, pmArgs[i]);
		}
		lvMap.put(KOMUNIKAT, lvKomunikat);
		lvMap.put(TYTUL, lvTytul);
		return lvMap;
	}

	@Override
	public LocalDate askLocalDate() {
		return new DatePicker().setPickedLocalDate();
	}
}
