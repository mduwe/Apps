package com.mathupyourlife.odesolver;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;


public class numSolveAfterEdit extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

   private double[][] data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_num_solve_after_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton solvingButton = (FloatingActionButton) findViewById(R.id.startNumSolvingButton);
        solvingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if(startNumSolving()){
/*
                  Mediator mediator = new Mediator("success");
                  mediator.showToasterMessage(getApplicationContext(),Toast.LENGTH_SHORT);
*/
              }
            }
        });

        FloatingActionButton editInputsButton = (FloatingActionButton) findViewById(R.id.editInputsButton);
        editInputsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), inputs_num_solve.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent editIntent = getIntent();

        TextView steps = (TextView) findViewById(R.id.stepSizeInfo);
        TextView leftBound = (TextView)findViewById(R.id.leftBoundInfo);
        TextView rightBound = (TextView)findViewById(R.id.rightBoundInfo);
        TextView initialValue = (TextView)findViewById(R.id.initialValueInfo);
        TextView numberOfSteps = (TextView)findViewById(R.id.numberOfStepsInfo);
        TextView solver = (TextView)findViewById(R.id.solverInfo);
        TextView ODE = (TextView)findViewById(R.id.functionInfo);


        steps.setText(editIntent.getStringExtra("stepSize"));
        numberOfSteps.setText(editIntent.getStringExtra("numberOfSteps"));
        leftBound.setText(editIntent.getStringExtra("leftBound"));
        rightBound.setText(editIntent.getStringExtra("rightBound"));
        initialValue.setText(editIntent.getStringExtra("initialValue"));
        solver.setText(editIntent.getStringExtra("solver"));
        ODE.setText(editIntent.getStringExtra("ODE"));

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

  /*  public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.num_solve_after_edit, menu);
        return true;
    }
*/
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.mainMenu) {
            showFrmMain();
        }
        else if (id == R.id.numSolve) {

        }
        else if (id == R.id.symSolve) {
            showFrmSymSolve();
        }
        else if (id == R.id.nav_share) {

        }
        else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showFrmMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void showFrmSymSolve(){
        Intent intent = new Intent(this, symSolve.class);
        startActivity(intent);
    }

       private boolean startNumSolving(){
        //initialize all components

        TextView steps = (TextView) findViewById(R.id.stepSizeInfo);
        TextView leftBound = (TextView)findViewById(R.id.leftBoundInfo);
        TextView rightBound = (TextView)findViewById(R.id.rightBoundInfo);
        TextView initialValue = (TextView)findViewById(R.id.initialValueInfo);
        TextView numberOfSteps = (TextView)findViewById(R.id.numberOfStepsInfo);
        TextView solver = (TextView)findViewById(R.id.solverInfo);
        TextView ODE = (TextView)findViewById(R.id.functionInfo);

        //get all values
        double _leftBound = parseDouble(leftBound.getText().toString().trim());
        double _rightBound = parseDouble(rightBound.getText().toString().trim());
        double _stepSize = parseDouble(steps.getText().toString().trim());
        int _numberOfSteps = parseInt(numberOfSteps.getText().toString().trim());
        double _initialValue = parseDouble(initialValue.getText().toString().trim());
        String _ODE = ODE.getText().toString().trim();
        String _solver = solver.getText().toString();

       boolean success;

       //start solving
       solver numericalSolver = new solver(_stepSize ,_leftBound, _rightBound,
                                    _initialValue,_numberOfSteps, _ODE, _solver);

       success = numericalSolver.startSolving();
       this.data = numericalSolver.data;

       Mediator mediator = new Mediator(valueOf(this.data[this.data.length-1][0]));
       Mediator medi = new Mediator(valueOf(this.data[this.data.length-1][1]));

       mediator.showToasterMessage(getApplicationContext(),Toast.LENGTH_LONG);
       medi.showToasterMessage(getApplicationContext(),Toast.LENGTH_LONG);

       return success;
    }

}
