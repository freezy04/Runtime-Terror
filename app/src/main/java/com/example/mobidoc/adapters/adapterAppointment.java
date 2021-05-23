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
import com.example.mobidoc.models.Doctor;
import com.example.mobidoc.ui.Appointment.Booking;

import java.util.List;

public class adapterAppointment extends RecyclerView.Adapter<adapterAppointment.MyHolder> {

    Context context;
    List<Doctor> userList;

    public adapterAppointment(Context context, List<Doctor> userList) {
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

        String hisUID = userList.get(position).getUid();
        // String userImage = userList.get(position).getImage();
        String userName = userList.get(position).getFirst_name();
        final String userSpecialization = userList.get(position).getSpecialization();

        holder.mNameTv.setText(userName);
        holder.mSpecializationTv.setText(userSpecialization);
        //try{
        // Picasso.get().load(userImage)
        //   .placeholder(R.drawable.ic_defualt_img)
        // .into(holder.mAvatarIv);
        //}
        //catch (Exception e){

        //}

        holder.itemView.setOnClickListener(v -> {
           holderItemClicked(hisUID, userName);
        });
    }

    public void holderItemClicked(String hisUID, String userName){
        Intent intent = new Intent(context, Booking.class);
        intent.putExtra("hisUid", hisUID);
        intent.putExtra("hisName",userName);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class  MyHolder extends RecyclerView.ViewHolder{

        ImageView mAvatarIv;
        TextView mNameTv, mSpecializationTv;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            mAvatarIv = itemView.findViewById(R.id.avatarIv);
            mNameTv = itemView.findViewById(R.id.nameTv);
            mSpecializationTv = itemView.findViewById(R.id.Specialization);
        }
    }
}
