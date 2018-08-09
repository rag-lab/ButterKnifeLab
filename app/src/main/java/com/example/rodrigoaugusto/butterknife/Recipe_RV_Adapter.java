package com.example.rodrigoaugusto.butterknife;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
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


    //Context mContext;
    List<Recipes> mRecipes;

    //@BindView(R.id.cardImage) ImageView mImageCard;
    //@BindView(R.id.cardTxt) TextView mTexttRecipeName;


    public Recipe_RV_Adapter(List<Recipes> recipes) {
        //this.mContext = mContext;
        this.mRecipes = recipes;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        //private ImageView imgCard;
        private TextView txtRecipeName;
        private CardView mCardView;

        public MyViewHolder(View itemView) {

            super(itemView);
            //ButterKnife.bind(this, itemView);

            mCardView = (CardView) itemView.findViewById(R.id.cardRecipe);
            txtRecipeName = (TextView) itemView.findViewById(R.id.cardTxt);

        }

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recview_recipe, parent, false);

        MyViewHolder vHolder = new MyViewHolder(v);
        return vHolder;

    }

    @Override
    public void onBindViewHolder(Recipe_RV_Adapter.MyViewHolder holder, final int position) {

        Recipes recipes = mRecipes.get(position);
        holder.txtRecipeName.setText(recipes.getName());

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context,StepsActivity.class);
                context.startActivity(intent);

            }
        });

    }



    @Override
    public int getItemCount() {

        Log.v("RAG", "getItemCount:"+String.valueOf(mRecipes.size()));
        return mRecipes.size();
    }


}

