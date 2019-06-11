package csv;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CSVWriter<T> {
    private File file;
    private T[][] data;

    public CSVWriter(File file) {
        this.file = file;
    }

    public void setData(T[][] data) {
        this.data = data;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void write() throws IOException {
        if (data == null)
            throw new IOException();

        FileWriter writer = new FileWriter(file);

        for (T[] row : data) {
            StringBuilder rowText = new StringBuilder();
            for (int i = 0; i < row.length - 1; i++) {
                rowText.append(row[i].toString());
                rowText.append(',');
            }

            rowText.append(row[row.length - 1].toString());
            rowText.append('\n');

            writer.write(rowText.toString());
        }

        writer.close();
    }
}
