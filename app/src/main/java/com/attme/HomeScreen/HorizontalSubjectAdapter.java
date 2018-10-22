package com.attme.HomeScreen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.attme.HomeScreen.model.SubjectAttendance;
import com.attme.R;

import java.util.List;

/**
 * Created by prerak on 17/10/18.
 */

public class HorizontalSubjectAdapter extends RecyclerView.Adapter<HorizontalSubjectAdapter.ViewHolder> {
    private Context context;
    private List<SubjectAttendance> list;

    public HorizontalSubjectAdapter(Home home, List<SubjectAttendance> attendanceList) {
        this.context = home;
        this.list = attendanceList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.row_subject, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (list.size() > 0) {
            holder.tvSubjectName.setText(list.get(position).getName());
            holder.tvDate.setText(list.get(position).getDate());
            holder.tvTime.setText(list.get(position).getTime());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvSubjectName, tvDate, tvTime;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tvSubjectName = itemView.findViewById(R.id.tvSubjectName);
            this.tvDate = itemView.findViewById(R.id.tvDate);
            this.tvTime = itemView.findViewById(R.id.tvTime);
        }
    }
}
