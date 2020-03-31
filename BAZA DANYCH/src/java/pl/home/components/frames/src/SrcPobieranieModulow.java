package pl.home.components.frames.src;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Enums.SLRodzajeAbsencji;
import Frames.dbAccess.Components.AbstractOkno;
import pl.home.components.frames.parameters.PModWejscie;
import pl.home.components.frames.parameters.PModWyjscie;

public abstract class SrcPobieranieModulow extends AbstractOkno<PModWejscie, PModWyjscie> {

	private static final long serialVersionUID = 1L;

	public SrcPobieranieModulow(PModWejscie pmParams) {
		super(pmParams);
	}

	public SrcPobieranieModulow() {
		super(null);
	}

	private JPanel contentPanel = new JPanel();
	protected List<JCheckBox> mListaCheckboxow;
	protected JCheckBox cbWszystkie;

	@Override
	protected void budujOkno() {
		contentPanel = new JPanel();
		setBounds(100, 100, 697, 307);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		mListaCheckboxow = new ArrayList<JCheckBox>();
		contentPanel.setLayout(new GridLayout(0, 3));
		for (SLRodzajeAbsencji lvAbs : SLRodzajeAbsencji.values()) {
			JCheckBox lvBox = new JCheckBox(lvAbs.toString());
			lvBox.setSelected(true);
			mListaCheckboxow.add(lvBox);
			contentPanel.add(lvBox);
		}
		JPanel lvButtonPane = new JPanel();
		lvButtonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(lvButtonPane, BorderLayout.SOUTH);
		cbWszystkie = new JCheckBox("Wszystkie");
		cbWszystkie.setSelected(true);
		lvButtonPane.add(cbWszystkie);
		lvButtonPane.add(mokButton);
		getRootPane().setDefaultButton(mokButton);
		lvButtonPane.add(mcancelButton);
	}

	@Override
	protected void onOpen() {
	}

	@Override
	protected void przypiszMetody() {
		cbWszystkie.addActionListener(e -> zaznaczWszystkie());
	}

	public abstract void zaznaczWszystkie();

	@Override
	protected void odswiezKontrolki() {
	}

}
