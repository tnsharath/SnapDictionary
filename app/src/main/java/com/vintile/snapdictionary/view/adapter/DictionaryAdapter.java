package com.vintile.snapdictionary.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vintile.snapdictionary.R;
import com.vintile.snapdictionary.model.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sharath on 2020/02/05
 **/
public class DictionaryAdapter extends RecyclerView.Adapter<DictionaryAdapter.DictionaryHolder>{

    private final List<Result> result;

    public DictionaryAdapter() {
        this.result = new ArrayList<>();
    }

    @NonNull
    @Override
    public DictionaryAdapter.DictionaryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dictionary, parent, false);
        return new DictionaryHolder(root);
    }


    @Override
    public void onBindViewHolder(@NonNull DictionaryHolder holder, int position) {
        final Result res = result.get(position);
        if (res.getDefinition() != null){
            String def;
            def = res.getDefinition().substring(0, 1).toUpperCase() + res.getDefinition().substring(1);
            def = "      "+ def + ".";
             holder.tvDefinition.setText(def);
             holder.tvDefinition.setVisibility(View.VISIBLE);
        }

        if (res.getPartOfSpeech() != null) {
            holder.tvPOS.setText(res.getPartOfSpeech());
            holder.tvPOS.setVisibility(View.VISIBLE);
        }
        setExample(holder, res);
        setSynonym(holder, res);

    }

    private void setExample(@NonNull DictionaryHolder holder, Result res) {
        int i = 0;
        if ( res.getExamples() != null && !res.getExamples().isEmpty()){
            holder.tvExampleTitle.setVisibility(View.VISIBLE);
            StringBuilder stringBuilder = new StringBuilder();
            for (String example: res.getExamples()){
                ++i;
                stringBuilder.append("\"").append(example).append("\"");
                if (i < res.getExamples().size())
                    stringBuilder.append("\n");
            }
            holder.tvExample.setText(stringBuilder.toString());
            holder.tvExample.setVisibility(View.VISIBLE);
        }
    }

    private void setSynonym(@NonNull DictionaryHolder holder, Result res) {
        int i = 0;
        if (res.getSynonyms() != null && !res.getSynonyms().isEmpty()){
            StringBuilder stringBuilder = new StringBuilder();
            for (String synonym: res.getSynonyms()){
                ++i;
                stringBuilder.append("\"").append(synonym).append("\"");
                if (i < res.getSynonyms().size())
                    stringBuilder.append(", ");
            }
            holder.tvSynonyms.setText(stringBuilder.toString());
            holder.tvSynonyms.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return result.size();
    }


    public void addAll(List<Result> results) {
        result.addAll(results);
        notifyDataSetChanged();
    }

    class DictionaryHolder extends RecyclerView.ViewHolder {

        final TextView tvDefinition;
        final TextView tvPOS;
        final TextView tvSynonyms;
        final TextView tvExampleTitle;
        final TextView tvExample;
        private DictionaryHolder(View itemView) {
            super(itemView);
            tvDefinition = itemView.findViewById(R.id.tvDefinition);
            tvPOS = itemView.findViewById(R.id.tvPOS);
            tvSynonyms = itemView.findViewById(R.id.tvSynonyms);
            tvExampleTitle = itemView.findViewById(R.id.tvExampleTitle);
            tvExample = itemView.findViewById(R.id.tvExample);
        }
    }
}