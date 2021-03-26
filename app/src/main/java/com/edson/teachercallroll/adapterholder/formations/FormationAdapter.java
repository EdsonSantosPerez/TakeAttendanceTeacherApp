package com.edson.teachercallroll.adapterholder.formations;

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
import com.edson.teachercallroll.model.FormationDto;

import java.util.ArrayList;

public class FormationAdapter extends BaseAdapter implements Filterable {

    Activity context;
    ArrayList<FormationDto> formationDtos;
    private static LayoutInflater inflater = null;

    FormatioDtoFilter filter;
    ArrayList<FormationDto> filterList;

    TextView txtVId;
    TextView txtVTitre;

    public FormationAdapter(Activity context, ArrayList<FormationDto> formationDtos) {
        this.context = context;
        this.formationDtos = formationDtos;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.filterList = formationDtos;
    }

    @Override
    public int getCount() {
        return formationDtos.size();
    }

    @Override
    public FormationDto getItem(int position) {
        return formationDtos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return formationDtos.indexOf(formationDtos.get(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        itemView = (itemView == null) ? inflater.inflate(R.layout.item_row_select_group, null) : itemView;
        txtVId = itemView.findViewById(R.id.txtVId);
        txtVTitre = itemView.findViewById(R.id.txtVTitre);
        FormationDto formationDtoSelected = formationDtos.get(position);
        txtVId.setText(formationDtoSelected.getId());
        txtVTitre.setText(formationDtoSelected.getTitre_officiel());
        return itemView;
    }

    @Override
    public Filter getFilter() {
        if (filter == null){
            filter = new FormatioDtoFilter();
        }
        return filter;
    }

    class FormatioDtoFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                constraint = constraint.toString().toUpperCase();
                ArrayList<FormationDto> filters = new ArrayList<>();
                for(FormationDto formationDto : filterList){
                    if (formationDto.getTitre_officiel().toUpperCase().contains(constraint)){
                        FormationDto newFormationDto = new FormationDto();
                        newFormationDto = formationDto;
                        filters.add(newFormationDto);
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
            formationDtos = (ArrayList<FormationDto>) results.values;
            notifyDataSetChanged();
        }
    }
}
