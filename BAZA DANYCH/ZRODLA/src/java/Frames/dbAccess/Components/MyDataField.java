package Frames.dbAccess.Components;

import java.time.LocalDate;
import java.util.Date;

import javax.swing.JFormattedTextField;

import ProjektGlowny.commons.utils.Data;

public class MyDataField extends JFormattedTextField {

	private static final long serialVersionUID = 1L;

	public MyDataField() {
		super(new Date());
	}

	public void setValue(LocalDate pmData) {
		super.setValue(Data.DateFromLocalDate(pmData));
	}

	public LocalDate getDateValue() {
		return Data.LocalDateFromDate((Date) super.getValue());
	}
}
