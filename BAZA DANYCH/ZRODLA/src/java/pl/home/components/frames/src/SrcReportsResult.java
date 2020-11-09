package pl.home.components.frames.src;

import ProjektGlowny.commons.Frames.AbstractOkno;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.Printable;
import java.text.MessageFormat;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import Frames.dbAccess.Components.TablePrintable;
import Pracownik.ObslugaPracownka;
import Wydruki.ListaObecnosci.CellRenderListyObecnosci;
import Wydruki.ListaObecnosci.CellRenderPustePole;
import Wydruki.PrzygotowanieDanych.AbsencjaDTO;
import Wydruki.PrzygotowanieDanych.PracownikDTO;
import Wydruki.PrzygotowanieDanych.PustePole;
import Wydruki.SprawozdanieMiesieczne.SprawozdanieMiesieczneCellRender;
import Wydruki.SprawozdanieRoczne.SprawozdanieRoczneCellRender;
import enums.RodzajWydruku;
import pl.home.components.frames.parameters.ReportsResultIn;
import pl.home.components.frames.parameters.ReportsResultOut;

public abstract class SrcReportsResult extends AbstractOkno<ReportsResultIn, ReportsResultOut> {

	private static final long serialVersionUID = 6556227623041313267L;
	protected JPanel contentPanel;
	protected JTable mtable;
	private MessageFormat mheader = new MessageFormat("Sprawozdanie");
	private MessageFormat mFinal = new MessageFormat("");

	public SrcReportsResult(ReportsResultIn pmParams) {
		super(pmParams);

	}

	@Override
	protected void onOpen() {
		setTitle(mParamsIn.getTytul());
		ustawTabele(mParamsIn.getModel());
		ustawFormatowanieWydruku(mParamsIn.getRodzajWydruku());
	}

	private void ustawFormatowanieWydruku(RodzajWydruku pmRodzajWydruku) {
		switch (pmRodzajWydruku) {
			case LISTA_OBECNOSCI :
				setingsListaObecnosci();
				break;
			case SPR_MIESIECZNE :
				settingsSprMiesieczne();
				break;
			case LISTA_PLAC :
				settingListPlac();
				break;
			case SPR_ROCZNE :
				settingSprRoczne();
				break;
		}

	}

	private void settingSprRoczne() {
		mtable.setDefaultRenderer(Object.class, new SprawozdanieRoczneCellRender());
		mtable.setDefaultRenderer(PustePole.class, new CellRenderPustePole());

	}

	private void settingListPlac() {
		mtable.setDefaultRenderer(Object.class, new SprawozdanieRoczneCellRender());
		mtable.setDefaultRenderer(PustePole.class, new CellRenderPustePole());
	}

	private void settingsSprMiesieczne() {
		mtable.setDefaultRenderer(Object.class, new SprawozdanieMiesieczneCellRender());
		mtable.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent pmE) {
				JTable target = (JTable) pmE.getSource();
				int row = target.getSelectedRow();
				int column = target.getSelectedColumn();

				if ((target.getValueAt(row, column) != null) && target.getValueAt(row, column).getClass() == PracownikDTO.class) {
					PracownikDTO lvPracownik = (PracownikDTO) target.getValueAt(row, column);
					new ObslugaPracownka().pokazPracownika(lvPracownik);
					int k = target.getModel().getColumnCount();
					Object[] lvPrzeliczonyWiersz = mParamsIn.getDane().przeliczWierszTabeli(lvPracownik);
					for (int i = 0; i < k; i++) {
						target.getModel().setValueAt(lvPrzeliczonyWiersz[i], row, i);
					}
				}
				if ((target.getValueAt(row, column) != null) && target.getValueAt(row, column).getClass() == AbsencjaDTO.class) {
					PracownikDTO lvPracownik = (PracownikDTO) target.getValueAt(row, 0);
					new ObslugaPracownka().pokazPracownika(lvPracownik);
					int k = target.getModel().getColumnCount();
					Object[] lvPrzeliczonyWiersz = mParamsIn.getDane().przeliczWierszTabeli(lvPracownik);
					for (int i = 0; i < k; i++) {
						target.getModel().setValueAt(lvPrzeliczonyWiersz[i], row, i);
					}
					repaint();
				}
			}
		});
	}

	private void setingsListaObecnosci() {
		mtable.setShowGrid(false);
		mtable.setIntercellSpacing(new Dimension(0, 0));
		ustawTabele(mParamsIn.getModel());
		mtable.setBorder(BorderFactory.createEmptyBorder());
		mtable.setRowHeight(Integer.valueOf(mParamsIn.getWysokoscWiersza()));
		mtable.setDefaultRenderer(Object.class, new CellRenderListyObecnosci());
		JTableHeader header = mtable.getTableHeader();
		header.setDefaultRenderer(new CellRenderListyObecnosci());
		mtable.setRowSelectionAllowed(false);

		mtable.getColumnModel().getColumn(3).setPreferredWidth(350);
		mtable.getColumnModel().getColumn(8).setPreferredWidth(350);
		mtable.getColumnModel().getColumn(0).setPreferredWidth(200);
		mtable.getColumnModel().getColumn(5).setPreferredWidth(200);
		mtable.getColumnModel().getColumn(4).setPreferredWidth(250);
	}

	@Override
	protected void przypiszMetody() {
	}

	@Override
	protected void odswiezKontrolki() {
	}

	@Override
	protected void budujOkno() {
		contentPanel = (JPanel) getContentPane();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(20, 20, 5, 20));

		{
			JScrollPane scrollPane = new JScrollPane();

			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				mtable = new JTable() {
					private static final long serialVersionUID = 6154375319291840115L;

					@Override
					public Printable getPrintable(PrintMode printMode, MessageFormat headerFormat, MessageFormat footerFormat) {
						return new TablePrintable(this, printMode, headerFormat, footerFormat);
					}
				};

				scrollPane.setViewportView(mtable);
			}
		}

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(new Rectangle(0, 0, 300, 50));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{

				JButton btnPrint = new JButton("Drukuj");
				btnPrint.setAlignmentX(Component.RIGHT_ALIGNMENT);

				btnPrint.addActionListener(e -> {
					setAlwaysOnTop(false);
					try {
						PrintRequestAttributeSet lvWydruk = new HashPrintRequestAttributeSet();
						lvWydruk.add(new MediaPrintableArea(10f, 10f, 190f, 277f, MediaPrintableArea.MM));
						lvWydruk.add(OrientationRequested.LANDSCAPE);

						mtable.print(JTable.PrintMode.FIT_WIDTH, mheader, mFinal, true, lvWydruk, false);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				});
				buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

				buttonPane.add(btnPrint);

				buttonPane.add(mokButton);
				getRootPane().setDefaultButton(mokButton);
			}

			buttonPane.add(mcancelButton);

		}

		pack();
		setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
	}

	public void ustawTabele(DefaultTableModel pmTab) {
		mtable.setModel(pmTab);
		mtable.getColumnModel().getColumn(0).setMinWidth(200);
		mtable.setCellSelectionEnabled(false);
		mtable.setColumnSelectionAllowed(true);
		mtable.setRowSelectionAllowed(true);

	}

	public void pokazWynik() {
		try {
			setVisible(true);
			setAlwaysOnTop(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected ReportsResultOut budujWyjscie() {
		return null;
	}

}
