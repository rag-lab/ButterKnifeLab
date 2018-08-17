package com.example.rodrigoaugusto.butterknife;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
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
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentRecipe extends Fragment implements android.support.v4.app.LoaderManager.LoaderCallbacks<List<Recipes>>{

    View v;

    private List<Recipes> lstRecipes = new ArrayList<>();
    Recipe_RV_Adapter listRecipe_recViewAdapter;
    RecyclerView myrecview; //recyclcerview das receitas
    private static final int thumbLoaderID= 22;
    private Bundle queryBundle = new Bundle(); //usado no loader das recipes
    private static final String SEARCH_URL = ""; //chave do bundle

    //@BindView(R.id.listRecipes_recView) RecyclerView myrecview;

    public FragmentRecipe() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        //ButterKnife.bind(container);

        v = inflater.inflate(R.layout.fragment_listrecipe, container, false);

        myrecview = (RecyclerView) v.findViewById(R.id.listRecipes_recView);

        listRecipe_recViewAdapter = new Recipe_RV_Adapter(lstRecipes);

        myrecview.setAdapter(listRecipe_recViewAdapter);

        myrecview.setLayoutManager(new LinearLayoutManager(getActivity()));

        myrecview.addItemDecoration(
                new DividerItemDecoration(container.getContext(), DividerItemDecoration.VERTICAL));

        return v;

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        LoaderManager loaderManager = getLoaderManager();
        Loader<String> thumbsLoader = loaderManager.getLoader(thumbLoaderID);

        queryBundle.putString(SEARCH_URL, Api.BASEURL);

        if (thumbsLoader == null) {
            loaderManager.initLoader(thumbLoaderID, queryBundle, this);
        } else {
            loaderManager.restartLoader(thumbLoaderID, queryBundle, this);
        }


    }


    //
    //LOADER
    //
    @Override
    public Loader<List<Recipes>> onCreateLoader(int id, final Bundle args) {

        //execute this not on the ami thread
        return new GetRecipeAsyncTask(getContext());
    }

    @Override
    public void onLoadFinished(Loader<List<Recipes>> loader, List<Recipes> data)
    {
        lstRecipes = data;
        listRecipe_recViewAdapter = new Recipe_RV_Adapter(lstRecipes);
        myrecview.setAdapter(listRecipe_recViewAdapter);

    }


    @Override
    public void onLoaderReset(Loader<List<Recipes>> loader) {
        //Log.v("RAG", "onLoaderReset()");
    }


    /*
    //END LOADER
    */



    static class GetRecipeAsyncTask extends AsyncTaskLoader<List<Recipes>>
    {

        List<Recipes> tmpLstRecipes;


        public GetRecipeAsyncTask(Context context) {
            super(context);
        }


        @Override
        protected void onStartLoading() {

            /*
            super.onStartLoading();
            forceLoad();
            */

            //if (args == null) return;
            //Log.v("RAG", "GetRecipeAsyncTask onStartLoading():");


            //pega do cache ou carrega
            if (tmpLstRecipes != null) {
                deliverResult(tmpLstRecipes);
            } else {
                this.forceLoad();
            }

        }


        @Override
        public List<Recipes> loadInBackground() {

            tmpLstRecipes = new ArrayList<>();

            try {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Api.BASEURL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Api api = retrofit.create(Api.class);

                Call<List<Recipes>> call = api.getRecipes();
                tmpLstRecipes = call.execute().body();

                return tmpLstRecipes;

            } catch (Exception e) {

                e.printStackTrace();
                return null;
            }

        }



        @Override
        public void deliverResult(List<Recipes> data) {

            // Hold a reference to the old data so it doesn't get garbage collected.
            // We must protect it until the new data has been delivered.
            List<Recipes> oldData = tmpLstRecipes;
            tmpLstRecipes = data;

            if (isStarted()) {
                // If the Loader is in a started state, deliver the results to the
                // client. The superclass method does this for us.
                super.deliverResult(data);
            }

        }

    }

}


