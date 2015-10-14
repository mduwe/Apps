package com.mathupyourlife.odesolver;

import java.util.Stack;

public class mParser {
    private double result;

    public mParser(String expression) throws Exception {
        expression = analyseExpressionForSymbols(expression);
        result = calculatePostFix(generatePostFix(expression));
    }

    private String analyseExpressionForSymbols(String expression) throws Exception {
        expression = "(" + expression + ")";
        expression = expression.replace(" ", "");
        if (findAdequateBracketEnd(expression, 0) == -1) {
            throw new Exception("Brackets are set wrongly");
        }
        expression = transformMinus(expression);
        expression = transformSqrt(expression);
        expression = transformExp(expression);
        expression = transformPI(expression);
        expression = transformSqr(expression);
        expression = transformTrigo(expression, "");
        return expression;
    }

    private int findAdequateBracketEnd(String expression, int start) {
        int count = 0;
        for (int i = start; i < expression.length(); i++) {
            if (expression.charAt(i) == '(') {
                count++;
            } else {
                if (expression.charAt(i) == ')') {
                    count--;
                    if (count == 0) {
                        return i;
                    }
                }
            }
        }
        return count == 0 ? expression.length() - 1 : -1;
    }
    private String transformTrigo(String expression, String fun) throws Exception {
        while (expression.contains("sin") || expression.contains("cos") || expression.contains("tan")) {
            int startCos = expression.indexOf("cos");
            int startSin = expression.indexOf("sin");
            int startTan = expression.indexOf("tan");
            if (startCos != -1 && ((startSin != -1 && startTan != -1 && startCos < startTan && startCos < startSin) || (startSin != -1 && startTan == -1 && startCos < startSin) || (startSin == -1 && startTan != -1 && startCos < startTan) || (startTan == -1 && startSin == -1))) {
                int bracketEnd = findAdequateBracketEnd(expression, startCos + 3);
                expression = expression.substring(0, startCos) + transformTrigo(expression.substring(startCos + 3, bracketEnd + 1), "cos") + expression.substring(bracketEnd + 1);
            } else {
                if (startSin != -1 && ((startCos != -1 && startTan != -1 && startSin < startTan && startSin < startCos) || (startCos != -1 && startTan == -1 && startSin < startCos) || (startCos == -1 && startTan != -1 && startSin < startTan) || (startCos == -1 && startTan == -1))) {
                    int bracketEnd = findAdequateBracketEnd(expression, startSin + 3);
                    expression = expression.substring(0, startSin) + transformTrigo(expression.substring(startSin + 3, bracketEnd + 1), "sin") + expression.substring(bracketEnd + 1);
                } else {
                    if (startTan != -1 && ((startSin != -1 && startCos != -1 && startTan < startSin && startTan < startCos) || (startSin != -1 && startCos == -1 && startTan < startSin) || (startSin == -1 && startCos != -1 && startTan < startCos) || (startCos == -1 && startSin == -1))) {
                        int bracketEnd = findAdequateBracketEnd(expression, startTan + 3);
                        expression = expression.substring(0, startTan) + transformTrigo(expression.substring(startTan + 3, bracketEnd + 1), "tan") + expression.substring(bracketEnd + 1);
                    }
                }
            }
        }
        if (fun.equals("sin")) {
            expression = transformMinus("(" + Math.sin(calculatePostFix(generatePostFix(expression))) + ")");
        } else {
            if (fun.equals("cos")) {
                expression = transformMinus("(" + Math.cos(calculatePostFix(generatePostFix(expression))) + ")");
            } else {
                if (fun.equals("tan")) {
                    expression = transformMinus("(" + Math.tan(calculatePostFix(generatePostFix(expression))) + ")");
                }
            }
        }
        return expression;
    }

    private String transformSqr(String expression) {
        int pos0 = 0, pos1 = 0;
        while (expression.contains("sqr")) {
            pos0 = expression.indexOf("sqr");
            pos1 = findAdequateBracketEnd(expression, pos0 + 3);
            expression = expression.substring(0, pos0) + expression.substring(pos0 + 3, pos1 + 1) + "^2" + expression.substring(pos1 + 1);
        }
        return expression;
    }

    private String transformExp(String expression) {
        expression = expression.replace("e", "2.718281828");
        return expression;
    }

    private String transformPI(String expression) {
        expression = expression.replace("PI", "3.14159265359");
        expression = expression.replace("pi", "3.14159265359");
        return expression;
    }

    private String transformSqrt(String expression) {
        int pos0 = 0, pos1 = 0;
        while (expression.contains("sqrt")) {
            pos0 = expression.indexOf("sqrt");
            pos1 = findAdequateBracketEnd(expression, pos0 + 4);
            expression = expression.substring(0, pos0) + expression.substring(pos0 + 4, pos1 + 1) + "^0.5" + expression.substring(pos1 + 1);
        }
        return expression;
    }

    private String transformMinus(String expression) {
        int pos0 = 0;
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '-' && expression.charAt(i - 1) == '(') {
                pos0 = findAdequateBracketEnd(expression, i - 1);
                expression = expression.substring(0, i) + "(0-" + expression.substring(i + 1, pos0) + ")" + expression.substring(pos0);
            } else {
                if (expression.charAt(i) == '-'
                        && (expression.charAt(i - 1) == '+'
                        || expression.charAt(i - 1) == '-'
                        || expression.charAt(i - 1) == '/'
                        || expression.charAt(i - 1) == '*' || expression
                        .charAt(i - 1) == '^')) {
                    for (int j = i + 1; j < expression.length(); j++) {
                        if (j == expression.length() - 1
                                || expression.charAt(j) == '/'
                                || expression.charAt(j) == '*'
                                || expression.charAt(j) == '+'
                                || expression.charAt(j) == '-'
                                || expression.charAt(j) == '^'
                                || expression.charAt(j) == ')') {
                            if (j == expression.length() - 1) {
                                pos0 = j + 1;
                                break;
                            } else {
                                pos0 = j;
                                break;
                            }

                        }
                    }
                    expression = expression.substring(0, i) + "(0-" + expression.substring(i + 1, pos0) + ")" + expression.substring(pos0);
                }
            }

        }
        return expression;
    }

    public Stack<String> generatePostFix(String expression) throws Exception {
        Stack<String> operandStack = new Stack<String>();
        Stack<String> operatorStack = new Stack<String>();
        String number = "";
        int i = 0;
        operatorStack.push("(");
        try {
            while (!operatorStack.isEmpty()) {
                switch (expression.charAt(i)) {
                    case '(':
                        operatorStack.push("(");
                        break;
                    case '^':
                        operatorStack.push("^");
                        break;
                    case '*':
                        if (operatorStack.peek().matches("[*^/]")) {
                            while (operatorStack.peek() != "(" && (operatorStack.peek().matches("[*^/]"))) {
                                operandStack.push(operatorStack.pop() + "");
                            }
                        }
                        operatorStack.push("*");
                        break;
                    case '/':
                        if (operatorStack.peek().matches("[^*/]")) {
                            while (!operatorStack.peek().equals("(") && (operatorStack.peek().matches("[^*/]"))) {
                                operandStack.push(operatorStack.pop() + "");
                            }
                        }
                        operatorStack.push("/");
                        break;
                    case '-':
                        while (!operatorStack.peek().equals("(")) {
                            operandStack.push(operatorStack.pop() + "");
                        }
                        operatorStack.push("-");
                        break;
                    case '+':
                        while (!operatorStack.peek().equals("(")) {
                            operandStack.push(operatorStack.pop() + "");
                        }
                        operatorStack.push("+");
                        break;
                    case ')':
                        String operator = "";
                        while (!(operator = operatorStack.pop()).equals("(")) {
                            operandStack.push(operator + "");
                        }
                        break;
                    default:
                        number += expression.charAt(i) + "";
                        if (i + 1 == expression.length()
                                || expression.charAt(i + 1) == '+'
                                || expression.charAt(i + 1) == '-'
                                || expression.charAt(i + 1) == '*'
                                || expression.charAt(i + 1) == '/'
                                || expression.charAt(i + 1) == '^'
                                || expression.charAt(i + 1) == ')') {
                            operandStack.push(number);
                            number = "";
                        }
                }
                i++;
                if (i == expression.length()) {
                    String operator = "";
                    while (!(operator = operatorStack.pop()).equals("(")) {
                        operandStack.push(operator + "");
                    }
                }
            }
            return operandStack;
        } catch (Exception e) {
            throw new Exception("wrong input");
        }
    }

    public double calculatePostFix(Stack<String> operandStack) throws Exception {
        Stack<String> stack = new Stack<String>();
        double result = 0.0;
        stack.addAll(operandStack);
        operandStack.clear();
        while (!stack.isEmpty()) {
            operandStack.add(stack.pop());
        }
        try {
            if (operandStack.size() < 3) {
                return Double.parseDouble(operandStack.peek());
            }
            while (!operandStack.isEmpty()) {
                if (!operandStack.peek().matches("[+-/*^]")) {
                    stack.push(operandStack.pop());
                } else {
                    char operator = operandStack.pop().charAt(0);
                    String buffer = stack.pop();
                    switch (operator) {
                        case '+':
                            result = Double.parseDouble(stack.pop()) + Double.parseDouble(buffer);
                            stack.push(result + "");
                            break;
                        case '-':
                            result = Double.parseDouble(stack.pop()) - Double.parseDouble(buffer);
                            stack.push(result + "");
                            break;
                        case '*':
                            result = Double.parseDouble(stack.pop()) * Double.parseDouble(buffer);
                            stack.push(result + "");
                            break;
                        case '/':
                            result = Double.parseDouble(stack.pop()) / Double.parseDouble(buffer);
                            stack.push(result + "");
                            break;
                        case '^':
                            double a = Double.parseDouble(stack.pop());
                            result = Math.pow(a, Double.parseDouble(buffer));
                            stack.push(result + "");
                            break;
                    }
                }
            }
            return result;
        } catch (Exception e) {
            throw new Exception("Wrong input");
        }
    }

    public double getResult() {
        return result;
    }

}
