package com.mathupyourlife.odesolver;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.view.MenuItem;
import android.widget.PopupMenu;

import java.util.Vector;

public class inputs_num_solve extends AppCompatActivity {

    public int _numberOfSteps;
    public String _solver;
    public double _stepSize;
    public double _leftBound;
    public double _rightBound;
    public double _initialValue;

    private void getInputs(){
        EditText steps = (EditText) findViewById(R.id.StepSize);
        EditText leftBound = (EditText)findViewById(R.id.leftBound);
        EditText rightBound = (EditText)findViewById(R.id.rightBound);
        EditText initialValue = (EditText)findViewById(R.id.initalValue);
        EditText numberOfSteps = (EditText)findViewById(R.id.No_Steps);
        Button solverButton = (Button) findViewById(R.id.solverButton);

        _numberOfSteps = Integer.parseInt(numberOfSteps.getText().toString());
        _stepSize = Double.valueOf(steps.getText().toString());
        _leftBound = Double.parseDouble(leftBound.getText().toString());
        _rightBound = Double.parseDouble(rightBound.getText().toString());
        _initialValue = Double.valueOf(initialValue.getText().toString());
        _solver = String.valueOf(solverButton.getText());
    }
    public void closeAndSafe(){
        //save instance etc.
        if(chkInputs()){
            Intent numSolveActivity = new Intent(getApplicationContext(), numSolveAfterEdit.class);

            //give parameters to numSolve
            numSolveActivity.putExtra("numberOfSteps", String.valueOf(getNumberOfSteps()));
            numSolveActivity.putExtra("stepSize", String.valueOf(getStepSize()));
            numSolveActivity.putExtra("leftBound", String.valueOf(getLeftBound()));
            numSolveActivity.putExtra("rightBound", String.valueOf(getRightBound()));
            numSolveActivity.putExtra("initialValue", String.valueOf(getInitialValue()));
            numSolveActivity.putExtra("solver", getSolver());
            numSolveActivity.putExtra("ODE", getODE());

            //Start numSolve Activity
            startActivity(numSolveActivity);
        }

    }

    private String getODE(){
        EditText ODEText = (EditText)findViewById(R.id.ODE);
        return ODEText.getText().toString();

    }
    private String getSolver(){
        Button solverButton = (Button) findViewById(R.id.solverButton);
        return solverButton.getText().toString();
    }

    private double getStepSize(){
        EditText steps = (EditText) findViewById(R.id.StepSize);
        return Double.valueOf(steps.getText().toString());
    }
    private void setStepSize(int _stepSize){
        EditText steps = (EditText)findViewById(R.id.StepSize);
        steps.setText(String.valueOf(_stepSize));
    }

    private double getLeftBound(){
        EditText leftBound = (EditText)findViewById(R.id.leftBound);
        return Double.parseDouble(leftBound.getText().toString());
    }

    private double getRightBound(){
        EditText rightBound = (EditText)findViewById(R.id.rightBound);
        return Double.parseDouble(rightBound.getText().toString());
    }

    private double getInitialValue(){
        EditText initialValue = (EditText)findViewById(R.id.initalValue);
        return Double.valueOf(initialValue.getText().toString());
    }

    private int getNumberOfSteps(){
        EditText numberOfSteps = (EditText)findViewById(R.id.No_Steps);
        return Integer.parseInt(numberOfSteps.getText().toString());
    }

    private void setNumberOfSteps(int _numberOfSteps){
        EditText numberOfSteps = (EditText)findViewById(R.id.No_Steps);
        numberOfSteps.setText(String.valueOf(_numberOfSteps));
    }

    public Boolean chkInputs(){
        //plausibilty check and check wheather all inputs are made
        //if all inputs are OK -> save the inputs
        EditText stepSizeText = (EditText) findViewById(R.id.StepSize);
        EditText leftBoundText = (EditText)findViewById(R.id.leftBound);
        EditText rightBoundText = (EditText)findViewById(R.id.rightBound);
        EditText initialValueText = (EditText)findViewById(R.id.initalValue);
        EditText numberOfStepsText = (EditText)findViewById(R.id.No_Steps);
        Button solverButton = (Button)findViewById(R.id.solverButton);
        String none = "";
        //check inputs made

        if(stepSizeText.getText().toString().equals(none)
                || leftBoundText.getText().toString().equals(none)
                || rightBoundText.getText().toString().equals(none)
                || initialValueText.getText().toString().equals(none)
                || numberOfStepsText.getText().toString().equals(none)
                || solverButton.getText().equals("Solver")) {

            Mediator mediator = new Mediator();
            Vector<String> missingParams = new Vector<String>(0);

            if (stepSizeText.getText().toString().equals(none)) {
                missingParams.add(stepSizeText.getHint().toString());
            }
            if (initialValueText.getText().toString().equals(none)){
                missingParams.add(initialValueText.getHint().toString());
            }
            if (leftBoundText.getText().toString().equals(none)) {
                missingParams.add(leftBoundText.getHint().toString());
            }
            if (rightBoundText.getText().toString().equals(none)) {
                missingParams.add(rightBoundText.getHint().toString());
            }
            if (numberOfStepsText.getText().toString().equals(none)) {
                missingParams.add(numberOfStepsText.getHint().toString());
            }
            if (solverButton.getText().equals("Solver")) {
                missingParams.add("Solver");
            }
            mediator._message = "Missing the following params:";
            mediator.showToasterMessage(missingParams, getApplicationContext(), Toast.LENGTH_SHORT);
            return false;
        }
        //Plausibility check, e. g. left bound<right bound
        if(Double.valueOf(leftBoundText.getText().toString()) >= Double.valueOf(rightBoundText.getText().toString())){
            Mediator mediator = new Mediator();
            Vector<String> messageContainer = new Vector<String>(0);

            mediator._message = "Left bound cannot be greater or equal than right Bound, please correct";
            mediator.showToasterMessage(messageContainer, getApplicationContext(), Toast.LENGTH_SHORT);
            return false;
        }

        //Everything seems fine, get the params
        _numberOfSteps = Integer.parseInt(numberOfStepsText.getText().toString());
        _stepSize = Double.valueOf(stepSizeText.getText().toString());
        _leftBound = Double.parseDouble(leftBoundText.getText().toString());
        _rightBound = Double.parseDouble(rightBoundText.getText().toString());
        _initialValue = Double.valueOf(initialValueText.getText().toString());
        _solver = String.valueOf(solverButton.getText());
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inputs_num_solve);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton camIcon = (FloatingActionButton) findViewById(R.id.cameraIcon);
        camIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        FloatingActionButton saveChangesButton = (FloatingActionButton) findViewById(R.id.closeInputs);
        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeAndSafe();
            }
        });

    }

    public void onSolverButtonClick(View view) {
        PopupMenu solverPopup = new PopupMenu(this, view);
        solverPopup.getMenuInflater().inflate(R.menu.solver, solverPopup.getMenu());

        solverPopup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                Button solverButton = (Button) findViewById(R.id.solverButton);
                solverButton.setText(String.valueOf(item.getTitle()));
                return true;
            }
        });
        solverPopup.show();
    }

}
