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


public class AdapterPatient extends RecyclerView.Adapter<AdapterPatient.MyHolder>{
    Context context;
    List<Appointment> userList;
    TextView datetime2;

       public AdapterPatient(Context context, List<Appointment> userList) {
        this.context = context;
        this.userList = userList;
    }


    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_users_patients, parent, false);
        return new MyHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        String appointmentUID = userList.get(position).getId();
        String patientUID = userList.get(position).getPatientUid();
        String patientName = userList.get(position).getPatient_Name();
        String status = userList.get(position).getStatus();
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
        holder.itemView.setOnClickListener(v -> {
            appointmentClicked(appointmentUID, patientUID, patientName, status);
         });
    }

    private void goToConfirmResultsScreen(String appointmentUID, String patientUID, String patientName) {
        Intent intent = new Intent(context, DoctorConfirmAppointmentResults.class);
        intent.putExtra("appointmentUID", appointmentUID);
        intent.putExtra("patientUID", patientUID);
        intent.putExtra("patientName", patientName);
        context.startActivity(intent);
    }

    private void acceptAppointment(String appointmentUID) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("Appointments");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Appointment app = ds.getValue(Appointment.class);
                    app.setId(ds.getKey());
                    if (app.getId().equals(appointmentUID)) {
                        app.setStatus("accepted");
                        Map<String, Object> appointmentUpdate = new HashMap<>();
                        appointmentUpdate.put(appointmentUID, app);
                        ref.updateChildren(appointmentUpdate);
                        break;
                    }
                }
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();//refresh activity
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Accept failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void rejectAppointment(String appointmentUID) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("Appointments");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (ds.getKey().equals(appointmentUID)) {
                        ds.getRef().child("status").setValue("rejected");
                        break;

                    }
                }
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();//refresh activity
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "Rejection failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void appointmentClicked(String appointmentUID, String patientUID, String patientName, String status) {
        if (status.equals("accepted")) {
            goToConfirmResultsScreen(appointmentUID, patientUID, patientName);
        } else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setTitle("Would you like to confirm this appointment?");
            //dialog.setMessage(patientName + " will not know that you rejected the request.");
            dialog.setPositiveButton("Accept", (dialog1, which) -> {
                acceptAppointment(appointmentUID);
            });
            dialog.setNegativeButton("Reject", (dialog12, which) -> {
                rejectAppointment(appointmentUID);
            });
            dialog.create().show();
        }
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