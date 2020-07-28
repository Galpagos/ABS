package pl.home.components.frames.mainframes;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;

import enums.SLRodzajeAbsencji;
import pl.home.components.frames.parameters.PModWejscie;
import pl.home.components.frames.parameters.PModWyjscie;
import pl.home.components.frames.src.SrcPobieranieModulow;

public class PobieranieModulow extends SrcPobieranieModulow {

	private static final long serialVersionUID = 1L;

	private List<SLRodzajeAbsencji> mListaAbsencji;

	public PobieranieModulow(PModWejscie pmParams) {
		super(pmParams);
	}

	public PobieranieModulow() {
	}

	@Override
	protected void onOpen() {
		super.onOpen();
		mListaAbsencji = new ArrayList<SLRodzajeAbsencji>();
	}

	@Override
	public void zaznaczWszystkie() {

		for (JCheckBox lvBox : mListaCheckboxow)
			lvBox.setSelected(cbWszystkie.isSelected());
	}

	@Override
	protected void beforeClose() {
		if (mAccepted)
			for (JCheckBox lvBox : mListaCheckboxow) {
				if (lvBox.isSelected()) {
					mListaAbsencji.add(SLRodzajeAbsencji.AbsencjaPoNazwie(lvBox.getText()));
				}
			}
		else
			mListaAbsencji = new ArrayList<SLRodzajeAbsencji>();
		super.beforeClose();
	}

	@Override
	protected PModWyjscie budujWyjscie() {

		return PModWyjscie.builder().listaAbsencji(mListaAbsencji).build();
	}

}
