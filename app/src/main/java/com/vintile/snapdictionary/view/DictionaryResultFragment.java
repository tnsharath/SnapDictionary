package com.vintile.snapdictionary.view;


import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vintile.snapdictionary.MyApplication;
import com.vintile.snapdictionary.R;

import com.vintile.snapdictionary.utils.AppConstants;
import com.vintile.snapdictionary.view.adapter.DictionaryAdapter;
import com.vintile.snapdictionary.viewmodel.DictionaryResultViewModel;
import com.vintile.snapdictionary.viewmodel.DictionaryVMFactory;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class DictionaryResultFragment extends Fragment {

    public DictionaryResultFragment() {
        // Required empty public constructor
    }

    private RecyclerView recyclerView;

    private DictionaryResultViewModel dictionaryResultViewModel;
    private DictionaryAdapter dictionaryAdapter;
    private ItemOffsetDecoration itemDecoration;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dictionary_result, container, false);

        String word =  Objects.requireNonNull(getArguments()).getString(AppConstants.WORD);
        recyclerView = view.findViewById(R.id.rv_meaning);
        TextView tvWord = view.findViewById(R.id.tvWord);
        tvWord.setText(word);
        try {
            Objects.requireNonNull(Objects.requireNonNull((CameraResultActivity)getActivity()).getSupportActionBar()).setTitle(word);
        }catch (Exception e){
            e.printStackTrace();
        }
        dictionaryResultViewModel = new ViewModelProvider(this, new DictionaryVMFactory()).get(DictionaryResultViewModel.class);
        dictionaryAdapter = new DictionaryAdapter();
        itemDecoration = new ItemOffsetDecoration(MyApplication.getContext());
        linearLayoutManager = new LinearLayoutManager(MyApplication.getContext(), RecyclerView.VERTICAL, false);
        initRecyclerView();
        getResult(word);
        return view;
    }

    private void getResult(String word) {
        dictionaryResultViewModel.requestDict(word);
        dictionaryResultViewModel.getDictResult().observe(getViewLifecycleOwner(), results -> {
            if (results != null && !results.isEmpty()){
                dictionaryAdapter.addAll(results);
            }
        });
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(dictionaryAdapter);

    }



    /**
     * Recyclerview item decoration
     */
    class ItemOffsetDecoration extends RecyclerView.ItemDecoration {

        private final int mItemOffset;

        private ItemOffsetDecoration(int itemOffset) {
            mItemOffset = itemOffset;
        }

        private ItemOffsetDecoration(@NonNull Context context) {
            this(context.getResources().getDimensionPixelSize(R.dimen.item_margin));
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                   @NonNull RecyclerView parent,
                                   @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset);
        }
    }

}
