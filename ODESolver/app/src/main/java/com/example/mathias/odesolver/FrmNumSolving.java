package com.example.mathias.odesolver;

import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.Vector;
import android.text.Editable;

import static java.lang.Integer.valueOf;

public class FrmNumSolving extends AppCompatActivity {
    private enum solverOSM {
        EXPLEULER, IMPLEULER, RUNGEKUTTA;
    }

    public void startNumSolving(View view) {
        //functionparser!!

        int solverID;
        double stepSize;
        double leftBound;
        double rightBound;
        double initialValue;
        int numberOfSteps;
        //Mediator mediator = new Mediator();

        if(chkInputs()) {
            solverID = getSolver();
            stepSize = getStepSize();
            leftBound = getLeftBound();
            rightBound = getRightBound();
            initialValue = getInitialValue();
            numberOfSteps = getNumberOfSteps();
        }
    }

    private Boolean chkInputs(){
        //plausibilty check and check wheather all inputs are made
        EditText stepSizeText = (EditText) findViewById(R.id.StepSize);
        EditText leftBoundText = (EditText)findViewById(R.id.leftBound);
        EditText rightBoundText = (EditText)findViewById(R.id.rightBound);
        EditText initialValueText = (EditText)findViewById(R.id.initalValue);
        EditText numberOfStepsText = (EditText)findViewById(R.id.No_Steps);
        ListView solver = (ListView)findViewById(R.id.chooseSolver);

        String none = "";
        //check inputs made
        if(stepSizeText.getText().toString().equals(none)
                || leftBoundText.getText().toString().equals(none)
                || rightBoundText.getText().toString().equals(none)
                || initialValueText.getText().toString().equals(none)
                || numberOfStepsText.getText().toString().equals(none)
                || solver.getCheckedItemCount() == 0) {
            //create a new instance of mediator
            Mediator mediator = new Mediator();
            Vector<String> missingParams = new Vector<String>(0);

            if (stepSizeText.getText().toString().equals(none)) {
                missingParams.add(stepSizeText.getHint().toString());
            }
            if (initialValueText.getText().toString().equals(none)) {
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
            if (solver.getCheckedItemCount() == 0) {
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
    return true;
    }

    private int getSolver(){
        ListView solverList = (ListView)findViewById(R.id.chooseSolver);
        String solver = solverList.getAdapter().getItem(solverList.getCheckedItemPosition()).toString();

        int solverID = 0;
        switch(solver){
            case "expl. Euler": solverID = 1;
            case "impl. Euler": solverID = 2;
            case "RungeKutta": solverID = 3;
        }
        return solverID;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_num_solving);
        //fill Solver List
        setSolverList();

    }

    private void setSolverList(){
        //create solverlist and match
        ListView lv = (ListView)findViewById(R.id.chooseSolver);
        ArrayAdapter<String> solverListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice);
        solverListAdapter.add("Expl.Euler");
        solverListAdapter.add("Impl.Euler");
        solverListAdapter.add("RungeKutta");
         lv.setAdapter(solverListAdapter);
     }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_frm_num_solving, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
