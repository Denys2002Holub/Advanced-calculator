package com.shpp.p2p.cs.dholub.assignment11;

import java.util.HashMap;
import java.util.Scanner;

public class Assignment11Part1 implements Simplifier, Verifier {

    /* Original formula that user wrote */
    private static String originalFormula;

    /* Formula that simplifies */
    private static String modifiedFormula;

    /* Variables that user gives */
    private static HashMap<String, Double> variables = new HashMap<>();

    /* Scanner for getting another variable values */
    private static Scanner scanner = new Scanner(System.in);

    /*
    Main method that checks program arguments and,
    depending on it's quantity, makes calculations or
    informs user of an error.
    Program can calculate formula with brackets and trigonometrical functions.
    This is an example for correct writing of trigonometric formulas:
        cosine -> cos(value)
        sinus -> sin(value)
        tangent -> tan(value)
        arccosine -> acos(value)
        arcsine -> asin(values)
        arctangent -> atan(value)
        logarithm -> log2(value), log10(value)
        square root -> sqrt(value)
    User can write formula and variables with spaces or not.
    After first calculation, program will ask user to enter
    new values for all variables given in formula.
     */
    public static void main(String[] args) throws Exception {
        if (args.length != 0) {
            while (true) {
                modifyArgsToRightForm(args);
                setFormula(args);
                setVariables(args);
                substituteValues();
                System.out.println("Result: " + calculate());
                setNewParameters(args);
            }
        } else {
            throw new Exception("Program launched without parameters");
        }
    }

    /*
    Asks user to enter new values for all variables in formula.
     */
    private static void setNewParameters(String[] args) {
        if (args.length > 1) {
            modifiedFormula = originalFormula;

            for (int i = 1; i < args.length; i++) {
                System.out.println("Set another value to: " + args[i].charAt(0));
                args[i] = args[i].charAt(0) + "=" + scanner.nextDouble();
            }
        } else {
            System.exit(1);
        }
    }

    /*
    Simplifies (calculates) formula to the final result, and returns it.
    Firstly, we need to simplify all trigonometric functions.
    Secondly, we need to simplify expressions inside the brackets.
    Thirdly, we need to simplify all that's left.
     */
    private static double calculate() throws Exception {
        modifiedFormula = Simplifier.simplifyFunctions(modifiedFormula);
        modifiedFormula = Simplifier.simplifyBrackets(modifiedFormula);
        for (String operand : regexOperands) {
            modifiedFormula = Simplifier.simplifyOperations(modifiedFormula, operand);
        }

        try {
            return Double.parseDouble(modifiedFormula);
        } catch (NumberFormatException e) {
            throw new Exception("Too big or too small value for double");
        }
    }

    /*
    Deletes all spaces and converts all of the characters to lower case
    in formula and parameters.
     */
    private static void modifyArgsToRightForm(String[] args) {
        args[0] = Simplifier.simplifyOperands(args[0]);
        for (int i = 0; i < args.length; i++) {
            args[i] = args[i].replace(" ", "").toLowerCase();
        }
    }

    /*
    Checks formula for correct writing, and writes it to the variables.
     */
    private static void setFormula(String[] args) throws Exception {
        if (Verifier.isNumberCorrect(args[0]) && Verifier.isBracketsQuantityCorrect(args[0])) {
            originalFormula = modifiedFormula = args[0];
            System.out.println("Original formula: " + originalFormula);
        } else {
            throw new Exception("Number format is incorrect or brackets quantity isn't equals");
        }
    }

    /*
    Checks is user gives any parameters, and writes it to
    HashMap<String,Double>. Where String is a variable and
    Double is a value.
     */
    private static void setVariables(String[] args) throws Exception {
        if (args.length > 1) {
            for (int i = 1; i < args.length; i++) {
                if (Verifier.isVariableFormatCorrect(args[i])) {
                    String[] tokens = args[i].split("=");
                    variables.put(tokens[0], Double.parseDouble(tokens[1]));
                } else {
                    throw new Exception(args[i] + " has incorrect form");
                }
            }
            System.out.println("Parameters: " + variables.toString());
        }
    }

    /*
    Finds variables in formula, and if user give value to this variable,
    substitutes it instead of letter.
     */
    private static void substituteValues() {
        StringBuilder stringBuilder = new StringBuilder(modifiedFormula);

        for (int i = 0; i < stringBuilder.length(); i++) {
            if (Verifier.isParameter(stringBuilder.toString(), i)) {
                if (variables.containsKey(String.valueOf(stringBuilder.charAt(i)))) {
                    String letter = String.valueOf(stringBuilder.charAt(i));
                    stringBuilder.deleteCharAt(i).insert(i, variables.get(letter));
                }
            }
        }

        modifiedFormula = stringBuilder.toString();
    }
}