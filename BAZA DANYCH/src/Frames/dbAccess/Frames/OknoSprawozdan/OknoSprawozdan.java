package Frames.dbAccess.Frames.OknoSprawozdan;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class OknoSprawozdan extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JButton mbtnSprawozdanieRoczne;
	private JLabel mlabel;
	private JLabel mlabel_1;
	private JButton mbtnSprawozdanieMiesiczne;
	private ObslugaOknaSprawozdan mObsluga;

	public OknoSprawozdan() {
		setTitle("Wyb\u00F3r sprawozdania");
		mObsluga = new ObslugaOknaSprawozdan();
		setBounds(100, 100, 285, 387);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		mbtnSprawozdanieRoczne = new JButton("Sprawozdanie Roczne");
		mbtnSprawozdanieRoczne.setBounds(28, 90, 205, 42);
		mbtnSprawozdanieRoczne.addActionListener(e -> {
			mObsluga.sprawozdanieRoczne();
			dispose();
		});

		{
			mlabel = new JLabel("");
			mlabel.setBounds(216, 5, 211, 104);
		}
		{
			mlabel_1 = new JLabel("");
			mlabel_1.setBounds(5, 109, 211, 104);
		}
		{
			mbtnSprawozdanieMiesiczne = new JButton("Sprawozdanie miesi\u0119czne");
			mbtnSprawozdanieMiesiczne.addActionListener(e -> {
				mObsluga.sprawozdanieMiesieczne();
				dispose();
			});
			mbtnSprawozdanieMiesiczne.setBounds(28, 28, 205, 42);
		}
		contentPanel.setLayout(null);
		contentPanel.add(mbtnSprawozdanieRoczne);
		contentPanel.add(mlabel);
		contentPanel.add(mlabel_1);
		contentPanel.add(mbtnSprawozdanieMiesiczne);

		JButton btnRoczneUrlopy = new JButton("Sprawozdanie Roczne Urlopy");
		btnRoczneUrlopy.addActionListener(e -> {
			mObsluga.sprawozdanieRoczneUrlopy();
			dispose();
		});
		btnRoczneUrlopy.setBounds(28, 153, 205, 42);
		contentPanel.add(btnRoczneUrlopy);

		JButton btnListaObecnosci = new JButton("Lista obecnosci");
		btnListaObecnosci.addActionListener(e -> {
			mObsluga.generujListeObecnosci();
			dispose();
		});
		btnListaObecnosci.setBounds(28, 218, 205, 42);
		contentPanel.add(btnListaObecnosci);
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(e -> dispose());
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setVisible(true);
	}
}
