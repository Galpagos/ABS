package pl.home.components.frames.src;

import ProjektGlowny.commons.Components.DatePicker;
import ProjektGlowny.commons.Components.LTable;
import ProjektGlowny.commons.DbBuilder.LRecordSet;
import ProjektGlowny.commons.DbBuilder.QueryBuilder;
import ProjektGlowny.commons.Frames.AbstractOkno;
import ProjektGlowny.commons.Frames.AskIntParams;
import ProjektGlowny.commons.Frames.universal.ZapytyniaUzytkownika;
import ProjektGlowny.commons.Frames.universal.ZapytywatorUzytkownika;
import ProjektGlowny.commons.Frames.universal.ZapytywatorUzytkownikaIn;
import ProjektGlowny.commons.Frames.universal.ZapytywatorUzytkownikaOut;
import ProjektGlowny.commons.utils.KomunikatUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;

import Utils.GridUtils;
import Wydruki.PrzygotowanieDanych.PracownikDTO;
import dbAccesspl.home.Database.Table.Zestawienie.AbsencjeColumns;
import dbAccesspl.home.Database.Table.Zestawienie.SobotaRoboczaColumns;
import dbAccesspl.home.Database.Table.Zestawienie.SystemTablesNames;
import dbAccesspl.home.Database.Table.Zestawienie.ZestawienieColumns;
import enums.InformacjeUniwersalne;
import pl.home.components.frames.mainframes.OknoPrzygotowaniaListyPracownikow;
import pl.home.components.frames.parameters.OPrzygListyPracWejscie;
import pl.home.components.frames.parameters.SaturdayOut;
import pl.home.components.frames.parameters.SaturdayViewIn;

public class SrcSaturdayView extends AbstractOkno<SaturdayViewIn, SaturdayOut> {
	private static final long serialVersionUID = 1L;
	private JPanel mContentPane;
	private JPanel mTop;
	private JLabel lblData;
	private LTable mTable;
	private JScrollPane mScrollPane;
	private LocalDate mData;
	private JButton btnPck1;
	private JPanel mRight;
	private JButton btnWorkers;

	private List<Integer> mWorkersId;
	private JButton btnUsun;
	private AbstractButton btnEdytuj;
	public SrcSaturdayView(SaturdayViewIn pmParams) {
		super(pmParams);

	}

	@Override
	protected void readParams() {
		mWorkersId = new ArrayList<>();
		mData = wyliczPoprzedniaSobote(LocalDate.now());
	}

	public LocalDate wyliczPoprzedniaSobote(LocalDate lvData) {
		while (!DayOfWeek.SATURDAY.equals(lvData.getDayOfWeek())) {
			lvData = lvData.minusDays(1);
		}
		return lvData;
	}

	@Override
	protected void budujOkno() {

		initOkno();
		initTop();
		initCenter();
		initRight();
		initBottomPanel();
		odswiezOkno();
	}

	private void initRight() {

		mRight = new JPanel(new GridBagLayout());
		mRight.setBorder(new EmptyBorder(5, 5, 10, 5));
		mRight.setPreferredSize(new Dimension(this.getSize().width / 5, 70));
		btnPck1 = new JButton("Wybór daty");
		btnPck1.addActionListener(e -> {
			LocalDate lvData = new DatePicker().setPickedLocalDate();
			if (lvData == null)
				return;
			mData = wyliczPoprzedniaSobote(lvData);
			odswiezOkno();
		});
		btnWorkers = new JButton("Dodaj");
		btnWorkers.addActionListener(lvE -> {
			OPrzygListyPracWejscie lvParams = OPrzygListyPracWejscie.builder().build();
			List<PracownikDTO> lvDane = new OknoPrzygotowaniaListyPracownikow(lvParams).WybierzPracownikow();
			zapisz(lvDane);
			odswiezOkno();
		});

		btnUsun = new JButton("Usuń");
		btnUsun.addActionListener(lvE -> usun());

		btnEdytuj = new JButton("Edytuj godziny");
		btnEdytuj.addActionListener(lvE -> edytujGodziny());
		mRight.add(btnPck1, GridUtils.gridCons(0, 0));
		mRight.add(btnWorkers, GridUtils.gridCons(0, 1));
		mRight.add(btnUsun, GridUtils.gridCons(0, 2));
		mRight.add(btnEdytuj, GridUtils.gridCons(0, 3));
		mContentPane.add(mRight, BorderLayout.EAST);
	}

	private void edytujGodziny() {
		if (mTable.getSelectedRowCount() == 0)
			return;
		ZapytywatorUzytkownikaIn lvIn = new ZapytywatorUzytkownikaIn();
		lvIn.setDefaultText("7");
		lvIn.setKontekst(ZapytyniaUzytkownika.DOUBLE);
		lvIn.setKomunikat(KomunikatUtils.fromString("Przepracowane godziny", "Ile godzin przepracowano?"));
		lvIn.setIntParams(AskIntParams.builder().defaultValue(7).minValue(0).maxValue(8).build());
		ZapytywatorUzytkownikaOut lvWynik = new ZapytywatorUzytkownika(lvIn).get();
		if (lvWynik.isAccepted())
			mTable.getSelectedKeys().forEach(lvId -> QueryBuilder.UPDATE()//
					.set(SobotaRoboczaColumns.GODZINY, lvWynik.getWartoscDouble())//
					.andWarunek(SobotaRoboczaColumns.ID_tabeli, lvId)//
					.execute());
		odswiezOkno();
	}

	private void usun() {
		mTable.getSelectedKeys().forEach(lvId -> QueryBuilder.DELETE()//
				.delete(SystemTablesNames.SOBOTA_ROBOCZA)//
				.andWarunek(SobotaRoboczaColumns.ID_tabeli, lvId)//
				.execute());
		odswiezOkno();
	}

	private void zapisz(List<PracownikDTO> pmDane) {

		if (pmDane.isEmpty())
			return;
		pmDane.removeIf(lvPrac -> mWorkersId.contains(lvPrac.getId()));

		pmDane.forEach(lvPrac -> QueryBuilder.INSERT()//
				.setFromGenerator(SobotaRoboczaColumns.ID_tabeli)//
				.set(SobotaRoboczaColumns.DATA, mData)//
				.set(SobotaRoboczaColumns.GODZINY, 8.)//
				.set(SobotaRoboczaColumns.ID_pracownika, lvPrac.getId())//
				.execute());
	}

	private void initOkno() {
		setModal(true);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Weryfikacja soboty");
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
		lblData.setFont(new Font("Serif", Font.BOLD, 35));

		mTop.add(mMiddleTop, BorderLayout.CENTER);
		mMiddleTop.add(lblData);

		mContentPane.add(mTop, BorderLayout.PAGE_START);
	}

	private void odswiezOkno() {

		lblData.setText(mData != null ? mData.toString() + "    " : "");
		mTable.reload(getDane());

	}

	private void initCenter() {
		mTable = createTable();
		mTable.getSelectionModel().addListSelectionListener(e -> odswiezKontrolki());
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

		LRecordSet lvRecSet = QueryBuilder.SELECT()//
				.select(SobotaRoboczaColumns.ID_tabeli, ZestawienieColumns.ID_tabeli, ZestawienieColumns.Pracownik, SobotaRoboczaColumns.GODZINY)//
				.joinOn(ZestawienieColumns.ID_tabeli, SobotaRoboczaColumns.ID_pracownika)//
				.andEqual(SobotaRoboczaColumns.DATA, mData)//
				.execute();

		mWorkersId.clear();
		mWorkersId.addAll(lvRecSet.stream()//
				.map(lvRec -> lvRec.getAsInteger(ZestawienieColumns.ID_tabeli))//
				.collect(Collectors.toList()));
		return lvRecSet;
	}

	private void initBottomPanel() {

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
	protected SaturdayOut budujWyjscie() {
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

}
