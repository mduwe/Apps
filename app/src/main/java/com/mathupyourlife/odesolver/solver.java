package com.mathupyourlife.odesolver;

/**
 * Created by mathias on 09.10.15.
 */
public class solver{

    private int _numberOfSteps;
    private double _stepSize;
    private double _leftBound;
    private double _rightBound;
    private double _initialValue;

    private String _ODE;
    private String _solver;

    public double[][] data;
    public int solverID;
    //Methods

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
                case 1: solved = this.startNumSolvingWithExplEuler();
                case 2: solved = this.startNumSolvingWithImplEuler();
                case 3: solved = this.startNumSolvingWithRungeKutta();
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
    //Start the solving with explicit euler
    // u(i+1) = u(i) + h f(xi,ui)
    private boolean startNumSolvingWithExplEuler(){

        return true;
    }

    private boolean startNumSolvingWithImplEuler(){

        return true;
    }

    private boolean startNumSolvingWithRungeKutta(){

        //implements the 3step rungekutta method
        double k1,k2,k3;
        this.data = new double[this._numberOfSteps][2];
        double h = this._stepSize;

        double x = this._leftBound;
        double y = this._initialValue;

        data[0][0] = x;
        data[0][1] = y;
        for(int i = 0; i < this._numberOfSteps-1 ; i++){
            if(x <= this._rightBound){
                //save data
                data[i][0] = x;
                data[i][1] = y;

                k1 = f(x,y);
                k2 = f(x + 0.5*h, y + 0.5*k1);
                k3 = f(x + h, y - h*k1 +2.*h*k2);

                y = y + (1./6.)*h*(k1 + 4.*k2 + k3);

                //next
                x = x + h;
            }
        }
        data[this._numberOfSteps-1][0] = x;
        data[this._numberOfSteps-1][1] = y;

        return true;
    }

    private double f(double x, double y){

        return x+y;
    }


}
