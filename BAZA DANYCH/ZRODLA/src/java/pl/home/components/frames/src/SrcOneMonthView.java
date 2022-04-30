package pl.home.components.frames.src;

import ProjektGlowny.commons.Components.DatePicker;
import ProjektGlowny.commons.Components.LTable;
import ProjektGlowny.commons.DbBuilder.LRecord;
import ProjektGlowny.commons.DbBuilder.LRecordSet;
import ProjektGlowny.commons.DbBuilder.QueryBuilder;
import ProjektGlowny.commons.DbBuilder.SystemTables;
import ProjektGlowny.commons.Frames.AbstractOkno;
import ProjektGlowny.commons.enums.InterfejsSlownika;
import ProjektGlowny.commons.parsers.ParseryDB;
import ProjektGlowny.commons.utils.Interval;

import java.util.List;
import java.util.function.Function;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;

import Datownik.LicznikDaty;
import Wydruki.PrzygotowanieDanych.AbsencjaDTO;
import dbAccesspl.home.Database.Table.Zestawienie.AbsencjeColumns;
import dbAccesspl.home.Database.Table.Zestawienie.SobotaRoboczaColumns;
import dbAccesspl.home.Database.Table.Zestawienie.SystemTablesNames;
import dbAccesspl.home.Database.Table.Zestawienie.ZestawienieColumns;
import enums.InformacjeUniwersalne;
import enums.SLRodzajeAbsencji;
import pl.home.absencje.AbsencjeRepositoryDB;
import pl.home.components.frames.parameters.OneDayViewIn;
import pl.home.components.frames.parameters.OneDayViewOut;

public class SrcOneMonthView extends AbstractOkno<OneDayViewIn, OneDayViewOut> {
	private static final long serialVersionUID = 1L;
	private JPanel mContentPane;
	private JPanel mTop;
	private JLabel lblData;
	private LTable mTable;
	private JScrollPane mScrollPane;
	private LocalDate mDataOd;
	private LocalDate mDataDo;
	private JLabel lblData2;
	private JButton btnPck1;
	private JButton btnPck2;
	private JCheckBox cbox;

	public SrcOneMonthView(OneDayViewIn pmParams) {
		super(pmParams);

	}

	@Override
	protected void readParams() {
		LocalDate lvData = LocalDate.now().minusMonths(1);
		mDataOd = lvData.withDayOfMonth(1);
		mDataDo = lvData.withDayOfMonth(lvData.lengthOfMonth());
	}

	@Override
	protected void budujOkno() {

		initOkno();
		initTop();
		initCenter();
		initBottomPanel();
		odswiezOkno();
	}

	private void initOkno() {
		setModal(true);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Weryfikacja premii");
		mContentPane = new JPanel(new BorderLayout());
		mContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mContentPane);
		mContentPane.add(new JPanel(), BorderLayout.CENTER);
	}

	private void initTop() {
		mTop = new JPanel(new BorderLayout());
		mTop.setBorder(new EmptyBorder(5, 5, 10, 5));
		mTop.setPreferredSize(new Dimension(this.getSize().width, 70));
		JPanel mMiddleTop = new JPanel(new GridBagLayout());
		lblData = new JLabel("");
		lblData2 = new JLabel("");
		lblData.setFont(new Font("Serif", Font.BOLD, 35));
		lblData2.setFont(new Font("Serif", Font.BOLD, 35));
		btnPck1 = new JButton("Od:");
		btnPck1.addActionListener(e -> {
			mDataOd = new DatePicker().setPickedLocalDate();
			odswiezOkno();
		});

		btnPck2 = new JButton("Do:");
		btnPck2.addActionListener(e -> {
			mDataDo = new DatePicker().setPickedLocalDate();
			odswiezOkno();
		});
		mTop.add(mMiddleTop, BorderLayout.CENTER);
		mMiddleTop.add(btnPck1);
		mMiddleTop.add(lblData);
		mMiddleTop.add(btnPck2);
		mMiddleTop.add(lblData2);

		mContentPane.add(mTop, BorderLayout.PAGE_START);
	}

	private void odswiezOkno() {
		lblData.setText(mDataOd != null ? mDataOd.toString() : "");
		lblData2.setText(mDataDo != null ? mDataDo.toString() : "");
		mTable.reload(getDane());

	}

	private void initCenter() {
		mTable = createTable();
		mScrollPane = new JScrollPane(mTable);
		mScrollPane.setAutoscrolls(true);
		mScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		mContentPane.add(mScrollPane, BorderLayout.CENTER);
	}

	private LTable createTable() {

		LTable lvTabela = new LTable(getDane());

		lvTabela.odswiez();

		lvTabela.repaint();
		lvTabela.reload(getDane());

		lvTabela.hideColumn(AbsencjeColumns.ID_tabeli);
		return lvTabela;
	}

	private LRecordSet getDane() {

		LRecordSet lvQuery = (cbox != null && cbox.isSelected())
				? QueryBuilder.SELECT()//
						.select(ZestawienieColumns.ID_tabeli, ZestawienieColumns.Pracownik)//
						.andWarunek(ZestawienieColumns.Data_Zwolnienia, null)//
						.andWarunek(" exists (select 1 from "//
								+ SystemTablesNames.ABSENCJE//
								+ " where " + " " + AbsencjeColumns.ID_pracownika + "=Zestawienie." + ZestawienieColumns.ID_tabeli//
								+ " and " + AbsencjeColumns.Do_kiedy + ">=" + parsujDate(mDataOd)//
								+ " and " + AbsencjeColumns.Od_kiedy + "<=" + parsujDate(mDataDo)//
								+ " and " + AbsencjeColumns.RODZAJ + " =" + SLRodzajeAbsencji.urlop_w_pracy.getKod() //
								+ ")")
						.execute()
				: QueryBuilder.SELECT()//
						.select(ZestawienieColumns.ID_tabeli, ZestawienieColumns.Pracownik)//
						.andWarunek(ZestawienieColumns.Data_Zwolnienia, null)//
						.execute();
		LRecordSet lvRecSet = new LRecordSet();
		for (LRecord lvR : lvQuery) {

			List<AbsencjaDTO> lvDane = new AbsencjeRepositoryDB().getWorkerAbsenceInTerms(lvR.getAsInteger(ZestawienieColumns.ID_tabeli),
					new Interval(mDataOd, mDataDo));
			int lvDni = 0;
			int lvDniBezUW = 0;
			String lvOpis = "";
			lvDane.sort((a, b) -> a.getOkres().getStart().compareTo(b.getOkres().getStart()));
			for (AbsencjaDTO lvabsencja : lvDane) {
				if (!SLRodzajeAbsencji.urlop_w_pracy.equals(lvabsencja.getRodzaj()))
					lvDniBezUW += lvabsencja.getOkres().overlap(new Interval(mDataOd, mDataDo))//
							.map(Interval::getLiczbaDniRoboczych).orElse(0l);
				else {
					lvOpis += lvabsencja.getOkres().overlap(new Interval(mDataOd, mDataDo)).map(this::toOpis).orElse("");
					lvDni += lvabsencja.getOkres().overlap(new Interval(mDataOd, mDataDo))//
							.map(Interval::getLiczbaDniRoboczych).orElse(0l);
				}
			}

			LRecordSet lvDaneSobot = QueryBuilder.SELECT()//
					.select(SobotaRoboczaColumns.DATA, SobotaRoboczaColumns.GODZINY)//
					.andWarunek(SobotaRoboczaColumns.ID_pracownika, lvR.getAsInteger(ZestawienieColumns.ID_tabeli))//
					.andWarunek(SobotaRoboczaColumns.DATA + ">=" + parsujDate(mDataOd)//
							+ " and " + SobotaRoboczaColumns.DATA + "<=" + parsujDate(mDataDo))//
					.execute();

			String lvSobotyString = "";
			int lvSobotyInt = 0;
			for (LRecord lvSobota : lvDaneSobot) {
				lvSobotyInt++;
				lvSobotyString += lvSobota.getAsLocalDate(SobotaRoboczaColumns.DATA).getDayOfMonth() + " (" + lvSobota.getAsString(SobotaRoboczaColumns.GODZINY)
						+ "h),";
			}
			if (lvSobotyString.length() > 1)
				lvSobotyString = lvSobotyString.substring(0, lvSobotyString.length() - 1);

			int lvMonthLenght = LicznikDaty.ileDniRobotnych(YearMonth.from(mDataOd));

			LRecord lvRec = new LRecord();
			lvRec.put(DaneTabelki.ID_tabeli, 5);
			lvRec.put(DaneTabelki.Pracownik, lvR.getAsString(ZestawienieColumns.Pracownik));
			lvRec.put(DaneTabelki.Dni, lvDni);
			lvRec.put(DaneTabelki.Okres, lvOpis);
			lvRec.put(DaneTabelki.Przepracowane, lvMonthLenght - lvDniBezUW);
			BigDecimal lvFreq = new BigDecimal("100")//
					.multiply(new BigDecimal(lvMonthLenght).subtract(new BigDecimal(lvDniBezUW)))
					.divide(new BigDecimal(lvMonthLenght), 0, RoundingMode.HALF_UP);
			lvRec.put(DaneTabelki.Frek, lvFreq.toPlainString() + "%");
			lvRec.put(DaneTabelki.Soboty, lvSobotyString);
			String lvPremia = (lvDniBezUW == 0) ? "100" : "0";
			lvRec.put(DaneTabelki.PremiaSob, 50 * lvSobotyInt);
			lvRec.put(DaneTabelki.PremiaUW, lvDni * 50);
			lvRec.put(DaneTabelki.PremiaFrek, lvPremia);
			lvRec.put(DaneTabelki.Premia, Double.valueOf(lvPremia) + (lvDni * 50) + (50 * lvSobotyInt));
			lvRecSet.add(lvRec);
		}

		return lvRecSet;
	}

	private String toOpis(Interval pmInterval) {
		return " " + dateToString(pmInterval.getStart()) + " - " + dateToString(pmInterval.getEnd()) + "       ";
	}

	private String dateToString(LocalDate pmDate) {
		return pmDate.getDayOfMonth() + "." + monthToString(pmDate.getMonthValue());
	}

	private String monthToString(int pmInt) {
		if (pmInt < 10)
			return "0" + pmInt;
		return pmInt + "";
	}

	private String parsujDate(LocalDate pmData) {
		return ParseryDB.DateParserToSQL_SELECT(pmData);
	}

	private void initBottomPanel() {
		cbox = new JCheckBox("tylko ulrop w pracy");
		cbox.addActionListener(e -> odswiezOkno());

		JButton lvSave = new JButton("Export");
		lvSave.addActionListener(e -> {
			JFileChooser fchoose = new JFileChooser();
			int option = fchoose.showSaveDialog(this);
			if (option == JFileChooser.APPROVE_OPTION) {
				String name = fchoose.getSelectedFile().getName();
				String path = fchoose.getSelectedFile().getParentFile().getPath();
				String file = path + "\\" + name + ".xls";
				export(mTable, new File(file));
			}
		});
		JPanel mBottomPanel = new JPanel(new GridLayout(1, 6, 10, 10));
		mBottomPanel.setBorder(new EmptyBorder(10, 5, 5, 5));
		mBottomPanel.add(cbox);
		mBottomPanel.add(lvSave);
		mBottomPanel.add(new JPanel());
		mBottomPanel.add(new JPanel());
		mBottomPanel.add(mokButton);
		mBottomPanel.add(mcancelButton);

		mContentPane.add(mBottomPanel, BorderLayout.PAGE_END);

	}

	public void export(JTable table, File file) {
		try {
			TableModel m = table.getModel();
			FileWriter fw = new FileWriter(file);
			for (int i = 0; i < m.getColumnCount(); i++) {
				fw.write(m.getColumnName(i) + "\t");
			}
			fw.write("\n");
			for (int i = 0; i < m.getRowCount(); i++) {
				for (int j = 0; j < m.getColumnCount(); j++) {
					fw.write(m.getValueAt(i, j).toString() + "\t");
				}
				fw.write("\n");
			}
			fw.close();
			info(InformacjeUniwersalne.OPERACJA_UDANA);
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	@Override
	protected OneDayViewOut budujWyjscie() {
		return null;
	}

	@Override
	protected void odswiezKontrolki() {
	}

	@Override
	protected void onOpen() {
	}

	@Override
	protected void przypiszMetody() {
	}

	private enum DaneTabelki implements SystemTables {

		ID_tabeli(Integer.class, "Identyfikator"), //
		Pracownik(String.class, "Pracownik"), //
		Dni(String.class, "Liczba dni urlopu w pracy"), //
		Okres(String.class, "Okres urlopu"), //
		Przepracowane(String.class, "l. dn. Rob. przepracowanych"), //
		Soboty(String.class, "Soboty"), //
		Frek(String.class, "Frek %"), //
		PremiaSob(String.class, "Premia soboty"), //
		PremiaUW(String.class, "Premia UW"), //
		PremiaFrek(String.class, "Premia frekw."), //
		Premia(String.class, "Premia");
		private Class<?> mKlasa;
		private String mColumnName;
		private Function<String, InterfejsSlownika> mOpis;

		<T> DaneTabelki(Class<T> cls, String pmNazwa) {
			this(cls, pmNazwa, null);
		}

		<T> DaneTabelki(Class<T> cls, String pmNazwa, Function<String, InterfejsSlownika> pmOpis) {
			mColumnName = pmNazwa;
			mKlasa = cls;
			mOpis = pmOpis;
		}
		@Override
		public Function<String, InterfejsSlownika> getOpisFunkcja() {
			return mOpis;
		}

		@Override
		public Class<?> getKlasa() {
			return mKlasa;
		}

		@Override
		public String getTableName() {

			return "DaneWydruk";
		}

		@Override
		public String getColumnName() {

			return mColumnName;
		}
	}
}
