package com.example.rodrigoaugusto.butterknife;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class StepsActivity extends AppCompatActivity{


    TextView t;
    @BindView(R.id.cardIngredients) CardView CardIng;
    Recipes recipe;
    LinearLayout layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);

        ButterKnife.bind(this);


        //get the data (recipe class) from the parent activity
        Intent intent = getIntent();
        recipe = intent.getParcelableExtra("recipe");

        setTitle(recipe.getName());

        FragmentSteps mFragmentListSteps = new FragmentSteps();

        //send data to fragment
        Bundle bundle = new Bundle();
        bundle.putParcelable("Recipe", recipe);
        mFragmentListSteps.setArguments(bundle);

        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.gridSteps, mFragmentListSteps).commit();



    }


    @OnClick(R.id.cardIngredients)
    public void Teste6(View view){

        Context context = view.getContext();
        //create Intent
        Intent intent = new Intent(context,Ingredients_activity.class);
        //put recipes inside it
        intent.putExtra("recipe", recipe);
        context.startActivity(intent);

        //Log.v("RAG", "clicked");
        //Snackbar snack = Snackbar.make(layout, "asd", Snackbar.LENGTH_SHORT);
        //snack.show();
    }

}
