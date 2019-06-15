package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

/**
 * A table to edit all the fields in the application.
 */
public abstract class MoreOptionsTable<OptionClass> extends JTable {

    private static String[] COLUMNS = new String[]{"Field", "Value"};
    private static Object[][] DEFAULT_DATA = {
            {"Function", "x^2", String.class},
            {"Start", 0.0, Double.class},
            {"Count", 0, Integer.class},
            {"Stride", 0.0, Double.class},
            {"SigmaX", 0.0, Double.class},
            {"SigmaY", 0.0, Double.class},
            {"Outliers", false, Boolean.class},
            {"One Every", 0, Integer.class},
            {"Scale", 0.0, Double.class},
    };

    protected Object[][] data;
    private Class editingClass = null;

    /**
     * Constructs a new <code>MoreOptionsTable</code> instance.
     */
    public MoreOptionsTable() {
        super(new MoreOptionsTableModel(getDisplayData(DEFAULT_DATA), COLUMNS));

        getTableHeader().setReorderingAllowed(false);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        data = getDisplayData(DEFAULT_DATA);

        updateModel();
    }

    /**
     * Constructs a new <code>ContinuousOptions</code> instance and passes in the field
     * values that are already set.
     *
     * @param options The options to pass.
     */
    public MoreOptionsTable(OptionClass options) {
        this();

        setOptions(options);

        updateModel();
    }

    /**
     * Extracts the data to display out of all the fields in the static <code>DEFAULT_DATA</code>
     * array.
     *
     * @return The 2D array containing all the data to be put into the cells of the table.
     */
    protected static Object[][] getDisplayData(Object[][] data) {
        Object[][] displayData = new Object[data.length][2];

        for (int i = 0; i < data.length; i++) {
            displayData[i][0] = data[i][0];
            displayData[i][1] = data[i][1];
        }

        return displayData;
    }

    /**
     * Returns the options that are currently in the fields.
     *
     * @return The options that are currently in the fields.
     */
    public abstract OptionClass getOptions();

    /**
     * Sets the options in the fields to the given parameter <code>options</code>.
     *
     * @param options The options to set.
     */
    public abstract void setOptions(OptionClass options);

    /**
     * Copies the values from the <code>data</code> array into the table model.
     */
    protected void updateModel() {
        TableModel tableModel = getModel();

        // Set the table model data to "this.data"
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                tableModel.setValueAt(data[i][j], i, j);
            }
        }

        adjustRowCount();
    }

    protected void adjustRowCount() {
        ((DefaultTableModel) getModel()).setRowCount(data.length);
    }

    @Override
    public TableCellRenderer getCellRenderer(int row, int column) {
        editingClass = null;

        TableModel model = getModel();
        Class cellClass = model.getValueAt(row, column).getClass();
        return getDefaultRenderer(cellClass);
    }

    @Override
    public TableCellEditor getCellEditor(int row, int column) {
        editingClass = null;

        if (getModel().isCellEditable(row, column)) {
            editingClass = getModel().getValueAt(row, column).getClass();
            return getDefaultEditor(editingClass);
        } else {
            return super.getCellEditor(row, column);
        }
    }

    @Override
    public Class<?> getColumnClass(int column) {
        return editingClass != null ? editingClass : super.getColumnClass(column);
    }

    protected static class MoreOptionsTableModel extends DefaultTableModel {
        public MoreOptionsTableModel(Object[][] data, Object[] columnNames) {
            super(data, columnNames);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 1;
        }
    }

}
