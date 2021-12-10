package com.example.covidtracker.Country;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidtracker.MainActivity;
import com.example.covidtracker.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> implements Filterable {
    List<CountryModel> countryModel;
    List<CountryModel> countryModelDuplicate;
    Context context;

    public CountryAdapter(Context context, List<CountryModel> countryModel) {
        this.context = context;
        this.countryModel = countryModel;
        countryModelDuplicate = new ArrayList<>(countryModel);
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<CountryModel> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(countryModelDuplicate);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (CountryModel item : countryModelDuplicate) {
                    if (item.getCountryName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            countryModel.clear();
            countryModel.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    @NonNull
    @Override
    public CountryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.country_simple_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Picasso.get().load(countryModel.get(position).getCountryInformation().getFlag()).into(holder.imageView);
        holder.textView.setText(countryModel.get(position).getCountryName());
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("country", countryModel.get(position).getCountryName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return countryModel.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        CardView root;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.countryFlag);
            textView = itemView.findViewById(R.id.countryName);
            root = itemView.findViewById(R.id.root);
        }
    }
}
