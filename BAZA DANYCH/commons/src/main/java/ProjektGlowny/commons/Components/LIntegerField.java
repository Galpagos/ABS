package ProjektGlowny.commons.Components;

import javax.swing.JTextField;
import javax.swing.text.PlainDocument;

public class LIntegerField extends JTextField {

	private static final long serialVersionUID = 1L;

	public LIntegerField(String pmDefault) {
		super(pmDefault);
		if (pmDefault == null)
			setText("0");
		PlainDocument lvDoc = (PlainDocument) this.getDocument();
		lvDoc.setDocumentFilter(new MyIntFilter());
	}
}
