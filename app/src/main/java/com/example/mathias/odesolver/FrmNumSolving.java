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
        int stepSize;
        double leftBound;
        double rightBound;
        double initialValue;
        double numberOfSteps;
        Vector<String> missingParams = chkInputs();

        if(missingParams.size() == 0) {
            solverID = getSolver();
            stepSize = getStepSize();
            leftBound = getLeftBound();
            rightBound = getRightBound();
            initialValue = getInitialValue();
            numberOfSteps = getNumberOfSteps();
        }
       else {
                showMessageMissingParams(missingParams);
        }
    }
    public void showMessageMissingParams(Vector<String> missingParams) {

        String message = "missing the following params:";
        for (int index = 0; index <= missingParams.size()-1; index++){
            message = message + "\n" + missingParams.elementAt(index);
        }
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context,message,duration);
        toast.show();
    }

    private Vector<String> chkInputs(){
        EditText stepSizeText = (EditText) findViewById(R.id.StepSize);
        EditText leftBoundText = (EditText)findViewById(R.id.leftBound);
        EditText rightBoundText = (EditText)findViewById(R.id.rightBound);
        EditText initialValueText = (EditText)findViewById(R.id.initalValue);
        EditText numberOfStepsText = (EditText)findViewById(R.id.No_Steps);
        ListView solver = (ListView)findViewById(R.id.chooseSolver);

        String none = "";
        Vector<String> missingParams = new Vector<String>(0);
    //check wheather all inputs are made
        if(stepSizeText.getText().toString().equals(none)
                || leftBoundText.getText().toString().equals(none)
                || rightBoundText.getText().toString().equals(none)
                || initialValueText.getText().toString().equals(none)
                || numberOfStepsText.getText().toString().equals(none)
                || solver.getCheckedItemCount() == 0){

            if(stepSizeText.getText().toString().equals(none)){
                missingParams.add(stepSizeText.getHint().toString());
            }
            if(leftBoundText.getText().toString().equals(none)){
                missingParams.add(leftBoundText.getHint().toString());
            }
            if(rightBoundText.getText().toString().equals(none)){
                missingParams.add(rightBoundText.getHint().toString());
            }
            if(initialValueText.getText().toString().equals(none)){
                missingParams.add(initialValueText.getHint().toString());
            }
            if(numberOfStepsText.getText().toString().equals(none)){
                missingParams.add(numberOfStepsText.getHint().toString());
            }
            if(solver.getCheckedItemCount() == 0){
                missingParams.add("Solver");
            }

        }
    return missingParams;
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

    private int getStepSize(){
        EditText steps = (EditText) findViewById(R.id.StepSize);
        return Integer.parseInt(steps.getText().toString());
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

    private void onChange(Bundle savedInstanceState){
        //check filled boxes and fill the unfilled
        EditText stepSizeText = (EditText)findViewById(R.id.StepSize);
        EditText numberOfStepsText = (EditText)findViewById(R.id.No_Steps);
        EditText leftBoundText = (EditText)findViewById(R.id.leftBound);
        EditText rightBoundText = (EditText)findViewById(R.id.rightBound);
        double leftBound = getLeftBound();
        double rightBound = getRightBound();
        int stepSize;
        int numberOfSteps;
        if(stepSizeText.getText().toString() != "" && leftBoundText.getText().toString() != "" && rightBoundText.getText().toString() != "") {
            stepSize = getStepSize();
            setNumberOfSteps((int)(rightBound-leftBound)/stepSize);
        }
        else{
            numberOfSteps = getNumberOfSteps();
            if(numberOfStepsText.getText().toString() != "" && leftBoundText.getText().toString() != "" && rightBoundText.getText().toString() != ""){
                setStepSize((int)(rightBound-leftBound)/numberOfSteps);
            }
        }
    }

    private void setSolverList(){
        //create solverlist and match
     ListView lv = (ListView)findViewById(R.id.chooseSolver);
     ArrayAdapter<String> solverListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice);
     solverListAdapter.add("Euler");
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
