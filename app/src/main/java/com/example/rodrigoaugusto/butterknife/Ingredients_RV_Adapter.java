package com.example.rodrigoaugusto.butterknife;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class Ingredients_RV_Adapter extends RecyclerView.Adapter<Ingredients_RV_Adapter.MyViewHolder> {


    List<Ingredients> mIngredients;

    public Ingredients_RV_Adapter(List<Ingredients> ingredients) {
        this.mIngredients = ingredients;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        //private ImageView imgCard;
        private TextView mTxtIngredientName;

        public MyViewHolder(View itemView) {

            super(itemView);
            //ButterKnife.bind(this, itemView);
            mTxtIngredientName = itemView.findViewById(R.id.ingredientTxt);

        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recview_ingredients, parent, false);

        MyViewHolder vHolder = new MyViewHolder(v);
        return vHolder;

    }

    @Override
    public void onBindViewHolder(Ingredients_RV_Adapter.MyViewHolder holder, int position) {

        final Ingredients ingredients = mIngredients.get(position);

        String txtIngredient = ingredients.getQuantity() + " " + ingredients.getMeasure() + " - " + ingredients.getIngredient();
        holder.mTxtIngredientName.setText(txtIngredient);
    }



    @Override
    public int getItemCount() {
        return mIngredients.size();
    }


}
