package com.example.productprojectprm;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.productprojectprm.models.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{

    private final List<User> mListUser;
    private Context context;

    public UserAdapter(List<User> mListUser) {
        this.mListUser = mListUser;
    }

    public UserAdapter(List<User> mListUser, Context context) {
        this.mListUser = mListUser;
        this.context = context;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = mListUser.get(position);
        if(user == null){
            return;
        }
        holder.userId.setText(String.valueOf("User ID:") + user.getUserId());
        holder.userAccount.setText("Account: " + user.getUserAccount());
        holder.userName.setText("Name: " + user.getUserName());
        holder.userRole.setText("Role: " + user.getUserRole());
        holder.userPhone.setText("Phone: " + user.getPhone());
        holder.userAddress.setText("Address : " + user.getAddress());
    }

    @Override
    public int getItemCount() {
        if(mListUser != null){
            return  mListUser.size();
        }
        return 0;
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder{
        private final TextView userId,userAccount, userName, userRole, userPhone, userAddress;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userId = itemView.findViewById(R.id.user_id);
            userAccount = itemView.findViewById(R.id.user_account);
            userName = itemView.findViewById(R.id.user_name);
            userRole = itemView.findViewById(R.id.user_role);
            userPhone = itemView.findViewById(R.id.user_phone);
            userAddress = itemView.findViewById(R.id.user_address);
        }
    }


}
