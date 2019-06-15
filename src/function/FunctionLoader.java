package function;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.tokenizer.UnknownFunctionOrVariableException;

import java.io.FileReader;
import java.io.IOException;

/**
 * Loads a <code>Function</code> class from a file or string.
 */
public class FunctionLoader {

    private static final int READ_BUFFER_SIZE = 2048;
    private static final String FUNC_VAR_NAME = "x";

    /**
     * Loads a <code>Function</code> object from a file.
     *
     * @param filename The filename of the file to read from.
     * @return The <code>Function</code> object.
     * @throws IOException Thrown if there is a problem reading the file.
     */
    public static Function loadFromFile(String filename) throws IOException {
        FileReader reader = new FileReader(filename);

        StringBuilder fileOutput = new StringBuilder();
        char[] buffer = new char[READ_BUFFER_SIZE];
        int charsRead;

        while ((charsRead = reader.read(buffer)) > 0)
            fileOutput.append(buffer, 0, charsRead);

        String functionString = fileOutput.toString();

        return loadFromString(functionString);
    }

    /**
     * Loads a <code>Function</code> object from a string.
     *
     * @param s The string version of the function.
     * @return The <code>Function</code> object.
     * @throws UnknownFunctionOrVariableException Thrown if the function can't be read.
     */
    public static Function loadFromString(String s) throws UnknownFunctionOrVariableException {
        Expression calc;

        calc = new ExpressionBuilder(s)
                .variable(FUNC_VAR_NAME)
                .build();

        return x -> {
            calc.setVariable(FUNC_VAR_NAME, x);

            Double result;
            try {
                result = calc.evaluate();
            } catch (ArithmeticException e) {
                result = null;
            }

            return result;
        };
    }

}
