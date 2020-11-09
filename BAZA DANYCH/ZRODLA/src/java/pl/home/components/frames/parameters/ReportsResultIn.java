package pl.home.components.frames.parameters;

import ProjektGlowny.commons.Frames.ParametryWejscia;

import java.util.Map;

import java.text.MessageFormat;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import Wydruki.SprawozdanieMiesieczne.wynikWResultTableWindow;
import enums.RodzajWydruku;
import lombok.Builder;
import lombok.Getter;
@Builder
@Getter
public class ReportsResultIn implements ParametryWejscia {
	private DefaultTableModel mModel;
	private wynikWResultTableWindow mDane;
	private Map<Class<?>, TableCellRenderer> mListaRenderow;
	private String mTytul;
	private RodzajWydruku mRodzajWydruku;
	private Integer mWysokoscWiersza;
	private MessageFormat mHeader;
	private MessageFormat mTextFinal;

}
