package com.example.delaney.myvacationtracker;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class EmployeeVacationAdapter extends RecyclerView.Adapter<EmployeeVacationAdapter.EmployeeVacationViewHolder>{

    private List<DocumentSnapshot> mEmployeeVacationSnapshots = new ArrayList<>();

    public EmployeeVacationAdapter (){

        CollectionReference employeevacationRef = FirebaseFirestore.getInstance().collection(Constants.COLLECTION_PATH);

        employeevacationRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                if (e != null) {
                    Log.w(Constants.TAG, "Listening failed!");
                    return;
                }
                mEmployeeVacationSnapshots = documentSnapshots.getDocuments();
                notifyDataSetChanged();
            }
        });
    }

    @NonNull
    @Override
    public EmployeeVacationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.employeevacation_itemview,parent,false);
        return new EmployeeVacationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeVacationViewHolder employeeVacationViewHolder, int i) {

        DocumentSnapshot ds = mEmployeeVacationSnapshots.get(i);
        String employee = (String)ds.get(Constants.KEY_EMPLOYEE);
        String start = (String)ds.get(Constants.KEY_STARTDATE);
        String end = (String)ds.get(Constants.KEY_ENDDATE);
        String reason = (String)ds.get(Constants.KEY_REASON);


        employeeVacationViewHolder.mNameTextView.setText(employee);
        employeeVacationViewHolder.mStartDateTextView.setText(start);
        employeeVacationViewHolder.mEndDateTextView.setText(end);
        employeeVacationViewHolder.mReasonTextView.setText(reason);

    }

    @Override
    public int getItemCount() {
        return mEmployeeVacationSnapshots.size();
    }

    static class EmployeeVacationViewHolder extends RecyclerView.ViewHolder{

        private TextView mNameTextView;
        private TextView mStartDateTextView;
        private TextView mEndDateTextView;
        private TextView mReasonTextView;



        public EmployeeVacationViewHolder(@NonNull View itemView) {
            super(itemView);
            mNameTextView = itemView.findViewById(R.id.itemview_employeename);
            mStartDateTextView = itemView.findViewById(R.id.itemview_start_date);
            mEndDateTextView = itemView.findViewById(R.id.itemview_end_date);
            mReasonTextView = itemView.findViewById(R.id.itemview_reason);


        }
    }
}
