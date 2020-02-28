package Grupy;

public class GrupaDTO {
	private int mID;
	private String mNazwa;

	public int getID() {
		return mID;
	}

	GrupaDTO setID(int pmID) {
		mID = pmID;
		return this;
	}

	public String getNazwa() {
		return mNazwa;
	}

	public GrupaDTO setNazwa(String pmNazwa) {
		mNazwa = pmNazwa;
		return this;
	}

	@Override
	public String toString() {
		return mNazwa;
	}
}
