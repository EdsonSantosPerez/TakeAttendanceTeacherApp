package com.edson.teachercallroll.adapterholder;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.edson.teachercallroll.R;
import com.edson.teachercallroll.model.SemestreDto;

import java.util.ArrayList;

public class SemestreAdapter extends BaseAdapter implements Filterable {

    Activity context;
    ArrayList<SemestreDto> semestreDtos;
    private static LayoutInflater inflater = null;

    SemestreDtoFilter filter;
    ArrayList<SemestreDto> filterList;

    TextView txtVId;
    TextView txtVTitre;

    public SemestreAdapter(Activity context, ArrayList<SemestreDto> semestreDtos) {
        this.context = context;
        this.semestreDtos = semestreDtos;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.filterList = semestreDtos;
    }

    @Override
    public int getCount() {
        return semestreDtos.size();
    }

    @Override
    public SemestreDto getItem(int position) {
        return semestreDtos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return semestreDtos.indexOf(semestreDtos.get(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        itemView = (itemView == null) ? inflater.inflate(R.layout.item_row_select_group, null) : itemView;
        txtVId = itemView.findViewById(R.id.txtVId);
        txtVTitre = itemView.findViewById(R.id.txtVTitre);
        SemestreDto semestreDtoSelected = semestreDtos.get(position);
        txtVId.setText(semestreDtoSelected.getId() + "");
        txtVTitre.setText(semestreDtoSelected.getSemestre());
        return itemView;
    }

    @Override
    public Filter getFilter() {
        if (filter == null){
            filter = new SemestreDtoFilter();
        }
        return filter;
    }

    class SemestreDtoFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                constraint = constraint.toString().toUpperCase();
                ArrayList<SemestreDto> filters = new ArrayList<>();
                for (SemestreDto semestreDto : filterList) {
                    if (semestreDto.getSemestre().toUpperCase().contains(constraint)) {
                        SemestreDto newSemestreDto = new SemestreDto();
                        newSemestreDto = semestreDto;
                        filters.add(newSemestreDto);
                    }
                }
                results.count = filters.size();
                results.values = filters;
            } else {
                results.count = filterList.size();
                results.values = filterList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            semestreDtos = (ArrayList<SemestreDto>) results.values;
            notifyDataSetChanged();
        }
    }
}
