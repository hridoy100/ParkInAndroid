package com.example.parkin.Add;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.parkin.R;
import com.example.parkin.Stepper.StepFragmentSample;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

public class AddGarageStepped extends AppCompatActivity implements StepperLayout.StepperListener{
    private StepperLayout mStepperLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_garage_stepped);
        mStepperLayout = (StepperLayout) findViewById(R.id.stepperLayout);
        mStepperLayout.setAdapter(new MyStepperAdapter(getSupportFragmentManager(), this));
        mStepperLayout.setListener(this);

    }
    @Override
    public void onCompleted(View completeButton) {
        Toast.makeText(this, "onCompleted!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(VerificationError verificationError) {
        Toast.makeText(this, "onError! -> " + verificationError.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStepSelected(int newStepPosition) {
        //Toast.makeText(this, "onStepSelected! -> " + newStepPosition, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onReturn() {
        finish();
    }


    public static class MyStepperAdapter extends AbstractFragmentStepAdapter {

        String CURRENT_STEP_POSITION_KEY = "step_pos";
        Context context;

        public MyStepperAdapter(FragmentManager fm, Context context) {

            super(fm, context);
            this.context = context;
        }

        @Override
        public Step createStep(int position) {
            final StepFragmentSample step = new StepFragmentSample();
            Bundle b = new Bundle();
            b.putInt(CURRENT_STEP_POSITION_KEY, position);
            step.setArguments(b);
            return step;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @NonNull
        @Override
        public StepViewModel getViewModel(@IntRange(from = 0) int position) {
            String str[] = new String[getCount()];
            str = new String[]{"step 1", "step 2", "step 3"};
            StepViewModel.Builder builder = new StepViewModel.Builder(context)
                    .setTitle(str[position]);

            switch (position) {
                case 0:
                    builder
                            .setEndButtonLabel("This way")
                            .setBackButtonLabel("Cancel")
                            //.setNextButtonEndDrawableResId(R.drawable.ic_arrow)
                            .setBackButtonStartDrawableResId(StepViewModel.NULL_DRAWABLE);
                    Toast.makeText(context, "onStepSelected! -> " + position, Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    builder
                            .setEndButtonLabel("Go To Summary")
                            .setBackButtonLabel("Go to first");
                            //.setBackButtonStartDrawableResId(R.drawable.ic_arrow_back_black_24dp);

                    Toast.makeText(context, "onStepSelected! -> " + position, Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    builder
                            .setBackButtonLabel("Go back")
                            .setEndButtonLabel("I'm done!");

                    Toast.makeText(context, "onStepSelected! -> " + position, Toast.LENGTH_SHORT).show();
                    break;
                default:
                    System.out.println("default");
                    throw new IllegalArgumentException("Unsupported position: " + position);
            }
            return builder.create();
        }


    }

}
