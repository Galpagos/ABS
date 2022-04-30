package Frames.dbAccess.Components;

import javax.swing.JFormattedTextField;

public class MyIntField extends JFormattedTextField {
	private static final long serialVersionUID = 1L;

	public MyIntField() {
		super(new Integer(0));
	}

	public void setValue(Integer pmInt) {
		if (pmInt == null)
			setText("");
		else
			super.setValue(pmInt);
	}

	public Integer getIntValue() {
		if ("".equals(getText()))
			return null;

		return (Integer) super.getValue();
	}
}
