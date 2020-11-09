package Frames.dbAccess.Components;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.Printable;
import java.text.MessageFormat;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Wydruki.SprawozdanieMiesieczne.wynikWResultTableWindow;

@SuppressWarnings("serial")
public class ResultTableWindow extends JDialog {

	private final JPanel contentPanel;
	private JTable mtable;
	private wynikWResultTableWindow mDane;
	private MessageFormat mheader = new MessageFormat("Sprawozdanie");
	private MessageFormat mFinal = new MessageFormat("");

	public MessageFormat getFinal() {
		return mFinal;
	}

	public void setFinal(MessageFormat pmFinal) {
		mFinal = pmFinal;
	}

	public wynikWResultTableWindow getDane() {
		return mDane;
	}

	public MessageFormat getHeader() {
		return mheader;
	}

	public void setHeader(MessageFormat pmMheader) {
		mheader = pmMheader;
	}

	public void setDane(wynikWResultTableWindow pmDane) {
		mDane = pmDane;
	}

	public ResultTableWindow() {
		contentPanel = (JPanel) getContentPane();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(20, 20, 5, 20));

		{
			JScrollPane scrollPane = new JScrollPane();

			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				mtable = new javax.swing.JTable() {
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
				JButton okButton = new JButton("OK");
				okButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
				okButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
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
				}
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent pmE) {
						dispose();

					}

				});
				buttonPane.add(cancelButton);
			}
		}

		pack();
		GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(this);
		// setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		// setExtendedState(JFrame.MAXIMIZED_BOTH);
		pack();
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

	public void setTytul(String pmTytul) {
		setTitle(pmTytul);
	}

	public JTable getMtable() {
		return mtable;
	}

	public void setMtable(JTable pmMtable) {
		mtable = pmMtable;
	}

}
