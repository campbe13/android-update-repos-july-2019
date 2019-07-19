package ca.campbell.roomwordsample;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ca.campbell.roomwordsample.Data.Pizza;

public class PizzaRecycAdapter extends RecyclerView.Adapter<PizzaRecycAdapter.PizzaViewHolder> {
    private final String TAG = "RECYCADAPTER";
    private final Context context;

    class PizzaViewHolder extends RecyclerView.ViewHolder {
        private final TextView wordItemView;

        private PizzaViewHolder(View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.textView);
            //wordItemView.setOnClickListener(showPizzaInfo);
            itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent(context, PizzaTextActivity.class);
                    Log.d(TAG, "position "+getAdapterPosition() );
                    intent.putExtra("pizzaname", wordItemView.getText().toString());
                    intent.putExtra("pizzakey", getAdapterPosition());
                    context.startActivity(intent);
                }
            });
        }
    }

    private final LayoutInflater mInflater;
    private List<Pizza> mPizzas; // Cached copy of words

        PizzaRecycAdapter(Context context) {

            this.context = context;
            mInflater = LayoutInflater.from(context); }

    @Override
    public PizzaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new PizzaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PizzaViewHolder holder, int position) {
        if (mPizzas != null) {
            Pizza current = mPizzas.get(position);
            holder.wordItemView.setText(current.getPizza());
        } else {
            // Covers the case of data not being ready yet.
            holder.wordItemView.setText("No Pizza");
        }
    }

    void setPizzas(List<Pizza> pizzas){
        mPizzas = pizzas;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mPizzas has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mPizzas != null)
            return mPizzas.size();
        else return 0;
    }
}