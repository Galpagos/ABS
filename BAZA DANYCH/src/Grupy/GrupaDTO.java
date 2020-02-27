package Grupy;

public class GrupaDTO {
	int mID;
	String mNazwa;

	public int getID() {
		return mID;
	}

	public GrupaDTO setID(int pmID) {
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
