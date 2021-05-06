package com.example.mobidoc.adapters;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mobidoc.R;
import com.example.mobidoc.models.Appointment;
import com.example.mobidoc.ui.Appointment.DoctorConfirmAppointmentResults;

import java.util.List;


public class adapterPatient  extends RecyclerView.Adapter<adapterPatient.MyHolder>{
    Context context;
    List<Appointment> userList;
    TextView datetime2;
       public adapterPatient(Context context, List<Appointment> userList) {
        this.context = context;
        this.userList = userList;
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_users, parent, false);
        return new MyHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        String appointmentUID = userList.get(position).getId();
        String patientUID = userList.get(position).getPatientUid();
        String patientName = userList.get(position).getPatient_Name();
        // String userImage = userList.get(position).getImage();
        //String userPatientName = userList.get(position).getPatient_Name();
        //String userPatientMessage = userList.get(position).getPatient_Message();
        String userDate = userList.get(position).getDate_for_appointment();
        String userTime = userList.get(position).getTime_for_appointment();
        holder.mNameTv2.setText(userTime);
        holder.mSpecializationTv2.setText(userDate);

// moving to the  next activity
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DoctorConfirmAppointmentResults.class);
                intent.putExtra("appointmentUID", appointmentUID);
                intent.putExtra("patientUID", patientUID);
                intent.putExtra("patientName", patientName);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class  MyHolder extends RecyclerView.ViewHolder{

        ImageView mAvatarIv2;
        TextView mNameTv2, mSpecializationTv2, PatientName,Patientmessage;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            mAvatarIv2 = itemView.findViewById(R.id.avatarIv);
            mNameTv2 = itemView.findViewById(R.id.nameTv);
            mSpecializationTv2 = itemView.findViewById(R.id.Specialization);
            datetime2 = itemView.findViewById(R.id.SelectDoctor);
        }
    }
}