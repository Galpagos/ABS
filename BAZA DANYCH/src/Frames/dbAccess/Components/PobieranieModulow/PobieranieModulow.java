package Frames.dbAccess.Components.PobieranieModulow;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Enums.SLRodzajeAbsencji;

public class PobieranieModulow {
	JDialog mOkno;
	private final JPanel contentPanel = new JPanel();
	List<SLRodzajeAbsencji> mListaAbsencji;
	List<JCheckBox> mListaCheckboxow;
	JCheckBox cbWszystkie;

	public PobieranieModulow() {
		mListaAbsencji = new ArrayList<SLRodzajeAbsencji>();
		mOkno = new JDialog();
		mOkno.setModalityType(ModalityType.APPLICATION_MODAL);
		mOkno.setBounds(100, 100, 697, 307);
		mOkno.getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		mOkno.getContentPane().add(contentPanel, BorderLayout.CENTER);
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
		mOkno.getContentPane().add(lvButtonPane, BorderLayout.SOUTH);

		cbWszystkie = new JCheckBox("Wszystkie");
		cbWszystkie.setSelected(true);
		cbWszystkie.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent pmE) {
				for (JCheckBox lvBox : mListaCheckboxow)
					lvBox.setSelected(cbWszystkie.isSelected());

			}
		});
		lvButtonPane.add(cbWszystkie);
		{
			JButton okButton = new JButton("OK");
			okButton.setActionCommand("OK");
			okButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent pmE) {
					for (JCheckBox lvBox : mListaCheckboxow) {
						if (lvBox.isSelected()) {
							mListaAbsencji.add(SLRodzajeAbsencji.AbsencjaPoNazwie(lvBox.getText()));
						}

					}
					mOkno.dispose();

				}
			});
			lvButtonPane.add(okButton);
			mOkno.getRootPane().setDefaultButton(okButton);
		}
		{
			JButton lvCancelButton = new JButton("Cancel");
			lvCancelButton.setActionCommand("Cancel");
			lvCancelButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent pmE) {
					mListaAbsencji = new ArrayList<SLRodzajeAbsencji>();
					mOkno.dispose();

				}
			});
			lvButtonPane.add(lvCancelButton);
		}
		mOkno.setVisible(true);
	}

	public List<SLRodzajeAbsencji> ZwrocModuly() {
		return mListaAbsencji;
	}

}
