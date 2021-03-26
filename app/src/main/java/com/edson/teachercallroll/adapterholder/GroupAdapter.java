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
import com.edson.teachercallroll.model.GroupDto;

import java.util.ArrayList;

public class GroupAdapter extends BaseAdapter implements Filterable {

    Activity context;
    ArrayList<GroupDto> groupDtos;
    private static LayoutInflater inflater = null;

    GroupDtoFilter filter;
    ArrayList<GroupDto> filterList;

    TextView txtVId;
    TextView txtVTitre;

    public GroupAdapter(Activity context, ArrayList<GroupDto> groupDtos) {
        this.context = context;
        this.groupDtos = groupDtos;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.filterList = groupDtos;
    }

    @Override
    public int getCount() {
        return groupDtos.size();
    }

    @Override
    public GroupDto getItem(int position) {
        return groupDtos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return groupDtos.indexOf(groupDtos.get(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;
        itemView = (itemView == null) ? inflater.inflate(R.layout.item_row_select_group, null) : itemView;
        txtVId = itemView.findViewById(R.id.txtVId);
        txtVTitre = itemView.findViewById(R.id.txtVTitre);
        GroupDto groupDtoSelected = groupDtos.get(position);
        txtVId.setText(groupDtoSelected.getId()+"");
        txtVTitre.setText(groupDtoSelected.getName());
        return itemView;
    }

    @Override
    public Filter getFilter() {
        if (filter == null){
            filter = new GroupDtoFilter();
        }
        return filter;
    }

    class GroupDtoFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if (constraint != null && constraint.length() > 0) {
                constraint = constraint.toString().toUpperCase();
                ArrayList<GroupDto> filters = new ArrayList<>();
                for (GroupDto groupDto : filterList){
                    if (groupDto.getName().toUpperCase().contains(constraint)){
                        GroupDto newGroupDto = new GroupDto();
                        newGroupDto = groupDto;
                        filters.add(newGroupDto);
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
            groupDtos = (ArrayList<GroupDto>) results.values;
            notifyDataSetChanged();
        }
    }
}
