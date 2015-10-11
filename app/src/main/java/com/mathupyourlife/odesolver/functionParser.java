package com.mathupyourlife.odesolver;

import java.util.Queue;

/**
 * Created by mathias on 10.10.15.
 */

public class functionParser {

    private static double pi = 3.14159265359;
    private static double e = 2.71828182846;

    private String ODEString;
    public double result;
    private Queue<String> terms;
    private Queue<String> operations;

    //constructor
    functionParser(String expression){
        expression = expression.trim();
        ODEString = expression;

        ODE();
    }
    //divide the ODE by priority: (), ^, *, /, -, +
    private void ODE(){
        ODEString = ODEString.replace(" ", "");

        if (ODEString.contains("(")){
            getBrackets();
        }

        if (ODEString.contains("^")){
            getPow();
        }

        if (ODEString.contains("*") || ODEString.contains("/")){
            getMultiplyAndDivision();
        }

        if(ODEString.contains("+") || ODEString.contains("-")){

            getPlusAndMinus();
        }

    }

    private void getBrackets(){


    }
    private void getPow(){


    }

    private void getMultiplyAndDivision(){


    }

    private void getPlusAndMinus(){

        for(int i = 1; i < ODEString.length(); i++){

            if(ODEString.charAt(i) == '+'){
                //start
                terms.add(ODEString.substring(i-1,i-1));
                terms.add(ODEString.substring(i+1,i+1));
                operations.add("+");
            }
        }
    }

    public double evalODE(double x, double y) {
        double value = 0;

        //local copies of the queues
        if(!this.terms.isEmpty()){
            Queue<String> termsCopy = this.terms;
            Queue<String> operationsCopy = this.operations;
            String str1, str2;

            //poll queues step by step
            if(termsCopy.size() >= 2){

                for (int i = 0; i < termsCopy.size(); i++) {

                    str1 = termsCopy.poll().replace("x", String.valueOf(x));
                    str1 = str1.replace("y", String.valueOf(y));

                    str2 = termsCopy.poll().replace("x", String.valueOf(x));
                    str2 = str1.replace("y", String.valueOf(y));

                    if (operationsCopy.poll().equals("+")) {

                        value = Double.valueOf(str1) + Double.valueOf(str2);
                    }
                }
            }
        }
        else{
            String ODE = ODEString.replace("x", String.valueOf(x));
            ODE = ODE.replace("y", String.valueOf(y));
            value = Double.valueOf(ODE);
        }
        return value;
    }
}