package Parsery;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ParseryDB
{
	public static String DateParserToSQL_INSERT(Date pmDate)
	{
		SimpleDateFormat mformat = new SimpleDateFormat("yyyy-MM-dd");
		return "'" + mformat.format(pmDate) + " 00:00:00'";
	}

	public static String DateParserToSQL_SELECT(Date pmDate)
	{
		SimpleDateFormat mformat = new SimpleDateFormat("yyyy-MM-dd");
		return "#" + mformat.format(pmDate) + "#";
	}

	public static String DateParserToMsg(Date pmDate)
	{
		SimpleDateFormat mformat = new SimpleDateFormat("yyyy-MM-dd");
		return mformat.format(pmDate);
	}

	public static LocalDate DateParseToLocalDate(Date pmData)
	{
		return pmData.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	public static String DateParserToSQL_SELECT(LocalDate pmDate)
	{
		// SimpleDateFormat mformat = new SimpleDateFormat("yyyy-MM-dd");
		return "#" + pmDate + "#";
	}
}
