package ProjektGlowny.commons.utils;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;

public class Interval {
	private LocalDate mDataOd;
	private LocalDate mDataDo;

	public Interval(LocalDate pmDataOd, LocalDate pmDataDo) {
		if (pmDataDo.isBefore(pmDataOd)) {
			mDataDo = pmDataOd;
			mDataOd = pmDataDo;
		} else {
			mDataDo = pmDataDo;
			mDataOd = pmDataOd;
		}
	}

	public Interval(Date pmDataOd, Date pmDataDo) {
		this(Data.LocalDateFromDate(pmDataOd), Data.LocalDateFromDate(pmDataDo));
	}

	public Interval(int pmMiesiac, int pmRok) {
		this(YearMonth.of(pmRok, pmMiesiac));
	}

	public Interval(YearMonth pmMiesiac) {
		this(pmMiesiac.atDay(1), pmMiesiac.atEndOfMonth());
	}

	public LocalDate getStart() {
		return mDataOd;
	}

	public Interval(Year pmRok) {
		this(pmRok.atMonth(1).atDay(1), pmRok.atMonth(12).atEndOfMonth());
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
		return new Interval(lvStartt, lvEndd);
	}

	public long getLiczbaDniKalendarzowych() {
		return mDataDo.toEpochDay() - mDataOd.toEpochDay() + 1;
	}

	public long getLiczbaDniRoboczych() {
		long lvLiczbaDni = 0;
		LocalDate lvDay = mDataOd;
		while (!lvDay.isAfter(mDataDo)) {
			if (!Arrays.asList(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY).contains(lvDay.getDayOfWeek()))
				lvLiczbaDni++;
			lvDay = lvDay.plusDays(1);
		}
		return lvLiczbaDni;
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
		return "Od " + mDataOd + " do " + mDataDo + "\n";
	}

	@Override
	public int hashCode() {

		int result = 17;

		result = 31 * result + mDataDo.hashCode();
		result = 31 * result + mDataOd.hashCode();

		return result;
	}

}
