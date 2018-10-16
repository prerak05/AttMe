package com.attme.HomeScreen;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.attme.R;

import java.util.List;

public class SubjectListAdapter extends RecyclerView.Adapter<SubjectListAdapter.MyViewHolder> {

    private List<Subject_Get_Set> subjectGetSetList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_subjectName;

        public MyViewHolder(View view) {
            super(view);
            tv_subjectName = (TextView) view.findViewById(R.id.tv_subjectName);
        }
    }

    public SubjectListAdapter(List<Subject_Get_Set> filesGetSetList) {
        this.subjectGetSetList = filesGetSetList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subject_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Subject_Get_Set filesGetSet = subjectGetSetList.get(position);
        holder.tv_subjectName.setText(filesGetSet.getTv_subjectName());
    }

    @Override
    public int getItemCount() {
        return subjectGetSetList.size();
    }
}