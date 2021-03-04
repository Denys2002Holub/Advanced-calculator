package com.shpp.p2p.cs.dholub.assignment11;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Verifier {

    /**
     * Checks is number writing correct.
     *
     * @param number any number that needs to be checked for correct numbers
     * @return is number correct or not
     */
    static boolean isNumberCorrect(String number) {
        Pattern pattern = Pattern.compile("\\d+\\.*\\d*");
        Matcher matcher = pattern.matcher(number);
        return matcher.find();
    }

    /**
     * Counts brackets quantity inside the expression.
     *
     * @param expression any expression that needs to be verified
     * @return is opened brackets quantity equals closed brackets quantity
     */
    static boolean isBracketsQuantityCorrect(String expression) {
        int openedBracketsQuantity = 0;
        int closedBracketsQuantity = 0;

        Pattern openedBracket = Pattern.compile("\\(");
        Matcher matcher = openedBracket.matcher(expression);
        while (matcher.find()) {
            openedBracketsQuantity++;
        }

        Pattern closedBracket = Pattern.compile("\\)");
        matcher = closedBracket.matcher(expression);
        while (matcher.find()) {
            closedBracketsQuantity++;
        }

        return openedBracketsQuantity == closedBracketsQuantity;
    }

    /**
     * Checks different parameters for correct writing.
     *
     * @param variable string how is the variable set
     * @return is variable format correct
     */
    static boolean isVariableFormatCorrect(String variable) {
        return Pattern.compile("[a-z]=-*\\d+\\.*\\d*").matcher(variable).find() &&
                isNumberCorrect(variable);
    }

    /**
     * Checks is current character parameter or not.
     *
     * @param expression any expression in which needs to find a variable
     * @param currentSymbol current expression character number
     * @return is current character parameter
     */
    static boolean isParameter(String expression, int currentSymbol) {
        if (currentSymbol == 0) {
            return isLetter(expression.charAt(currentSymbol)) &&
                    !isLetter(expression.charAt(currentSymbol + 1));
        } else if (currentSymbol == expression.length() - 1) {
            return !isLetter(expression.charAt(currentSymbol - 1)) &&
                    isLetter(expression.charAt(currentSymbol));
        } else {
            return !isLetter(expression.charAt(currentSymbol - 1)) &&
                    isLetter(expression.charAt(currentSymbol)) &&
                    !isLetter(expression.charAt(currentSymbol + 1));
        }
    }

    /**
     * Checks is given symbol letter or not.
     *
     * @param symbol any symbol to check
     * @return is given symbol letter or not
     */
    static boolean isLetter(char symbol) {
        return Character.isLetter(symbol);
    }

    /**
     * Checks is opened or closed round brackets exists inside the given expression.
     *
     * @param expression any expression that needs to be verified
     * @return is opened or closed round brackets exists inside the expression
     */
    static boolean isExpressionHasBrackets(String expression) {
        return Pattern.compile("[()]").matcher(expression).find();
    }

    /**
     * Checks is trigonometric functions exists inside the expression.
     *
     * @param expression any expression that needs to be verified
     * @return is any trigonometric functions exists inside the expression
     */
    static boolean isExpressionHasTrigonometry(String expression) {
        return Pattern.compile("[a-z]{3,4}\\(.+\\)").matcher(expression).find();
    }
}