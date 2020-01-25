package Frames.dbAccess.Frames.OknoSprawozdan;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Frames.dbAccess.Components.AbstractOkno;
import Frames.dbAccess.Components.ScriptParams;

public abstract class SrcOknoSprawozdan extends AbstractOkno {
	private static final long serialVersionUID = 1L;
	private JPanel contentPanel;
	private JButton mbtnSprawozdanieRoczne;
	private JLabel mlabel;
	private JLabel mlabel_1;
	private JButton mbtnSprawozdanieMiesiczne;
	private JButton btnRoczneUrlopy;
	private JButton btnListaObecnosci;
	private JButton cancelButton;

	public SrcOknoSprawozdan(ScriptParams pmParams) {
		super(pmParams);
	}

	@Override
	protected void readParams() {

	}

	@Override
	protected void onOpen() {

	}

	@Override
	protected void przypiszMetody() {
		mbtnSprawozdanieRoczne.addActionListener(e -> wywolajSprawozdanieRoczne());
		mbtnSprawozdanieMiesiczne.addActionListener(e -> wywolajSprawozdanieMiesieczne());
		btnRoczneUrlopy.addActionListener(e -> wywolajSprawozdanieRoczneUrlopy());
		btnListaObecnosci.addActionListener(e -> wywolajListeObecnosci());
		cancelButton.addActionListener(e -> dispose());

	}

	protected abstract void wywolajListeObecnosci();

	protected abstract void wywolajSprawozdanieRoczneUrlopy();

	abstract void wywolajSprawozdanieMiesieczne();

	abstract void wywolajSprawozdanieRoczne();

	@Override
	protected void odswiezKontrolki() {
	}

	@Override
	protected void budujOkno() {
		contentPanel = new JPanel();
		setTitle("Wyb\u00F3r sprawozdania");
		setBounds(100, 100, 285, 387);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		mbtnSprawozdanieRoczne = new JButton("Sprawozdanie Roczne");
		mbtnSprawozdanieRoczne.setBounds(28, 90, 205, 42);
		mlabel = new JLabel("");
		mlabel.setBounds(216, 5, 211, 104);

		mlabel_1 = new JLabel("");
		mlabel_1.setBounds(5, 109, 211, 104);

		mbtnSprawozdanieMiesiczne = new JButton("Sprawozdanie miesi\u0119czne");
		mbtnSprawozdanieMiesiczne.setBounds(28, 28, 205, 42);
		contentPanel.setLayout(null);
		contentPanel.add(mbtnSprawozdanieRoczne);
		contentPanel.add(mlabel);
		contentPanel.add(mlabel_1);
		contentPanel.add(mbtnSprawozdanieMiesiczne);

		btnRoczneUrlopy = new JButton("Sprawozdanie Roczne Urlopy");
		btnRoczneUrlopy.setBounds(28, 153, 205, 42);
		contentPanel.add(btnRoczneUrlopy);

		btnListaObecnosci = new JButton("Lista obecnosci");
		btnListaObecnosci.setBounds(28, 218, 205, 42);
		contentPanel.add(btnListaObecnosci);
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));

		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
	}
}
