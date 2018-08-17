package com.example.rodrigoaugusto.butterknife;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Steps_RV_Adapter extends RecyclerView.Adapter<Steps_RV_Adapter.MyViewHolder> {


    List<Steps> mSteps;

    public Steps_RV_Adapter(List<Steps> steps) {
        this.mSteps = steps;
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder{

        //private ImageView imgCard;
        private TextView mTxtStepName;
        private CardView mCardStep;

        public MyViewHolder(View itemView) {

            super(itemView);
            //ButterKnife.bind(this, itemView);
            mCardStep = itemView.findViewById(R.id.cardSteps);
            mTxtStepName = itemView.findViewById(R.id.cardStepTxt);

        }

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recview_steps, parent, false);

        MyViewHolder vHolder = new MyViewHolder(v);
        return vHolder;

    }



    @Override
    public void onBindViewHolder(Steps_RV_Adapter.MyViewHolder holder, int position) {

        final Steps step = mSteps.get(position);

        holder.mTxtStepName.setText(step.getShortDescription());

        holder.mCardStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                Context context = v.getContext();
                //create Intent
                Intent intent = new Intent(context,StepsActivity.class);
                //put recipes inside it
                intent.putExtra("recipe", recipes);
                context.startActivity(intent);
                */
                Log.v("RAG","clicked");
            }
        });

    }

    @Override
    public int getItemCount() {

        //Log.v("RAG", "getItemCount:"+String.valueOf(mSteps.size()));
        return mSteps.size();
    }

}
