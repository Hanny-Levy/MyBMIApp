package com.example.mybmi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RadioGroup  bodyFrameGroup ;
    private WeightStatus weightStatus ;
    double height ;
    private double actualWeight , age , bmiResult , slimness , idealWeight;
    private TextView bmiView , weightStatusView , heightView , idealWeightView;
    private Button submit , clear ;
    private EditText ageField ,actualWeightField ;
    private SeekBar heightBar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bmiView = findViewById(R.id.bmiView);
        weightStatusView = findViewById(R.id.weightStatusView);
        idealWeightView=findViewById(R.id.idealWeightView);
        submit = findViewById(R.id.submitButton);
        submit.setOnClickListener(this);
        clear = findViewById(R.id.clearButton);
        clear.setOnClickListener(this);
        bodyFrameGroup=findViewById(R.id.bodyFrameGroup);
        bmiView.setVisibility(View.INVISIBLE);
        weightStatusView.setVisibility(View.INVISIBLE);
        idealWeightView.setVisibility(View.INVISIBLE);
        ageField = findViewById(R.id.ageField);
        actualWeightField = findViewById(R.id.actualWeightField);
        heightView=findViewById(R.id.heightView);
        heightBar=findViewById(R.id.heightSeekBar);
        heightBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int value, boolean fromUser) {
                heightView.setText("Height :" + value + " cm");
                height=value/100 ;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        }) ;
        bodyFrameGroup=findViewById(R.id.bodyFrameGroup);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.submitButton :
                this.setBmiResultAndWeightStatus();
                break;

            case R.id.clearButton:
                this.clear();
                break;

        }

    }

    private void clear(){
        EditText firstNameField = findViewById(R.id.firstNameField);
        EditText lastNameField = findViewById(R.id.lastNameField);
        RadioGroup genderGroup=findViewById(R.id.genderGroup);
        firstNameField.setText(getString(R.string.defaultValue));
        lastNameField.setText(getString(R.string.defaultValue));
        ageField.setText(getString(R.string.defaultValue));
        actualWeightField.setText(getString(R.string.defaultValue));
        bmiView.setText(getString(R.string.bmiView));
        weightStatusView.setText(getString(R.string.weightStatusView));
        idealWeightView.setText(getString(R.string.idealWeightView));
        genderGroup.clearCheck();
        bodyFrameGroup.clearCheck();
        heightView.setText(getString(R.string.heightView));
        heightBar.setProgress(140);


    }

    private void setBmiResultAndWeightStatus (){
        actualWeight = Double.parseDouble(String.valueOf(actualWeightField.getText()));
        age = Double.parseDouble(String.valueOf(ageField.getText()));
        this.setSlimness();
        bmiResult= (actualWeight / (Math.pow(height, 2)));
        bmiView.setText(getString(R.string.bmiView) + String.format("%.2f",bmiResult));
        this.setWeightStatus();
        weightStatusView.setText(getString(R.string.weightStatusView) + weightStatus.toString());
        height *= 100;
        idealWeight = (((height - 100 + (age / 10)) * 0.9 * slimness));
        idealWeightView.setText(getString(R.string.idealWeightView)+  String.format("%.2f", idealWeight));

        bmiView.setVisibility(View.VISIBLE);
        weightStatusView.setVisibility(View.VISIBLE);
        idealWeightView.setVisibility(View.VISIBLE);

    }

    private void setSlimness() {
        switch (bodyFrameGroup.getCheckedRadioButtonId()) {
            case R.id.smallButton:
                slimness = 0.9;
                break;
            case R.id.mediumButton:
                slimness = 1;
                break;
            case R.id.largeButton:
                slimness = 1.1;
                break;

        }
    }

    private void setWeightStatus(){
        if (bmiResult<15){
            weightStatus=WeightStatus.Anorexic;
        }
        if (bmiResult>=15 && bmiResult<18.5){
            weightStatus=WeightStatus.Underweight;
        }
        if (bmiResult>=18.5 &&bmiResult<=24.9){
            weightStatus=WeightStatus.Normal;
        }
        if (bmiResult>=25 &&bmiResult<=29.9){
            weightStatus=WeightStatus.Overweight;
        }
        if (bmiResult>=30 &&bmiResult<=35){
            weightStatus=WeightStatus.Obese;
        }
        if (bmiResult>35)
            weightStatus=WeightStatus.Extreme_Obese;
    }
    }

