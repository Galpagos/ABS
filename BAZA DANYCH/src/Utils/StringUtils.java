package Utils;

import java.text.Collator;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class StringUtils
{
	public static String getKwotaDoDwochMiejscZKropka(double pmKwota)
	{
		return "" + NumberUtils.round(pmKwota, 2);
	}

	public static List<String> sortujPL(List<String> pmLista)
	{
		Collections.sort(pmLista, Collator.getInstance(new Locale("pl", "PL")));
		return pmLista;
	}
}
