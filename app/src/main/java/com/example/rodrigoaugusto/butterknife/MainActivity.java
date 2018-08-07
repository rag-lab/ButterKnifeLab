package com.example.rodrigoaugusto.butterknife;

import android.support.v4.app.Fragment;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.myText) TextView text1;
    @BindView(R.id.btnSnack) Button btSnack;
    @BindView(R.id.btnCallback) Button btnCallBack;
    @BindView(R.id.btnRetrofit) Button btnRetro;
    @BindView(R.id.constrainLayout) ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        FragmentRecipe mFragmentListRecipes = new FragmentRecipe();
        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();

        fm.beginTransaction().add(R.id.gridRecipes, mFragmentListRecipes).commit();




    }


    @OnClick(R.id.btnSnack)
    public void Teste(){

        Snackbar snackbar = Snackbar
                .make(layout, "Welcome to AndroidHive", Snackbar.LENGTH_LONG);
        snackbar.show();
    }


    @OnClick(R.id.btnCallback)
    public void Teste1(){

        Snackbar snackbar = Snackbar
                .make(layout, "Message is deleted", Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar snackbar1 = Snackbar.make(layout, "Message is restored!", Snackbar.LENGTH_SHORT);
                        snackbar1.show();
                    }
                });
        snackbar.show();
    }


    @OnClick(R.id.btnRetrofit)
    public void Teste2(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        Call<List<Recipes>> call = api.getRecipes();

        call.enqueue(new Callback<List<Recipes>>() {

            @Override
            public void onResponse(Call<List<Recipes>> call, Response<List<Recipes>> response) {

                //Snackbar snackbar = Snackbar
                //      .make(layout, "success", Snackbar.LENGTH_LONG);
                //snackbar.show();
                List<Recipes> recipes = response.body();

                for(Recipes r: recipes){
                    Log.v("RAG", r.getName());
                }
            }

            @Override
            public void onFailure(Call<List<Recipes>> call, Throwable t) {
                Snackbar snackbar = Snackbar
                        .make(layout, t.getMessage(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }

        });

    }

}
