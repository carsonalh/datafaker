package csv;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A class for writing 2D arrays to a CSV file.
 *
 * @param <T> The type of data in the 2D array.
 */
public class CSVWriter<T> {
    private File file;
    private T[][] data;

    /**
     * Constructs a CSVWriter with the output file of <code>file</code>.
     *
     * @param file The file to write the data to.
     */
    public CSVWriter(File file) {
        this.file = file;
    }

    /**
     * Sets the data to write to the file with.
     *
     * @param data The data to write to the file with.
     */
    public void setData(T[][] data) {
        this.data = data;
    }

    /**
     * Sets the file to write to.
     *
     * @param file The file to write to.
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * Writes the data to the given file.
     *
     * @throws IOException Thrown if there is a problem writing the data.
     */
    public void write() throws IOException {
        if (data == null)
            throw new IOException("Data is null");

        FileWriter writer = new FileWriter(file);

        for (T[] row : data) {
            StringBuilder rowText = new StringBuilder();
            for (int i = 0; i < row.length - 1; i++) {
                rowText.append(row[i].toString());
                rowText.append(',');
            }

            rowText.append(row[row.length - 1].toString());
            rowText.append('\n');

            // rowText will be in the format of: abc,def,...,xyz\n

            writer.write(rowText.toString());
        }

        writer.close();
    }
}
