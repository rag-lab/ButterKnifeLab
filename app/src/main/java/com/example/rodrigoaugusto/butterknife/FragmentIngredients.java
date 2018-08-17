package com.example.rodrigoaugusto.butterknife;

import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FragmentIngredients extends Fragment implements android.support.v4.app.LoaderManager.LoaderCallbacks<List<Ingredients>>{

    View v;
    private List<Ingredients> listIngredients = new ArrayList<>();
    Ingredients_RV_Adapter listIngredients_recViewAdapter;
    RecyclerView Ingredients_recview; //recyclcerview do ingrediete
    private static final int thumbLoaderID= 26;
    private Bundle ingredientsBundle = new Bundle(); //usado no loader das recipes

    public FragmentIngredients() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Recipes recipe;
        recipe = new Recipes(Parcel.obtain());

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            recipe = bundle.getParcelable("Recipe");
            //Log.v("RAG", recipe.getName());
            Steps[] step = recipe.getSteps();
            //Log.v("RAG", step[0].getDescription());
        }

        Ingredients[] ingredients = recipe.getIngredients();
        for(int r=0;r<ingredients.length;r++){
            Ingredients tmpIngredient = ingredients[r];
            listIngredients.add(tmpIngredient);
        }


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_ingredients, container, false);

        Ingredients_recview = (RecyclerView) v.findViewById(R.id.listIngredients_recView);
        Ingredients_recview.setLayoutManager(new LinearLayoutManager(getActivity()));

        Ingredients_recview.addItemDecoration(
                new DividerItemDecoration(container.getContext(), DividerItemDecoration.VERTICAL));


        listIngredients_recViewAdapter = new Ingredients_RV_Adapter(listIngredients);
        Ingredients_recview.setAdapter(listIngredients_recViewAdapter);

        return v;
    }


    //
    //
    //LOADER
    @Override
    public Loader<List<Ingredients>> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<Ingredients>> loader, List<Ingredients> data) {

        listIngredients= data;
        listIngredients_recViewAdapter= new Ingredients_RV_Adapter(listIngredients);
        Ingredients_recview.setAdapter(listIngredients_recViewAdapter);

    }

    @Override
    public void onLoaderReset(Loader<List<Ingredients>> loader) {

    }
}
