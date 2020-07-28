package ProjektGlowny.commons.Frames.universal;

import ProjektGlowny.commons.Components.LIntegerField;
import ProjektGlowny.commons.Frames.AbstractOkno;
import ProjektGlowny.commons.Frames.Komunikat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ZapytywatorUzytkownika extends AbstractOkno<ZapytywatorUzytkownikaIn, ZapytywatorUzytkownikaOut> {

	private static final long serialVersionUID = 1L;
	private ZapytyniaUzytkownika mKontekst = null;
	private Komunikat mKomunikat = null;
	private JLabel lbPytanie = null;
	private JTextField mPole = null;
	private JLabel mWalidacja = null;
	private JPanel mContentPane = null;

	public ZapytywatorUzytkownika(ZapytywatorUzytkownikaIn pmParams) {
		super(pmParams);
	}

	@Override
	protected void onOpen() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void przypiszMetody() {
		mPole.getDocument().addDocumentListener(wyczyszWalidacjeListener());
	}

	@Override
	protected void odswiezKontrolki() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void budujOkno() {
		mContentPane = new JPanel(new BorderLayout());
		mContentPane.setBorder(new EmptyBorder(25, 15, 15, 5));
		setContentPane(mContentPane);
		JPanel mOk = new JPanel();
		mOk.add(mokButton);
		mOk.add(mcancelButton);
		mOk.setLayout(new GridLayout(1, 2));

		JPanel mPolePanel = new JPanel();
		this.setTitle(mKomunikat.getTytul());
		lbPytanie = new JLabel(mKomunikat.getKomunikat());
		if (ZapytyniaUzytkownika.INTEGER == mKontekst)
			mPole = new LIntegerField(mParamsIn.getDefaultText());
		else
			mPole = new JTextField();
		mWalidacja = new JLabel();
		lbPytanie.setLabelFor(mPole);
		mWalidacja.setForeground(Color.RED);
		mWalidacja.setVisible(false);
		mPolePanel.setLayout(new BorderLayout());
		mPolePanel.add(mPole, BorderLayout.PAGE_START);
		mPolePanel.add(mWalidacja, BorderLayout.PAGE_END);
		mPolePanel.setBorder(new EmptyBorder(15, 15, 15, 15));
		mContentPane.add(lbPytanie, BorderLayout.PAGE_START);
		mContentPane.add(mPolePanel, BorderLayout.CENTER);
		mContentPane.add(mOk, BorderLayout.PAGE_END);
		pack();
	}

	@Override
	protected ZapytywatorUzytkownikaOut budujWyjscie() {
		return ZapytywatorUzytkownikaOut//
				.builder()//
				.wartoscInt(ZapytyniaUzytkownika.INTEGER == mKontekst && mAccepted ? Integer.valueOf(mPole.getText()) : null)//
				.warString(mPole.getText())//
				.build();
	}

	@Override
	protected boolean validateForm() {
		if (mPole.getText() == null || mPole.getText().equals("")) {
			return waliduj("Brak wprowadzonej wartości!");
		}
		if (ZapytyniaUzytkownika.INTEGER == mKontekst) {
			Integer lvWartosc = Integer.valueOf(mPole.getText());
			if (mParamsIn.getIntParams().getMinValue() != null && lvWartosc.intValue() < mParamsIn.getIntParams().getMinValue().intValue())
				return waliduj("Wartość musi być większa niż " + mParamsIn.getIntParams().getMinValue());

			if (mParamsIn.getIntParams().getMaxValue() != null && lvWartosc.intValue() > mParamsIn.getIntParams().getMaxValue().intValue())
				return waliduj("Wartość musi być mniejsza niż " + mParamsIn.getIntParams().getMaxValue());
		}

		return super.validateForm();
	}

	private boolean waliduj(String pmKomunikat) {
		mWalidacja.setVisible(true);
		mWalidacja.setText(pmKomunikat);
		mPole.setBackground(Color.red);
		pack();
		return false;
	}

	@Override
	protected void readParams() {
		mKontekst = mParamsIn.getKontekst();
		mKomunikat = mParamsIn.getKomunikat();
		super.readParams();
	}

	private DocumentListener wyczyszWalidacjeListener() {
		return new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				mWalidacja.setVisible(false);
				mPole.setBackground(Color.white);
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				mWalidacja.setVisible(false);
				mPole.setBackground(Color.white);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				mWalidacja.setVisible(false);
				mPole.setBackground(Color.white);

			}
		};
	}
}
