package pl.home.components.frames.src;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ProjektGlowny.commons.Frames.AbstractOkno;
import pl.home.components.frames.parameters.OSprawozdanWejscie;
import pl.home.components.frames.parameters.OSprawozdanWyjscie;

public abstract class SrcOknoSprawozdan extends AbstractOkno<OSprawozdanWejscie, OSprawozdanWyjscie> {
	private static final long serialVersionUID = 1L;
	private JPanel contentPanel;
	private JButton mbtnSprawozdanieRoczne;
	private JLabel mlabel;
	private JLabel mlabel_1;
	private JButton mbtnSprawozdanieMiesiczne;
	private JButton btnRoczneUrlopy;
	private JButton btnListaObecnosci;
	private JButton btnListaPlac;

	public SrcOknoSprawozdan(OSprawozdanWejscie pmParams) {
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
		btnListaPlac.addActionListener(e -> wywolajListePlac());

	}

	protected abstract void wywolajListePlac();

	protected abstract void wywolajListeObecnosci();

	protected abstract void wywolajSprawozdanieRoczneUrlopy();

	protected abstract void wywolajSprawozdanieMiesieczne();

	protected abstract void wywolajSprawozdanieRoczne();

	@Override
	protected void odswiezKontrolki() {
	}

	@Override
	protected void budujOkno() {
		contentPanel = new JPanel();
		setTitle("Wyb\u00F3r sprawozdania");
		setBounds(100, 100, 285, 487);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		mbtnSprawozdanieRoczne = new JButton("Sprawozdanie Roczne");
		mbtnSprawozdanieRoczne.setBounds(28, 90, 205, 42);
		mlabel = new JLabel("");
		mlabel.setBounds(216, 5, 211, 104);

		mlabel_1 = new JLabel("");
		mlabel_1.setBounds(5, 109, 211, 104);

		mbtnSprawozdanieMiesiczne = new JButton("Sprawozdanie miesięczne");
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

		btnListaPlac = new JButton("Lista płac");
		btnListaPlac.setBounds(28, 283, 205, 42);
		contentPanel.add(btnListaPlac);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));

		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		buttonPane.add(mcancelButton);
	}

	@Override
	protected void beforeClose() {
	}

	@Override
	protected OSprawozdanWyjscie budujWyjscie() {

		return null;
	}

}
