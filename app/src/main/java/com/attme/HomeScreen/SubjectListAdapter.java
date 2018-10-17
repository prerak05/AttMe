package com.attme.HomeScreen;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.attme.HomeScreen.model.SubjectList;
import com.attme.R;

import java.util.List;

public class SubjectListAdapter extends RecyclerView.Adapter<SubjectListAdapter.MyViewHolder> {

    private List<SubjectList> subjectLists;
    private RecyclerItemClickListener recyclerItemClickListener;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_subjectName;

        public MyViewHolder(View view) {
            super(view);
            tv_subjectName = (TextView) view.findViewById(R.id.tv_subjectName);
        }
    }

    public SubjectListAdapter(List<SubjectList> filesGetSetList, RecyclerItemClickListener recyclerItemClickListener) {
        this.subjectLists = filesGetSetList;
        this.recyclerItemClickListener = recyclerItemClickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subject_list, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (subjectLists != null && subjectLists.size() > 0) {
            holder.tv_subjectName.setText(subjectLists.get(position).getName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerItemClickListener.onItemClick(subjectLists.get(position));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return subjectLists.size();
    }

    public interface RecyclerItemClickListener {
        void onItemClick(SubjectList subjectList);
    }
}