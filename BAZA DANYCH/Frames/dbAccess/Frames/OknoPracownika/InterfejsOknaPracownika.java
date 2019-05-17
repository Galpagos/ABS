package dbAccess.Frames.OknoPracownika;

import javax.swing.JLabel;

import dbAccess.Absencja;
import dbAccess.ZestawienieBean;

public interface InterfejsOknaPracownika
{
	public ZestawienieBean getPracownika();

	public int getZaznaczenieTabeli();

	public Absencja getAbsencjeZTabeli();

	public void odswiezTabele();

	public JLabel getLblGrupy();
}
