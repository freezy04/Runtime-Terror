package com.example.mobidoc.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobidoc.R;
import com.example.mobidoc.models.Appointment;

import java.util.List;

public class AdapterAppointmentList extends RecyclerView.Adapter<AdapterAppointmentList.MyHolder> {
    Context context;
    List<Appointment> userList;
    TextView datetime2;
    private String userType;

    public AdapterAppointmentList(Context context, List<Appointment> userList, String userType) {
        this.context = context;
        this.userList = userList;
        this.userType = userType;
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.appointment_row, parent, false);
        return new AdapterAppointmentList.MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterAppointmentList.MyHolder holder, int position) {

        String appointmentUID = userList.get(position).getId();
        String doctorUID = userList.get(position).getDoctorUid();
        String doctorName = userList.get(position).getDoctor_Name();
        String patientName = userList.get(position).getPatient_Name();
        String appCost = userList.get(position).getAppointment_Cost();
        // String userImage = userList.get(position).getImage();
        String userPatientMessage = userList.get(position).getReason_for_appointment();
        String userDate = userList.get(position).getDate_for_appointment();
        String userTime = userList.get(position).getTime_for_appointment();
        String notes = userList.get(position).getNotes();
        //   holder.DoctorName.setText(userDoctorName);

        if (userType.equals("Doctor")) {
            holder.DoctorName.setText(patientName);
        } else {
            holder.DoctorName.setText(doctorName);
        }

        holder.Patientmessage.setText(userPatientMessage);
        holder.mTimeAppointment.setText(userTime);
        holder.mDateAppointment.setText(userDate);
        holder.appointmentCost.setText(appCost);
        holder.appointmentNotes.setText(notes);

        if (userList.get(position).getStatus().equals("rejected")){
            holder.mTimeAppointment.setText("REJECTED");
            holder.mDateAppointment.setText("REJECTED");

        }

// moving to the  next activity
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, DoctorConfirmAppointmentResults.class);
//                intent.putExtra("appointmentUID", appointmentUID);
//                intent.putExtra("doctorUID", doctorUID);
//                intent.putExtra("doctorName", doctorName);
//                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        ImageView mAvatarIv2;
        TextView mTimeAppointment, mDateAppointment, DoctorName, Patientmessage, appointmentCost, appointmentNotes;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            mAvatarIv2 = itemView.findViewById(R.id.avatarIv);
            DoctorName = itemView.findViewById(R.id.name_Tv);
            mTimeAppointment = itemView.findViewById(R.id.timeTv);
            appointmentCost = itemView.findViewById(R.id.PatientMedicalCost);
            mDateAppointment = itemView.findViewById(R.id.dateTv);
            Patientmessage = itemView.findViewById(R.id.messageTv);
            datetime2 = itemView.findViewById(R.id.SelectDoctor);
            appointmentNotes = itemView.findViewById(R.id.appointmentNotesTV);
            Button prog = itemView.findViewById(R.id.btn_edit_patient);

        }
    }

}
