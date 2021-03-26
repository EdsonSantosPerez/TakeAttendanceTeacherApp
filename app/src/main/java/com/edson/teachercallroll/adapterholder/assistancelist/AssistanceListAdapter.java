package com.edson.teachercallroll.adapterholder.assistancelist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.edson.teachercallroll.R;
import com.edson.teachercallroll.model.AssistanceSheetDto;

import java.util.ArrayList;

public class AssistanceListAdapter extends RecyclerView.Adapter<AssistanceListHolder> implements View.OnClickListener {

    Context context;
    ArrayList<AssistanceSheetDto> assistanceSheetDtos;

    private View.OnClickListener listener;

    public AssistanceListAdapter(Context context, ArrayList<AssistanceSheetDto> assistanceSheetDtos) {
        this.context = context;
        this.assistanceSheetDtos = assistanceSheetDtos;
    }

    @NonNull
    @Override
    public AssistanceListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_row_assistances, parent, false);

        view.setOnClickListener(this);
        return new AssistanceListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssistanceListHolder holder, int position) {
        AssistanceSheetDto assistanceSheetDto = assistanceSheetDtos.get(position);
        holder.setDetails(assistanceSheetDto);
    }

    @Override
    public int getItemCount() {
        return assistanceSheetDtos.size();
    }

    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener != null){
            listener.onClick(v);
        }
    }
}
