package function;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.tokenizer.UnknownFunctionOrVariableException;

import javax.swing.*;
import java.io.FileReader;
import java.io.IOException;

public class FunctionLoader {

    private static final int READ_BUFFER_SIZE = 2048;
    private static final String FUNC_VAR_NAME = "x";

    public static TrendFunction loadFromFile(String filename) throws IOException {
        FileReader reader = new FileReader(filename);

        StringBuilder fileOutput = new StringBuilder();
        char[] buffer = new char[READ_BUFFER_SIZE];
        int charsRead;

        while ((charsRead = reader.read(buffer)) > 0)
            fileOutput.append(buffer, 0, charsRead);

        String functionString = fileOutput.toString();

        return loadFromString(functionString);
    }

    public static TrendFunction loadFromString(String s) throws UnknownFunctionOrVariableException {
        Expression calc;

        calc = new ExpressionBuilder(s)
                .variable(FUNC_VAR_NAME)
                .build();

        TrendFunction function = x -> {
            calc.setVariable(FUNC_VAR_NAME, x);
            return calc.evaluate();
        };

        return function;
    }

}
