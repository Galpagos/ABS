package ProjektGlowny.commons.Components;

import javax.swing.JTextField;
import javax.swing.text.PlainDocument;

public class LDoubleFilter extends JTextField {

	private static final long serialVersionUID = 1L;

	public LDoubleFilter(String pmDefault) {
		super(pmDefault);
		if (pmDefault == null)
			setText("0");
		PlainDocument lvDoc = (PlainDocument) this.getDocument();
		lvDoc.setDocumentFilter(new MyDoubleFilter());
	}
}
