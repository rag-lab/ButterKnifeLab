package com.example.rodrigoaugusto.butterknife;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Recipe_RV_Adapter extends RecyclerView.Adapter<Recipe_RV_Adapter.MyViewHolder> {


    Context mContext;
    List<Recipes> mRecipes;

    //@BindView(R.id.cardImage) ImageView mImageCard;
    //@BindView(R.id.cardTxt) TextView mTexttRecipeName;


    public Recipe_RV_Adapter(Context context, List<Recipes> recipes) {
        this.mContext = context;
        this.mRecipes = recipes;

    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        //private ImageView imgCard;
        private TextView txtRecipeName;



        public MyViewHolder(View itemView) {

            super(itemView);
            //ButterKnife.bind(this, itemView);
            txtRecipeName = (TextView) itemView.findViewById(R.id.cardTxt);

        }

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_recview_recipe, parent, false);

        MyViewHolder vHolder = new MyViewHolder(v);
        return vHolder;

    }

    @Override
    public void onBindViewHolder(Recipe_RV_Adapter.MyViewHolder holder, int position) {

        Recipes recipes = mRecipes.get(position);
        holder.txtRecipeName.setText(recipes.getName());

        //holder..mTexttRecipeName.setText("banana");
        //holder.
        //mTexttRecipeName.setText(mRecipes.get(position).getName());
        //mImageCard.setImageResource(mRecipes.get(position).getImage());


    }

    @Override
    public int getItemCount() {

        //Log.v("RAG", "getItemCount:"+String.valueOf(mRecipes.size()));
        return mRecipes.size();
    }


}

