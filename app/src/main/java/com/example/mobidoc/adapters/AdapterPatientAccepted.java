package com.example.mobidoc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobidoc.R;
import com.example.mobidoc.models.Appointment;

import java.util.List;

public class AdapterPatientAccepted extends RecyclerView.Adapter<AdapterPatientAccepted.MyHolder> {
    Context context;
    List<Appointment> userList;
    TextView datetime2;

    public AdapterPatientAccepted(Context context, List<Appointment> userList) {
        this.context = context;
        this.userList = userList;

    }

    @NonNull
    @Override
    public AdapterPatientAccepted.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_users_patients_upcoming, parent, false);
        return new AdapterPatientAccepted.MyHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        //String appointmentUID = userList.get(position).getId();
        // String patientUID = userList.get(position).getPatientUid();
        //  String patientName = userList.get(position).getPatient_Name();
        // String status = userList.get(position).getStatus();
        // String userImage = userList.get(position).getImage();
        String userDoctorName = userList.get(position).getDoctor_Name();
        String userPatientMessage = userList.get(position).getReason_for_appointment();
        String userDate = userList.get(position).getDate_for_appointment();
        String userTime = userList.get(position).getTime_for_appointment();

        if(userList.get(position).getStatus().equals("accepted")){

            holder.PatientTime.setText(userTime);
            holder.PatientDate.setText(userDate);
            holder.Patientmessage.setText(userPatientMessage);
            holder.DoctorName.setText(userDoctorName);

        }

// moving to the  next activity
        // holder.itemView.setOnClickListener(v -> {
        //appointmentClicked(appointmentUID, patientUID, patientName, status);
        //  });
    }




    @Override
    public int getItemCount() {
        return userList.size();
    }

    class  MyHolder extends RecyclerView.ViewHolder{

        ImageView mAvatarIv2;
        TextView PatientTime,PatientDate, DoctorName,Patientmessage;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            DoctorName = itemView.findViewById(R.id.patient_name);
            Patientmessage= itemView.findViewById(R.id.patient_appointment_message);
            PatientTime = itemView.findViewById(R.id.patient_appointment_time);
            PatientDate = itemView.findViewById(R.id.patient_appointment_date);
        }
    }
}


