package Utils;

public class DateUtils
{
	public static String PobierzMiesiacJakoDwucyfrowy(int pmMiesiac)
	{
		if (pmMiesiac >= 10)
			return "" + pmMiesiac;
		return "0" + pmMiesiac;
	}
}
