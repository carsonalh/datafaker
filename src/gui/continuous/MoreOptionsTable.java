package gui.continuous;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class MoreOptionsTable extends JTable {

    private static final String[] COLUMNS = new String[]{"Field", "Value"};
    private static final Object[][] DATA = {
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

    public MoreOptionsTable() {
        super(new MoreOptionsTableModel(getDisplayData(), COLUMNS));

        getTableHeader().setReorderingAllowed(false);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private static Object[][] getDisplayData() {
        Object[][] displayData = new Object[DATA.length][2];

        for (int i = 0; i < DATA.length; i++) {
            displayData[i][0] = DATA[i][0];
            displayData[i][1] = DATA[i][1];
        }

        return displayData;
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
