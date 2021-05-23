package com.example.mobidoc.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobidoc.R;
import com.example.mobidoc.models.Appointment;
import com.example.mobidoc.ui.Appointment.DoctorConfirmAppointmentResults;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class adapterPatientUpcoming extends RecyclerView.Adapter<adapterPatientUpcoming.MyHolder>{
    Context context;
    List<Appointment> userList;
    TextView datetime2;

       public adapterPatientUpcoming(Context context, List<Appointment> userList) {
        this.context = context;
        this.userList = userList;
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_users_patients_upcoming, parent, false);
        return new MyHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        //String appointmentUID = userList.get(position).getId();
       // String patientUID = userList.get(position).getPatientUid();
      //  String patientName = userList.get(position).getPatient_Name();
       // String status = userList.get(position).getStatus();
        // String userImage = userList.get(position).getImage();
        String userPatientName = userList.get(position).getPatient_Name();
        String userPatientMessage = userList.get(position).getReason_for_appointment();
        String userDate = userList.get(position).getDate_for_appointment();
        String userTime = userList.get(position).getTime_for_appointment();
        holder.PatientTime.setText(userTime);
        holder.PatientDate.setText(userDate);
        holder.Patientmessage.setText(userPatientMessage );
        holder.PatientName.setText(userPatientName);

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
        TextView PatientTime,PatientDate, PatientName,Patientmessage;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            PatientName = itemView.findViewById(R.id.patient_name);
            Patientmessage= itemView.findViewById(R.id.patient_appointment_message);
            PatientTime = itemView.findViewById(R.id.patient_appointment_time);
            PatientDate = itemView.findViewById(R.id.patient_appointment_date);
        }
    }
}