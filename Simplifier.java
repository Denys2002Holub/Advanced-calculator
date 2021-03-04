package com.shpp.p2p.cs.dholub.assignment11;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Simplifier {

    /**
     * All equation operations in regex form
     **/
    String[] regexOperands = {"\\^", "\\*", "/", "-", "\\+"};

    /**
     * Simplifies operands in given formula.
     *
     * @param expression any expression that needs to be simplified
     * @return expression after simplification
     */
    static String simplifyOperands(String expression) {
        return expression.replace("--", "+").
                replace("+-", "-");
    }

    /**
     * If finds expression like '-2-2', returns ["-2", "2"]
     *
     * @param expression any expression that needs to be simplified
     * @return expression after simplification
     */
    private static String[] simplifyOperation(String expression, String operand) {
        Pattern pattern = Pattern.compile("(-\\d+\\.*\\d*)-(\\d+\\.*\\d*)");
        Matcher matcher = pattern.matcher(expression);

        if (matcher.find()) {
            return new String[] {matcher.group(1), matcher.group(2)};
        } else {
            return expression.split(operand);
        }
    }

    /**
     * Simplifies expressions in brackets inside given expression.
     * Replaces expressions with final result.
     * Example:
     * expression (5+3)*(10-2)
     * finds first brackets (5+3) and replaces its with 8,
     * than finds second brackets (10-2) and replaces its with 8.
     *
     * @param expression any expression that needs to be simplified
     * @return expression after simplification
     * @throws Exception if doesn't find an operation (+, -, *, /, ^)
     */
    static String simplifyBrackets(String expression) throws Exception {
        Pattern pattern = Pattern.compile("\\((-*\\d+\\.*\\d*\\^*\\**/*-*\\+*-*\\d*\\.*\\d*)*\\)");
        Matcher matcher;
        String bracketsFormula = "";
        String parameters = "";

        while (Verifier.isExpressionHasBrackets(expression)) {
            matcher = pattern.matcher(expression);
            while (matcher.find()) {
                bracketsFormula = matcher.group();
                parameters = getExpressionInsideBrackets(bracketsFormula);
                matcher = pattern.matcher(parameters);
            }

            for (String operand : regexOperands) {
                parameters = simplifyOperations(parameters, operand);
            }


            expression = expression.replace(
                    bracketsFormula, parameters
            );

            expression = simplifyOperands(expression);
        }
        return expression;
    }

    /**
     * Finds inside the expression needed operation, and simplifies it
     * by replacing with the final result.
     *
     * @param expression any expression that needs to be simplified
     * @param operand    current operation that needs to be found in the expression
     * @return expression after simplification
     * @throws Exception if doesn't finds an operation (+, -, *, /, ^)
     */
    static String simplifyOperations(String expression, String operand) throws Exception {
        Pattern pattern = Pattern.compile("-*\\d+\\.*\\d*" + operand + "-*\\d+\\.*\\d*");
        Matcher matcher = pattern.matcher(expression);

        while (matcher.find()) {
            String[] tokens = simplifyOperation(matcher.group(), operand);
            expression = expression.replace(
                    matcher.group(), Calculator.getResultOfOperation(tokens, operand)
            );
            expression = simplifyOperands(expression);

            matcher = pattern.matcher(expression);
        }
        return expression;
    }

    /**
     * Finds inside the expression trigonometric function, and
     * firstly simplifies expression inside it, and than simplifies
     * the function itself by replacing with the final result.
     *
     * @param expression any expression that needs to be simplified
     * @return expression after simplification
     * @throws Exception if doesn't finds a function (cos, sin, tan, ...)
     */
    static String simplifyFunctions(String expression) throws Exception {
        Pattern pattern = Pattern.compile("[a-z]{3,4}\\d*\\((-*\\d+\\.*\\d*\\^*\\**/*-*\\+*-*\\d*\\.*\\d*)*\\)");
        Matcher matcher;
        String function = "";
        String value = "";
        while (Verifier.isExpressionHasTrigonometry(expression)) {
            matcher = pattern.matcher(expression);
            while (matcher.find()) {
                function = matcher.group();
                value = getExpressionInsideBrackets(function);
                matcher = pattern.matcher(value);
            }

            value = simplifyBrackets(value);
            for (String operand : regexOperands) {
                value = simplifyOperations(value, operand);
            }

            expression = expression.replace(
                    function,
                    Calculator.getFunctionResult(
                            function,
                            Double.parseDouble(value)
                    )
            );

            expression = simplifyOperands(expression);
        }
        return expression;
    }

    /**
     * Finds expression inside round brackets and returns it.
     *
     * @param expression any expression that needs to be simplified
     * @return expression without opened and closed round brackets
     */
    private static String getExpressionInsideBrackets(String expression) throws Exception {
        Pattern pattern = Pattern.compile("\\((.+)\\)");
        Matcher matcher = pattern.matcher(expression);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            throw new Exception("Check brackets quantity");
        }
    }
}