package gui.continuous;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

/**
 * A table to edit all the fields in the application.
 */
public class MoreOptionsTable extends JTable {

    private static final String[] COLUMNS = new String[]{"Field", "Value"};
    private static final Object[][] DEFAULT_DATA = {
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
    private Class editingClass = null;
    private Object[][] data;

    /**
     * Constructs a new <code>MoreOptionsTable</code> instance.
     */
    public MoreOptionsTable() {
        super(new MoreOptionsTableModel(getDisplayData(), COLUMNS));

        getTableHeader().setReorderingAllowed(false);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        data = getDisplayData();
    }

    /**
     * Constructs a new <code>ContinuousOptions</code> instance and passes in the field
     * values that are already set.
     *
     * @param options The options to pass.
     */
    public MoreOptionsTable(ContinuousOptions options) {
        this();

        data[0][1] = options.function; // Function
        data[1][1] = options.start; // Start
        data[2][1] = options.count; // Count
        data[3][1] = options.stride; // Stride
        data[4][1] = options.sigmaX; // Sigma X
        data[5][1] = options.sigmaY; // Sigma Y
        data[6][1] = options.outlierCount != 0; // Outliers
        data[7][1] = options.outlierCount; // One Every
        data[8][1] = options.outlierScale; // Scale

        updateModel();
    }

    /**
     * Extracts the data to display out of all the fields in the static <code>DEFAULT_DATA</code>
     * array.
     *
     * @return The 2D array containing all the data to be put into the cells of the table.
     */
    private static Object[][] getDisplayData() {
        Object[][] displayData = new Object[DEFAULT_DATA.length][2];

        for (int i = 0; i < DEFAULT_DATA.length; i++) {
            displayData[i][0] = DEFAULT_DATA[i][0];
            displayData[i][1] = DEFAULT_DATA[i][1];
        }

        return displayData;
    }

    /**
     * Returns the options that are currently in the fields.
     * @return The options that are currently in the fields.
     */
    public ContinuousOptions getOptions() {
        ContinuousOptions options = new ContinuousOptions();
        TableModel model = getModel();

        options.function = (String) model.getValueAt(0, 1);
        options.start = (Double) model.getValueAt(1, 1);
        options.count = (Integer) model.getValueAt(2, 1);
        options.stride = (Double) model.getValueAt(3, 1);
        options.sigmaX = (Double) model.getValueAt(4, 1);
        options.sigmaY = (Double) model.getValueAt(5, 1);
        options.outlierCount = (Integer) model.getValueAt(7, 1);
        options.outlierScale = (Double) model.getValueAt(8, 1);

        return options;
    }

    /**
     * Copys the values from the <code>data</code> array into the table model.
     */
    private void updateModel() {
        TableModel model = getModel();

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                model.setValueAt(data[i][j], i, j);
            }
        }
    }

    @Override
    public TableCellRenderer getCellRenderer(int row, int column) {
        editingClass = null;

        Class cellClass = getModel().getValueAt(row, column).getClass();
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

    private static class MoreOptionsTableModel extends DefaultTableModel {
        public MoreOptionsTableModel(Object[][] data, Object[] columnNames) {
            super(data, columnNames);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 1;
        }
    }

}
