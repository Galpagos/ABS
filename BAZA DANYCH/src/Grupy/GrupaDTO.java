package Grupy;

public class GrupaDTO
{
	int mID;
	String mNazwa;

	public int getID()
	{
		return mID;
	}

	public void setID(int pmID)
	{
		mID = pmID;
	}

	public String getNazwa()
	{
		return mNazwa;
	}

	public void setNazwa(String pmNazwa)
	{
		mNazwa = pmNazwa;
	}

	public String toString()
	{
		return mNazwa;
	}
}
