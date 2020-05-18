package Frames.dbAccess.Components;

import javax.swing.ComboBoxModel;

public interface ObsluzenieComboPicker {

	ComboBoxModel<Object> getComboBoxModel();

	void usun(Object pmSelectedItem);

	void dodaj();
}
