package ProjektGlowny.commons.Frames;

import static ProjektGlowny.commons.utils.KomunikatUtils.KOMUNIKAT;
import static ProjektGlowny.commons.utils.KomunikatUtils.TYTUL;
import static ProjektGlowny.commons.utils.KomunikatUtils.przygotujKomunikat;
import static ProjektGlowny.commons.utils.KomunikatUtils.przygotujMapeZKomunikatu;

import ProjektGlowny.commons.Components.DatePicker;
import ProjektGlowny.commons.Frames.universal.ZapytyniaUzytkownika;
import ProjektGlowny.commons.Frames.universal.ZapytywatorUzytkownika;
import ProjektGlowny.commons.Frames.universal.ZapytywatorUzytkownikaIn;

import java.util.Map;

import java.awt.Component;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

public abstract class AbstractOkno<T1 extends ParametryWejscia, T2 extends ParametryWyjscia> extends JDialog implements InterfejsAbstractOkno {

	private static final long serialVersionUID = 1L;
	protected T1 mParamsIn;
	protected T2 mParamsOut;
	protected boolean mAccepted;

	protected JButton mokButton;
	protected JButton mcancelButton;

	public AbstractOkno(T1 pmParams) {
		mParamsIn = pmParams;
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
		mcancelButton.setVerticalTextPosition(SwingConstants.CENTER);
		mcancelButton.setVerticalAlignment(SwingConstants.CENTER);
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
		mokButton.setVerticalTextPosition(SwingConstants.CENTER);
		mokButton.setVerticalAlignment(SwingConstants.CENTER);
		mokButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
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

	protected void beforeClose() {
		mParamsOut = budujWyjscie();
	}

	protected abstract void onOpen();

	protected abstract void przypiszMetody();

	protected abstract void odswiezKontrolki();

	protected abstract void budujOkno();

	protected abstract T2 budujWyjscie();

	public T2 get() {
		if (mParamsOut == null)
			return null;
		mParamsOut.setAccepted(mAccepted);
		return mParamsOut;
	}

	protected void readParams() {
	}

	@Override
	public void info(Komunikat pmKomunikat, String... pmArgs) {
		Map<String, String> lvMapa = przygotujMapeZKomunikatu(pmKomunikat, pmArgs);
		JOptionPane.showMessageDialog(null, lvMapa.get(KOMUNIKAT), lvMapa.get(TYTUL), JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public boolean ask(Komunikat pmKomunikat, String... pmArgs) {
		Map<String, String> lvMapa = przygotujMapeZKomunikatu(pmKomunikat, pmArgs);
		int reply = JOptionPane.showConfirmDialog(null, lvMapa.get(KOMUNIKAT), lvMapa.get(TYTUL), JOptionPane.YES_NO_OPTION);
		if (reply == JOptionPane.YES_OPTION) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void err(Komunikat pmKomunikat, String... pmArgs) {
		Map<String, String> lvMapa = przygotujMapeZKomunikatu(pmKomunikat, pmArgs);
		JOptionPane.showMessageDialog(null, lvMapa.get(KOMUNIKAT), lvMapa.get(TYTUL), JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public String askString(Komunikat pmKomunikat, String... pmArgs) {
		Map<String, String> lvMapa = przygotujMapeZKomunikatu(pmKomunikat, pmArgs);
		return JOptionPane.showInputDialog(null, lvMapa.get(KOMUNIKAT), lvMapa.get(TYTUL));
	}

	@Override
	public Integer askInt(AskIntParams pmDefault, Komunikat pmKomunikat, String... pmArgs) {
		Komunikat lvKomunikat = przygotujKomunikat(pmKomunikat, pmArgs);
		ZapytywatorUzytkownikaIn lv = new ZapytywatorUzytkownikaIn();
		lv.setKontekst(ZapytyniaUzytkownika.INTEGER);
		lv.setKomunikat(lvKomunikat);
		lv.setIntParams(pmDefault);
		return new ZapytywatorUzytkownika(lv).get().getWartoscInt();
	}

	@Override
	public LocalDate askLocalDate() {
		return new DatePicker().setPickedLocalDate();
	}
}
