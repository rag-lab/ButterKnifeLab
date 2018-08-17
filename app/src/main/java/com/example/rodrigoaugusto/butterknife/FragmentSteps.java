package com.example.rodrigoaugusto.butterknife;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FragmentSteps extends Fragment  implements android.support.v4.app.LoaderManager.LoaderCallbacks<List<Steps>>{


    View v;
    private List<Steps> lstSteps = new ArrayList<>();
    Steps_RV_Adapter listSteps_recViewAdapter;
    RecyclerView Steps_recview; //recyclcerview das receitas
    private static final int thumbLoaderID= 23;
    private Bundle stepsBundle = new Bundle(); //usado no loader das recipes


    public FragmentSteps() {

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

        Steps[] steps = recipe.getSteps();
        for(int r=0;r<steps.length;r++){
            Steps tmpStep = steps[r];
            lstSteps.add(tmpStep);
        }


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_steps, container, false);

        Steps_recview = (RecyclerView) v.findViewById(R.id.listSteps_recView);
        Steps_recview.setLayoutManager(new LinearLayoutManager(getActivity()));

        Steps_recview.addItemDecoration(
                new DividerItemDecoration(container.getContext(), DividerItemDecoration.VERTICAL));


        listSteps_recViewAdapter = new Steps_RV_Adapter(lstSteps);
        Steps_recview.setAdapter(listSteps_recViewAdapter);

        return v;

    }


    //
    //LOADER
    //
    @Override
    public Loader<List<Steps>> onCreateLoader(int id, final Bundle args) {

        Log.v("RAG", "onCreateLoader() id:" + id);

        return null;
    }


    @Override
    public void onLoadFinished(Loader<List<Steps>> loader, List<Steps> data)
    {
        lstSteps = data;
        listSteps_recViewAdapter = new Steps_RV_Adapter(lstSteps);
        Steps_recview.setAdapter(listSteps_recViewAdapter);

    }


    @Override
    public void onLoaderReset(Loader<List<Steps>> loader) {
        //Log.v("RAG", "onLoaderReset()");
    }


    /*
    //END LOADER
    */



}
