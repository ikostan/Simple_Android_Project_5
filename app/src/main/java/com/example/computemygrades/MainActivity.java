package com.example.computemygrades;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private int labsGrade, projectGrade, midtermGrade, subTotal, finalExpectedGrade, finalGrade;
    private String expectedGrade, resultMessage;
    private EditText labs, project, midterm;
    private Spinner goal;
    private Button btnCalc;
    Intent resultIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //GUI objects
        labs = (EditText) findViewById(R.id.labs);
        project = (EditText) findViewById(R.id.project);
        midterm = (EditText) findViewById(R.id.midterm);
        goal = (Spinner) findViewById(R.id.goal);
        btnCalc = (Button) findViewById(R.id.btnCalc);
        resultIntent = new Intent(this, ResultActivity.class);

        //Calculate button event handler
        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    //Get user input
                    getUserData();
                    //Calculation
                    expectedGrade = goal.getSelectedItem().toString();
                    subTotal = labsGrade + projectGrade + midtermGrade;

                    //Generate final expected grade
                    finalExpectedGrade = calcFinal(expectedGrade);

                    //Result
                    resultMessage = setMessage(finalExpectedGrade, subTotal);

                    //Start a new view
                    resultIntent.putExtra("resultMsg", resultMessage);
                    startActivity(resultIntent);

                }
                catch(IllegalArgumentException e){
                    //Error
                    String error = e.getMessage();

                    if(error.startsWith("For input string")){
                        //Error message
                        Toast.makeText(getApplicationContext(), "One is following values is empty: labs, projrct, midterm or expected final grades. Please reenter", Toast.LENGTH_LONG).show();
                    }
                    else{
                        //Error message
                        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception ex){
                    //Error message
                    Toast.makeText(getApplicationContext(), "Please enter a valid labs, projrct, midterm and expected final grades.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private int calcFinal(String expectedGrade){

        int finalGoal = 0;

        switch(expectedGrade){

            case "A+":
                finalGoal = 95;
                break;
            case "A":
                finalGoal = 90;
                break;
            case "A-":
                finalGoal = 85;
                break;
            case "B+":
                finalGoal = 80;
                break;
            case "B":
                finalGoal = 75;
                break;
            case "B-":
                finalGoal = 70;
                break;
            case "C+":
                finalGoal = 65;
                break;
            case "C":
                finalGoal = 60;
                break;
            case "C-":
                finalGoal = 55;
                break;
            default:
                break;
        }

        return finalGoal;
    }


    private void getUserData(){

        labsGrade = Integer.parseInt(labs.getText().toString());
        projectGrade = Integer.parseInt(project.getText().toString());
        midtermGrade = Integer.parseInt(midterm.getText().toString());

        if(labsGrade > 20 || projectGrade > 20 || midtermGrade > 30){

            throw new IllegalArgumentException("One of the grades you entered is nvalid.");
        }
    }


    private String setMessage(int finalExpectedGrade, int subTotal){

        if(finalExpectedGrade - subTotal > 30){

            return "Sorry but you have to retake the course";
        }
        else if((finalExpectedGrade - subTotal <= 30) && (finalExpectedGrade - subTotal > 0)){

            return String.format("In order to acheive your goal (%d) you have to get %d in the final exam", finalExpectedGrade, (finalExpectedGrade - subTotal));
        }
        else if(finalExpectedGrade - subTotal <= 0){

            return String.format("Congratulations, you already to acheived your goal (%d). Your current grade so far is %d", finalExpectedGrade, (finalExpectedGrade - subTotal), subTotal);
        }

        return "ERROR: please go back and try again.";
    }

}
