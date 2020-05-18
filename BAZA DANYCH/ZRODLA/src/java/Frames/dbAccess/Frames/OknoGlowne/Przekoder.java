package Frames.dbAccess.Frames.OknoGlowne;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import Wersja.Przekod;

public class Przekoder implements WindowListener {

	@Override
	public void windowOpened(WindowEvent e) {
		try {
			Przekod.Wykonaj();
		} catch (Exception lvE) {
			lvE.printStackTrace();
		}
	}

	@Override
	public void windowClosing(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {

	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {

	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {

	}

}
