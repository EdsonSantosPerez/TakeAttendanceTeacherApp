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
import com.edson.teachercallroll.model.ModuleDto;

import java.util.ArrayList;

public class ModuleAdapter extends BaseAdapter implements Filterable {

    Activity context;
    ArrayList<ModuleDto> moduleDtos;
    private static LayoutInflater inflater = null;

    ModuleDtoFilter filter;
    ArrayList<ModuleDto> filterList;

    TextView txtVId;
    TextView txtVTitre;

    public ModuleAdapter(Activity context, ArrayList<ModuleDto> moduleDtos) {
        this.context = context;
        this.moduleDtos = moduleDtos;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.filterList = moduleDtos;

    }

    @Override
    public int getCount() {
        return moduleDtos.size();
    }

    @Override
    public ModuleDto getItem(int position) {
        return moduleDtos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return moduleDtos.indexOf(moduleDtos.get(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        itemView = (itemView == null) ? inflater.inflate(R.layout.item_row_select_group, null) : itemView;
        txtVId = itemView.findViewById(R.id.txtVId);
        txtVTitre = itemView.findViewById(R.id.txtVTitre);
        ModuleDto moduleDtoSelected = moduleDtos.get(position);
        txtVId.setText(moduleDtoSelected.getId()+"");
        txtVTitre.setText(moduleDtoSelected.getTitre());
        return itemView;
    }

    @Override
    public Filter getFilter() {
        if (filter == null){
            filter = new ModuleDtoFilter();
        }
        return filter;
    }

    class ModuleDtoFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                constraint = constraint.toString().toUpperCase();
                ArrayList<ModuleDto> filters = new ArrayList<>();
                for (ModuleDto moduleDto : filterList){
                    if (moduleDto.getTitre().toUpperCase().contains(constraint)){
                        ModuleDto newModuleDto = new ModuleDto();
                        newModuleDto = moduleDto;
                        filters.add(newModuleDto);
                    }
                }
                results.count = filters.size();
                results.values = filters;
            }else {
                results.count = filterList.size();
                results.values = filterList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            moduleDtos = (ArrayList<ModuleDto>) results.values;
            notifyDataSetChanged();
        }
    }
}
