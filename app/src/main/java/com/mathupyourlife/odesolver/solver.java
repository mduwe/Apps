package com.mathupyourlife.odesolver;

import android.provider.ContactsContract;

/**
 * Created by mathias on 09.10.15.
 */
public class solver{

    private int _numberOfSteps;
    private double _stepSize;
    private double _leftBound;
    private double _rightBound;
    private double _initialValue;

    public String _ODE;
    private String _solver;

    public double[][] data;
    public int solverID;
    //constructor, its necessary that all inputs are made

    solver(double stepSize, double leftBound, double rightBound,
           double initialValue, int numberOfSteps, String ODE, String solver){

        //copy inputs to private elements
        _leftBound = leftBound;
        _rightBound = rightBound;
        _initialValue = initialValue;
        _stepSize = stepSize;
        _numberOfSteps = numberOfSteps;
        _ODE = ODE;
        _solver = solver;
        solverID = getSolverID(_solver);
        //initalize log


    }

    public Boolean startSolving(){

        int solverID = getSolverID(this._solver);
        boolean solved = false;

        if(solverID != 0){
            switch(solverID){
                case 1: solved = this.startNumSolvingWithExplEuler(); break;
                case 2: solved = this.startNumSolvingWithImplEuler(); break;
                case 3: solved = this.startNumSolvingWithRungeKutta(); break;
            }

        }
        return solved;
    }
    private Integer getSolverID(String solver){

        switch (solver){
            case "expl. Euler": return 1;
            case "impl. Euler": return 2;
            case "Runge/Kutta": return 3;
        }
        return 0;
    }

    private boolean startNumSolvingWithExplEuler(){
        //Start the solving with explicit euler
        // u(i+1) = u(i) + h f(xi,ui)
        this.data = new double[this._numberOfSteps+1][2];
        double h = this._stepSize;

        double x = this._leftBound;
        double y = this._initialValue;

        //save initial value

        int i = 0;
        while((i < this._numberOfSteps) && (x < this._rightBound)){

            data[i][0] = x;
            data[i][1] = y;

            y = y + h * f(x,y);
            x = x + h;

            i++;
        }

        data[this._numberOfSteps][0] = x;
        data[this._numberOfSteps][1] = y;
        return true;
    }

    private boolean startNumSolvingWithImplEuler(){

        return true;
    }

    private boolean startNumSolvingWithRungeKutta(){

        //implements the 3step rungekutta method
        double k1, k2, k3;
        this.data = new double[this._numberOfSteps+1][2];
        double h = this._stepSize;

        double x = this._leftBound;
        double y = this._initialValue;

        int i = 0;
        while((i < this._numberOfSteps) && (x < this._rightBound)){
             //save data
            data[i][0] = x;
            data[i][1] = y;

            k1 = f(x,y);
            k2 = f(x + 0.5*h, y + 0.5*h*k1);
            k3 = f(x + h, y - h*k1 + 2.*h*k2);

            y = y + h*(k1 + 4.*k2 + k3)/6.;

            //next
            x = x + h;
            i++;
        }
        data[this._numberOfSteps][0] = x;
        data[this._numberOfSteps][1] = y;

        return true;
    }

    private double f(double x, double y){

        String dummyODE = _ODE;
        dummyODE = dummyODE.replace("x", String.valueOf(x));
        dummyODE = dummyODE.replace("y", String.valueOf(y));

        try {
            mParser p = new mParser(dummyODE);
            return p.getResult();
        }
        catch (Exception e) {
            return f(x,y);
           // e.printStackTrace();
        }

        //rückgabewert streitbar
    }

}
