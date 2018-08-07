package com.example.rodrigoaugusto.butterknife;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
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
    private static final int thumbLoaderID= 22;
    private Bundle queryBundle = new Bundle(); //usado no loader das recipes
    private static final String SEARCH_URL = ""; //chave do bundle

    //@BindView(R.id.listRecipes_recView) RecyclerView myrecview;


    public FragmentRecipe() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        RecyclerView myrecview;

        //ButterKnife.bind(container);

        v = inflater.inflate(R.layout.fragment_listrecipe, container, false);

        myrecview = (RecyclerView) v.findViewById(R.id.listRecipes_recView);

        Recipe_RV_Adapter listRecipe_recViewAdapter = new Recipe_RV_Adapter(container.getContext(),lstRecipes);

        myrecview.setLayoutManager(new LinearLayoutManager(getActivity()));
        myrecview.setAdapter(listRecipe_recViewAdapter);

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


    /* method not being used*/
    private void getRecipes() {

        /*
        Steps stepItem = new Steps("12", "short desc", "description", "videourl", "thumbUrl");
        Steps[] stepItens = {stepItem,stepItem};

        Ingredients ingredient = new Ingredients("measure", "ingredient", "qtd");
        Ingredients[] ingredients = {ingredient,ingredient};


        lstRecipes.add(new Recipes("id","servings","name1","image", stepItens, ingredients));
        lstRecipes.add(new Recipes("id","servings","name2","image", stepItens, ingredients));
        lstRecipes.add(new Recipes("id","servings","name3","image", stepItens, ingredients));
        */

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
                //lstRecipes = response.body();

                List<Recipes> recipes = response.body();

                for(Recipes r: recipes){

                    Recipes tmp = new Recipes(r.getId(), r.getServings(), r.getName(), r.getImage(), r.getSteps(), r.getIngredients());
                    lstRecipes.add(tmp);
                }

            }

            @Override
            public void onFailure(Call<List<Recipes>> call, Throwable t) {

                Log.v("RAG", "erro:"+t.toString());

                /*
                Snackbar snackbar = Snackbar
                        .make(layout, t.getMessage(), Snackbar.LENGTH_LONG);
                snackbar.show();
                */
            }

        });

    }


    //
    //LOADER
    //
    @Override
    public Loader<List<Recipes>> onCreateLoader(int id, final Bundle args) {

        Log.v("RAG", "onCreateLoader()");
        return new GetRecipeAsyncTask(getContext());

    }

    @Override
    public void onLoadFinished(Loader<List<Recipes>> loader, List<Recipes> data) {

        Log.v("RAG", "onLoadFinished() data size:" + data.size());
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
        RecyclerView rv;

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
            Log.v("RAG", "GetRecipeAsyncTask onStartLoading():");

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
                call.enqueue(new Callback<List<Recipes>>() {

                    @Override
                    public void onResponse(Call<List<Recipes>> call, Response<List<Recipes>> response) {

                        //Snackbar snackbar = Snackbar
                        //      .make(layout, "success", Snackbar.LENGTH_LONG);
                        //snackbar.show();
                        //lstRecipes = response.body();

                        List<Recipes> recipes = response.body();

                        for(Recipes r: recipes){
                            Recipes tmp = new Recipes(r.getId(), r.getServings(), r.getName(), r.getImage(), r.getSteps(), r.getIngredients());
                            tmpLstRecipes.add(tmp);
                        }

                        Recipe_RV_Adapter adapter = new Recipe_RV_Adapter(getContext(), tmpLstRecipes);
                        adapter.notifyDataSetChanged();

                        Log.v("RAG", "loadInBackground inside retrofit success():"+ tmpLstRecipes.size());

                    }

                    @Override
                    public void onFailure(Call<List<Recipes>> call, Throwable t) {

                        Log.v("RAG", "loadInBackground onFailure():"+ t.toString());
                        /*
                        Snackbar snackbar = Snackbar
                                .make(layout, t.getMessage(), Snackbar.LENGTH_LONG);
                        snackbar.show();
                        */
                    }

                });


            } catch (Exception e) {
                e.printStackTrace();
            }

            Log.v("RAG", "loadInBackground list size:"+tmpLstRecipes.size());

            return tmpLstRecipes;
        }



        @Override
        public void deliverResult(List<Recipes> data) {

            Log.v("RAG", "deliverResult data size:"+data.size());


            // Hold a reference to the old data so it doesn't get garbage collected.
            // We must protect it until the new data has been delivered.
            List<Recipes> oldData = tmpLstRecipes;
            tmpLstRecipes = data;

            if (isStarted()) {
                // If the Loader is in a started state, deliver the results to the
                // client. The superclass method does this for us.
                super.deliverResult(data);
            }

            // Invalidate the old data as we don't need it any more.
            if (oldData != null && oldData != data) {
                //releaseResources(oldData);
            }

        }

    }

}


