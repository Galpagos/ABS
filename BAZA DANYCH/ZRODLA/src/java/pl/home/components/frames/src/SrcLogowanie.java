package pl.home.components.frames.src;

import ProjektGlowny.commons.DbBuilder.QueryBuilder;
import ProjektGlowny.commons.Frames.AbstractOkno;
import ProjektGlowny.commons.Frames.InterfejsAbstractOkno;
import ProjektGlowny.commons.Frames.ParametryWejscia;
import ProjektGlowny.commons.Frames.ParametryWyjscia;
import ProjektGlowny.commons.utils.KomunikatUtils;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import Utils.GridUtils;
import dbAccesspl.home.Database.Table.Zestawienie.SysInfoColumns;

public class SrcLogowanie extends AbstractOkno<ParametryWejscia, ParametryWyjscia> implements InterfejsAbstractOkno {

	private static final long serialVersionUID = 1L;
	private JPanel mMainPanel;
	private JLabel lblPassword;
	private JPasswordField mPassword;
	private JButton btnLoguj;

	private String mHaslo;
	public SrcLogowanie(ParametryWejscia pmParams) {
		super(pmParams);

	}

	@Override
	protected void onOpen() {
	}

	@Override
	protected void przypiszMetody() {
	}

	@Override
	protected void odswiezKontrolki() {
	}

	@Override
	protected void budujOkno() {
		requestFocus();
		setBounds(100, 100, 825, 560);

		mHaslo = QueryBuilder.SELECT().select(SysInfoColumns.WARTOSC)//
				.andWarunek(SysInfoColumns.NAZWA, "HASLO")//
				.execute().getAsString(SysInfoColumns.WARTOSC);
		if (czyPierwszeLogowanie()) {
			info(KomunikatUtils.fromString("Pierwsze logowanie", "Proszę podać hasło!\nBędzie wymagane przy kolejnym logowaniu"));
		}

		mMainPanel = new JPanel(new GridBagLayout());

		lblPassword = new JLabel("Hasło");
		mPassword = new JPasswordField();
		mPassword.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent pmE) {
				if (KeyEvent.VK_ENTER == pmE.getKeyCode())
					btnLoguj.doClick();
			}

			@Override
			public void keyTyped(KeyEvent pmE) {
			}

			@Override
			public void keyReleased(KeyEvent pmE) {
			}
		});
		mPassword.setPreferredSize(new Dimension(100, 20));
		btnLoguj = new JButton("Zaloguj");
		btnLoguj.addActionListener(lvE -> {
			if (czyHasloOk()) {
				mAccepted = true;
				beforeClose();
				info(KomunikatUtils.fromString("Logowanie", "Hasło jest poprawne.\nZe chwilę nastąpi zalogowanie do aplikacji."));
				dispose();
			} else
				err(KomunikatUtils.fromString("Błąd", "Błędne hasło"));
		});

		mMainPanel.add(lblPassword, GridUtils.gridCons(0, 0));
		mMainPanel.add(mPassword, GridUtils.gridCons(1, 0));
		mMainPanel.add(btnLoguj, GridUtils.gridCons(2, 0));
		this.add(mMainPanel);

	}

	public boolean czyPierwszeLogowanie() {
		return mHaslo == null || mHaslo.isEmpty();
	}

	private boolean czyHasloOk() {
		if (mPassword.getText() == null || mPassword.getText().trim().isEmpty())
			return false;
		if (czyPierwszeLogowanie()) {
			QueryBuilder.INSERT()//
					.set(SysInfoColumns.NAZWA, "HASLO")//
					.set(SysInfoColumns.WARTOSC, mPassword.getText().trim())//
					.execute();
			return true;
		} else
			return mHaslo.trim().equals(mPassword.getText().trim());
	}

	@Override
	protected ParametryWyjscia budujWyjscie() {
		return new ParametryWyjscia();
	}

}
