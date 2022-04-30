package Utils;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class GridUtils {
	public static GridBagConstraints gridCons(int pmX, int pmY) {
		GridBagConstraints lvGridCons = new GridBagConstraints();
		lvGridCons.gridx = pmX;
		lvGridCons.gridy = pmY;
		lvGridCons.fill = GridBagConstraints.HORIZONTAL;
		lvGridCons.ipady = 10;
		lvGridCons.insets = new Insets(5, 5, 5, 5);
		return lvGridCons;
	}
}
