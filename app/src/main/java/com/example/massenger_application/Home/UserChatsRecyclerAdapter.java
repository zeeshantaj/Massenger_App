package com.example.massenger_application.Home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.massenger_application.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserChatsRecyclerAdapter extends FirestoreRecyclerAdapter<Users_Chat_Model,UserChatsRecyclerAdapter.ViewHolder> {

    public UserChatsRecyclerAdapter(@NonNull FirestoreRecyclerOptions<Users_Chat_Model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull UserChatsRecyclerAdapter.ViewHolder holder, int position, @NonNull Users_Chat_Model model) {
        holder.name.setText(model.getName());
        holder.lastMessage.setText(model.getLastMessage());
        holder.time.setText(model.getLastMessage());

        Glide.with(holder.itemView.getContext())
                .load(model.getImage())
                .into(holder.imageView);
    }

    @NonNull
    @Override
    public UserChatsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_layout,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView imageView;
        private TextView name,lastMessage,time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.main_img);
            name = itemView.findViewById(R.id.main_message_name);
            lastMessage = itemView.findViewById(R.id.main_lastMsg);
            time = itemView.findViewById(R.id.main_lastMstTime);
        }
    }
}
