package ProjektGlowny.commons.Components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DatePicker {
	// define variables
	private int month = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH);
	private int year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
	// create object of JLabel with alignment
	private JLabel l = new JLabel("", JLabel.CENTER);
	// define variable
	private String day = "";
	// declaration
	private JDialog d;
	// create object of JButton
	private JButton[] button = new JButton[49];
	private String[] Miesiace = { "Stycze�", "Luty", "Marzec", "Kwiecie�", "Maj", "Czerwiec", "Lipiec", "Sierpie�",
			"Wrzesie�", "Pa�dziernik", "Listopad", "Grudzie�" };

	public DatePicker()// JFrame parent)//create constructor
	{
		// create object
		d = new JDialog();
		// set modal true

		d.setModalityType(ModalityType.APPLICATION_MODAL);
		// define string
		String[] header = { "Ndz", "Pon", "Wt", "�r", "Czw", "Pt", "Sb" };
		// create JPanel object and set layout
		JPanel p1 = new JPanel(new GridLayout(7, 7));
		// set size
		p1.setPreferredSize(new Dimension(430, 120));
		// for loop condition
		for (int x = 0; x < button.length; x++) {
			// define variable
			final int selection = x;
			// create object of JButton
			button[x] = new JButton();
			// set focus painted false
			button[x].setFocusPainted(false);
			// set background colour
			button[x].setBackground(Color.white);
			// if loop condition
			if (x > 6)
				// add action listener
				button[x].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent ae) {
						day = button[selection].getActionCommand();
						// call dispose() method
						d.dispose();
					}
				});
			if (x < 7)// if loop condition
			{
				button[x].setText(header[x]);
				// set fore ground colour
				button[x].setForeground(Color.red);
			}
			p1.add(button[x]);// add button
		}
		// create JPanel object with grid layout
		JPanel p2 = new JPanel(new GridLayout(1, 3));

		// create object of button for previous month
		JButton previous = new JButton("<< Cofnij");
		// add action command
		previous.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				// decrement month by 1
				month--;
				// call method
				d.setTitle(Miesiace[(36 + month) % 12]);
				displayDate();
			}
		});
		p2.add(previous);// add button
		p2.add(l);// add label
		// create object of button for next month
		JButton next = new JButton("Dalej >>");
		// add action command
		next.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				// increment month by 1
				month++;
				// call method
				d.setTitle(Miesiace[(36 + month) % 12]);
				displayDate();
			}
		});
		p2.add(next);// add next button
		// set border alignment
		d.add(p1, BorderLayout.CENTER);
		d.add(p2, BorderLayout.SOUTH);
		d.pack();
		// set location
		// d.setLocationRelativeTo(parent);
		// call method
		displayDate();
		// set visible true
		d.setLocationRelativeTo(previous);
		d.setTitle(Miesiace[month]);
		d.setVisible(true);
	}

	private void displayDate() {
		for (int x = 7; x < button.length; x++)// for loop
		{
			button[x].setText("");// set text
			if (x % 7 == 0 || x % 7 == 6)
				button[x].setBackground(Color.red);
		}
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
		// create object of SimpleDateFormat
		java.util.Calendar cal = java.util.Calendar.getInstance();
		// create object of java.util.Calendar
		if (month < 0) {
			year--;
			month += 12;
		}
		if (month >= 12) {
			year++;
			month -= 12;
		}
		cal.set(year, month, 1); // set year, month and date
		// define variables
		int dayOfWeek = cal.get(java.util.Calendar.DAY_OF_WEEK);
		int daysInMonth = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
		// condition
		for (int x = 6 + dayOfWeek, day = 1; day <= daysInMonth; x++, day++)// 5?
			// set text
			button[x].setText("" + day);
		l.setText(sdf.format(new Date(cal.getTimeInMillis())));
		// set title
		d.setTitle(Miesiace[(36 + month) % 12]);
	}

	public LocalDate setPickedLocalDate() {
		if (day.equals(""))
			return null;

		return LocalDate.of(year, (120 + (month)) % 12 + 1, Integer.parseInt(day));

	}
}