package Frames.dbAccess.Components;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ComboPicker {
	ObsluzenieComboPicker mObsluga;

	public ObsluzenieComboPicker getObsluga() {
		return mObsluga;
	}

	public void setObsluga(ObsluzenieComboPicker pmObsluga) {
		mObsluga = pmObsluga;
	}

	private JComboBox<Object> cbGrupy;
	private final JPanel contentPanel = new JPanel();
	private JButton btnNowy;
	private JDialog d;
	JButton btnUsu;

	public JButton getBtnUsu() {
		return btnUsu;
	}

	public void setBtnUsu(JButton pmBtnUsu) {
		btnUsu = pmBtnUsu;
	}

	public JButton getBtnNowy() {
		return btnNowy;
	}

	public void setBtnNowy(JButton pmBtnNowy) {
		btnNowy = pmBtnNowy;
	}

	public JComboBox<Object> getCbGrupy() {
		return cbGrupy;
	}

	public void setCbGrupy(JComboBox<Object> pmCbGrupy) {
		cbGrupy = pmCbGrupy;
	}

	public ComboPicker() {
		d = new JDialog();
		d.setModalityType(ModalityType.APPLICATION_MODAL);
		d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		d.setBounds(100, 100, 450, 300);
		d.getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		d.getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			cbGrupy = new JComboBox<Object>();
			cbGrupy.setBounds(40, 92, 196, 30);
			contentPanel.add(cbGrupy);
		}
		{
			btnNowy = new JButton("Nowy...");
			btnNowy.setBounds(259, 92, 120, 30);
			btnNowy.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent pmArg0) {
					mObsluga.dodaj();
					ustawCombo();
					cbGrupy.setSelectedIndex(cbGrupy.getItemCount() - 1);

				}
			});
			contentPanel.add(btnNowy);
		}

		btnUsu = new JButton("Usu\u0144...");
		btnUsu.setBounds(259, 135, 120, 30);
		contentPanel.add(btnUsu);
		btnUsu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent pmE) {
				mObsluga.usun(cbGrupy.getSelectedItem());
				ustawCombo();

			}
		});

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		d.getContentPane().add(buttonPane, BorderLayout.SOUTH);
		{
			JButton okButton = new JButton("OK");
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					d.dispose();
				}
			});
			okButton.setActionCommand("OK");
			buttonPane.add(okButton);
			d.getRootPane().setDefaultButton(okButton);
		}
		{
			JButton cancelButton = new JButton("Cancel");
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					cbGrupy.setSelectedItem(null);
					d.dispose();
				}
			});
			cancelButton.setActionCommand("Cancel");
			buttonPane.add(cancelButton);
			d.addWindowListener(new WindowListener() {

				@Override
				public void windowOpened(WindowEvent pmE) {
					// TODO Auto-generated method stub

				}

				@Override
				public void windowIconified(WindowEvent pmE) {
					// TODO Auto-generated method stub

				}

				@Override
				public void windowDeiconified(WindowEvent pmE) {
					// TODO Auto-generated method stub

				}

				@Override
				public void windowDeactivated(WindowEvent pmE) {
					// TODO Auto-generated method stub

				}

				@Override
				public void windowClosing(WindowEvent pmE) {
					cbGrupy.setSelectedItem(null);

				}

				@Override
				public void windowClosed(WindowEvent pmE) {
					// TODO Auto-generated method stub

				}

				@Override
				public void windowActivated(WindowEvent pmE) {
					// TODO Auto-generated method stub

				}
			});
		}

	}

	public void ustawCombo() {
		ComboBoxModel<Object> mModel = mObsluga.getComboBoxModel();
		cbGrupy.setModel(mModel);

	}

	public Object ustawGrupy() {
		ustawCombo();
		d.setVisible(true);
		return cbGrupy.getSelectedItem();
	}
}
