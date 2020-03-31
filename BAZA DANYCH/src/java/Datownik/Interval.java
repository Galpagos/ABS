package Datownik;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

public class Interval {
	private LocalDate mDataOd;
	private LocalDate mDataDo;

	public Interval(LocalDate pmDataOd, LocalDate pmDataDo) {
		mDataDo = pmDataDo;
		mDataOd = pmDataOd;
	}

	public Interval(Date pmDataOd, Date pmDataDo) {
		mDataOd = Data.LocalDateFromDate(pmDataOd);
		mDataDo = Data.LocalDateFromDate(pmDataDo);
	}

	public Interval(int pmMiesiac, int pmRok) {
		this(YearMonth.of(pmRok, pmMiesiac));
	}

	public Interval(YearMonth pmMiesiac) {
		mDataOd = pmMiesiac.atDay(1);
		mDataDo = pmMiesiac.atEndOfMonth();
	}

	public LocalDate getStart() {
		return mDataOd;
	}

	public Interval(Year pmRok) {
		mDataOd = pmRok.atMonth(1).atDay(1);
		mDataDo = pmRok.atMonth(12).atEndOfMonth();
	}

	public LocalDate getEnd() {
		return mDataDo;
	}

	public Optional<Interval> overlap(Interval pmOkres) {

		if (pmOkres == null || mDataOd.isAfter(pmOkres.getEnd()) || pmOkres.getStart().isAfter(mDataDo))
			return Optional.empty();
		LocalDate lvDataOd = mDataOd.isAfter(pmOkres.getStart()) ? mDataOd : pmOkres.getStart();
		LocalDate lvDataDo = mDataDo.isBefore(pmOkres.getEnd()) ? mDataDo : pmOkres.getEnd();
		return Optional.of(new Interval(lvDataOd, lvDataDo));
	}

	public static Interval OkreszBazy(Object pmDataOd, Object pmDataDo) {

		Timestamp lvStart = (Timestamp) pmDataOd;
		Timestamp lvEnd = (Timestamp) pmDataDo;
		LocalDate lvStartt = lvStart.toLocalDateTime().toLocalDate();
		LocalDate lvEndd = lvEnd.toLocalDateTime().toLocalDate();
		if (lvStartt.isBefore(lvEndd))
			return new Interval(lvStartt, lvEndd);
		return new Interval(lvEndd, lvStartt);
	}

	@Override
	public boolean equals(Object o) {

		// null check
		if (o == null)
			return false;
		// type check and cast
		if (getClass() != o.getClass())
			return false;
		Interval lvInterval = (Interval) o;
		// field comparison
		return Objects.equals(mDataDo, lvInterval.mDataDo) && Objects.equals(mDataOd, lvInterval.mDataOd);
	}

	@Override
	public String toString() {
		return "Od " + mDataOd + " do " + mDataDo;
	}

	@Override
	public int hashCode() {

		int result = 17;

		result = 31 * result + mDataDo.hashCode();
		result = 31 * result + mDataOd.hashCode();

		return result;

	}

}
