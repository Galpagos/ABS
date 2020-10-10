package pl.home.components.frames.src;

import ProjektGlowny.commons.Components.LTable;
import ProjektGlowny.commons.DbBuilder.DbSelect;
import ProjektGlowny.commons.DbBuilder.QueryBuilder;
import ProjektGlowny.commons.Frames.AbstractOkno;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import dbAccesspl.home.Database.Table.Zestawienie.AbsencjeColumns;
import dbAccesspl.home.Database.Table.Zestawienie.ZestawienieColumns;
import pl.home.components.frames.parameters.OneDayViewIn;
import pl.home.components.frames.parameters.OneDayViewOut;

public abstract class SrcOneDayView extends AbstractOkno<OneDayViewIn, OneDayViewOut> {

	private static final long serialVersionUID = 1L;
	private JButton btnAddWorker;
	private JButton btnBack;
	private JButton btnDeleteAbsence;
	private JButton btnDoubleBack;
	private JButton btnDoubleNext;
	private JButton btnModifyAbsence;
	private JButton btnNext;
	protected JCheckBox cbEditableAbsence;
	private JLabel lblData;
	private JPanel mContentPane;
	protected LocalDate mData;
	private JScrollPane mScrollPane;
	private LTable mTable;
	private JPanel mTop;
	private boolean mCzyOdswiezacTabele;
	public SrcOneDayView(OneDayViewIn pmParams) {
		super(pmParams);

	}

	protected abstract void addDay();

	protected abstract void addMonth();

	protected abstract void addWorker();

	@Override
	protected void budujOkno() {
		mCzyOdswiezacTabele = true;
		initOkno();
		initTop();
		initCenter();
		initRightPanel();
		initBottomPanel();
	}

	@Override
	protected OneDayViewOut budujWyjscie() {
		return OneDayViewOut.builder().build();
	}

	private void createLeftTop() {
		btnBack = new JButton("<");
		btnDoubleBack = new JButton("<<");
		JPanel mLeftTop = new JPanel(new GridLayout(1, 0, 5, 0));

		mLeftTop.add(btnBack, 0, 0);
		mLeftTop.add(btnDoubleBack, 1, 0);
		mTop.add(mLeftTop, BorderLayout.LINE_START);
	}

	private void createMiddleTop() {
		JPanel mMiddleTop = new JPanel(new GridBagLayout());
		lblData = new JLabel("");
		lblData.setFont(new Font("Serif", Font.BOLD, 35));
		mTop.add(mMiddleTop, BorderLayout.CENTER);
		mMiddleTop.add(lblData);
	}

	private void createRightTop() {
		btnNext = new JButton(">");
		btnDoubleNext = new JButton(">>");
		JPanel mRightTop = new JPanel(new GridLayout(1, 0, 5, 0));
		mRightTop.add(btnDoubleNext, 0, 0);
		mRightTop.add(btnNext, 1, 0);
		mTop.add(mRightTop, BorderLayout.LINE_END);
	}

	private LTable createTable() {
		DbSelect lvZapytanieLS = getTableQuery();

		LTable lvTabela = new LTable(lvZapytanieLS.execute());
		lvTabela.odswiez();
		lvTabela.repaint();
		lvTabela.reload(getTableQuery().execute());
		lvTabela.hideColumn(AbsencjeColumns.ID_ABS);

		return lvTabela;
	}

	protected abstract void deleteAbsence();

	protected List<Integer> getSelectedAbsenceId() {
		int[] lvTableIndex = mTable.getSelectedRows();
		return Arrays.stream(lvTableIndex)//
				.boxed()//
				.map(this::mapIndex)//
				.collect(Collectors.toList());
	}

	private QueryBuilder getTableQuery() {
		return QueryBuilder//
				.SELECT()//
				.select(AbsencjeColumns.ID_ABS, ZestawienieColumns.Pracownik, AbsencjeColumns.RODZAJ, AbsencjeColumns.Od_kiedy, AbsencjeColumns.Do_kiedy)//
				.joinOn(ZestawienieColumns.ID_PRAC, AbsencjeColumns.ID_pracownika)//
				.andBeforeOrEqual(AbsencjeColumns.Od_kiedy, mData)//
				.andAfterOrEqual(AbsencjeColumns.Do_kiedy, mData)//
				.andWarunek(ZestawienieColumns.Data_Zwolnienia, null).orderBy(ZestawienieColumns.Pracownik, true);
	}

	private GridBagConstraints gridCons(int pmX, int pmY) {
		GridBagConstraints lvGridCons = new GridBagConstraints();
		lvGridCons.gridx = pmX;
		lvGridCons.gridy = pmY;
		lvGridCons.fill = GridBagConstraints.HORIZONTAL;
		lvGridCons.ipady = 10;
		lvGridCons.insets = new Insets(5, 5, 5, 5);
		return lvGridCons;
	}

	private void initBottomPanel() {
		JPanel mBottomPanel = new JPanel(new GridLayout(1, 6, 10, 10));
		mBottomPanel.setBorder(new EmptyBorder(10, 5, 5, 5));
		mBottomPanel.add(new JPanel());
		mBottomPanel.add(new JPanel());
		mBottomPanel.add(new JPanel());
		mBottomPanel.add(new JPanel());
		mBottomPanel.add(mokButton);
		mBottomPanel.add(mcancelButton);

		mContentPane.add(mBottomPanel, BorderLayout.PAGE_END);
	}

	private void initCenter() {
		mTable = createTable();
		mScrollPane = new JScrollPane(mTable);
		mScrollPane.setAutoscrolls(true);
		mScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		mContentPane.add(mScrollPane, BorderLayout.CENTER);
	}

	private void initOkno() {
		setModal(true);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Weryfikacja obecności");
		mContentPane = new JPanel(new BorderLayout());
		mContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(mContentPane);
		mContentPane.add(new JPanel(), BorderLayout.CENTER);
	}

	private void initRightPanel() {
		JPanel lvPanel = new JPanel(new GridBagLayout());
		lvPanel.setBorder(new EmptyBorder(5, 20, 5, 10));

		cbEditableAbsence = new JCheckBox("Czy edytować absencję przed dodaniem?");
		btnAddWorker = new JButton("Dodaj pracownika");
		lvPanel.add(btnAddWorker, gridCons(0, 4));
		lvPanel.add(cbEditableAbsence, gridCons(0, 2));
		btnModifyAbsence = new JButton("Modyfikuj");
		lvPanel.add(btnModifyAbsence, gridCons(0, 6));
		btnDeleteAbsence = new JButton("Usuń");
		lvPanel.add(btnDeleteAbsence, gridCons(0, 7));

		mContentPane.add(lvPanel, BorderLayout.LINE_END);
	}

	private void initTop() {
		mTop = new JPanel(new BorderLayout());
		mTop.setBorder(new EmptyBorder(5, 5, 10, 5));
		mTop.setPreferredSize(new Dimension(this.getSize().width, 70));

		createLeftTop();
		createMiddleTop();
		createRightTop();

		mContentPane.add(mTop, BorderLayout.PAGE_START);

	}

	private Integer mapIndex(Integer pmIndeks) {
		int lvRow = mTable.convertRowIndexToModel(pmIndeks);
		return (Integer) mTable.getModel().getValueAt(lvRow, 0);
	}

	protected abstract void minusDay();

	protected abstract void minusMonth();

	protected abstract void modifyAbsence();

	@Override
	protected void odswiezKontrolki() {
		lblData.setText(mData.format(DateTimeFormatter.ISO_LOCAL_DATE));
		lblData.repaint();
		reloadTable();
		btnModifyAbsence.setEnabled(mTable.getSelectedRows().length == 1);
		btnDeleteAbsence.setEnabled(mTable.getSelectedRows().length > 0);
		repaint();
	}

	private void reloadTable() {
		if (mCzyOdswiezacTabele)
			mTable.reload(getTableQuery().execute());
	}

	@Override
	protected void onOpen() {
		mData = LocalDate.now();
	}

	@Override
	protected void przypiszMetody() {
		btnBack.addActionListener(lvE -> minusDay());
		btnNext.addActionListener(lvE -> addDay());
		btnDoubleBack.addActionListener(lvE -> minusMonth());
		btnDoubleNext.addActionListener(lvE -> addMonth());
		btnAddWorker.addActionListener(lvE -> addWorker());
		btnModifyAbsence.addActionListener(lvE -> modifyAbsence());
		btnDeleteAbsence.addActionListener(lvE -> deleteAbsence());
		mTable.getSelectionModel().addListSelectionListener(e -> odswiezKontrolkiBezTabeli());
	}

	private void odswiezKontrolkiBezTabeli() {
		mCzyOdswiezacTabele = false;
		odswiezKontrolki();
		mCzyOdswiezacTabele = true;
	}

}
