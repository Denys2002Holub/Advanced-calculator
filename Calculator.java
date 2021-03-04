package com.shpp.p2p.cs.dholub.assignment11;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Calculator {

    /**
     * Returns final result of two numbers and operand.
     *
     * @param tokens array with two numbers that needed to get result
     * @param operand operation to be performed with numbers
     * @return final result in String form
     * @throws Exception if doesn't finds an operation (+, -, *, /, ^)
     */
    static String getResultOfOperation(String[] tokens, String operand) throws Exception {
        double firstValue = Double.parseDouble(tokens[0]);
        double secondValue = Double.parseDouble(tokens[1]);

        switch (operand) {
            case "\\^":
                return String.valueOf(Math.pow(firstValue, secondValue));
            case "\\*":
                return String.valueOf(firstValue * secondValue);
            case "/":
                return String.valueOf(firstValue / secondValue);
            case "\\+":
                return String.valueOf(firstValue + secondValue);
            case "-":
                return String.valueOf(firstValue - secondValue);
            default:
                throw new Exception("Unknown operation. Something went wrong during computation");
        }
    }

    /**
     * Finds specific function by pattern and matcher, and
     * returns final result.
     *
     * @param function any of trigonometric functions
     * @param value value inside trigonometric function
     * @return final result + given quantity of brackets
     * @throws Exception if doesn't finds a function (cos, sin, tan, ...)
     */
    static String getFunctionResult(String function, double value) throws Exception {
        Pattern pattern = Pattern.compile("[a-z]{3,4}\\d*");
        Matcher matcher = pattern.matcher(function);

        if (matcher.find()) {
            switch (matcher.group()) {
                case "cos":
                    return String.valueOf(Math.cos(value));
                case "acos":
                    return String.valueOf(Math.acos(value));
                case "sin":
                    return String.valueOf(Math.sin(value));
                case "asin":
                    return String.valueOf(Math.asin(value));
                case "tan":
                    return String.valueOf(Math.tan(value));
                case "atan":
                    return String.valueOf(Math.atan(value));
                case "log10":
                    return String.valueOf(Math.log10(value));
                case "log2":
                    return String.valueOf(Math.log(value));
                case "sqrt":
                    return String.valueOf(Math.sqrt(value));
                default:
                    throw new Exception("Unknown function: " + function);
            }
        } else {
            throw new Exception("Unknown function " + function);
        }
    }
}