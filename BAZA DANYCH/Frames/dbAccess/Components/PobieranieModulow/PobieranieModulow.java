package dbAccess.Components.PobieranieModulow;

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

public class PobieranieModulow
{
	JDialog mOkno;
	private final JPanel contentPanel = new JPanel();
	List<SLRodzajeAbsencji> mLista;
	List<JCheckBox> lvListaCheckboxow;
	JCheckBox chckbxWszystkie;

	public PobieranieModulow()
	{
		mLista = new ArrayList<SLRodzajeAbsencji>();
		mOkno = new JDialog();
		mOkno.setModalityType(ModalityType.APPLICATION_MODAL);
		mOkno.setBounds(100, 100, 597, 307);
		mOkno.getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		mOkno.getContentPane().add(contentPanel, BorderLayout.CENTER);
		lvListaCheckboxow = new ArrayList<JCheckBox>();
		contentPanel.setLayout(new GridLayout(0, 3));
		for (SLRodzajeAbsencji lvAbs : SLRodzajeAbsencji.values())
		{
			JCheckBox lvBox = new JCheckBox(lvAbs.toString());
			lvBox.setSelected(true);
			lvListaCheckboxow.add(lvBox);
			contentPanel.add(lvBox);
		}
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		mOkno.getContentPane().add(buttonPane, BorderLayout.SOUTH);

		chckbxWszystkie = new JCheckBox("Wszystkie");
		chckbxWszystkie.setSelected(true);
		chckbxWszystkie.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent pmE)
			{
				for (JCheckBox lvBox : lvListaCheckboxow)
					lvBox.setSelected(chckbxWszystkie.isSelected());

			}
		});
		buttonPane.add(chckbxWszystkie);
		{
			JButton okButton = new JButton("OK");
			okButton.setActionCommand("OK");
			okButton.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent pmE)
				{
					for (JCheckBox lvBox : lvListaCheckboxow)
					{
						if (lvBox.isSelected())
						{
							mLista.add(SLRodzajeAbsencji.AbsencjaPoNazwie(lvBox.getText()));
						}

					}
					mOkno.dispose();

				}
			});
			buttonPane.add(okButton);
			mOkno.getRootPane().setDefaultButton(okButton);
		}
		{
			JButton cancelButton = new JButton("Cancel");
			cancelButton.setActionCommand("Cancel");
			cancelButton.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent pmE)
				{
					mLista = new ArrayList<SLRodzajeAbsencji>();
					mOkno.dispose();

				}
			});
			buttonPane.add(cancelButton);
		}
		mOkno.setVisible(true);
	}

	public List<SLRodzajeAbsencji> ZwrocModuly()
	{
		return mLista;
	}

}
