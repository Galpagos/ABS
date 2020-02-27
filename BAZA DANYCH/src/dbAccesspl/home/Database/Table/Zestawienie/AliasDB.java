package dbAccesspl.home.Database.Table.Zestawienie;

public class AliasDB implements SystemTables {

	public AliasDB(String pmAlias, Class<?> pmKlasa) {
		mAlias = pmAlias;
		mKlasa = pmKlasa;
	}

	private String mAlias;
	private Class<?> mKlasa;

	@Override
	public Class<?> getKlasa() {

		return mKlasa;
	}

	@Override
	public String getTableName() {

		return mAlias;
	}

	@Override
	public String toString() {

		return mAlias;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mAlias == null) ? 0 : mAlias.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AliasDB other = (AliasDB) obj;
		if (mAlias == null) {
			if (other.mAlias != null)
				return false;
		} else if (!mAlias.equals(other.mAlias))
			return false;
		return true;
	}

}
