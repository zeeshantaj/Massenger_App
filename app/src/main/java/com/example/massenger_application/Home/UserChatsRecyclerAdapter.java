package com.example.massenger_application.Home;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.massenger_application.Chat.Chat;
import com.example.massenger_application.Chat.ChatActivity;
import com.example.massenger_application.Chat.ChatRoomModel;
import com.example.massenger_application.Model.Users;
import com.example.massenger_application.R;
import com.example.massenger_application.Utils.FirebaseUtils;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserChatsRecyclerAdapter extends FirestoreRecyclerAdapter<ChatRoomModel, UserChatsRecyclerAdapter.ViewHolder> {

    public UserChatsRecyclerAdapter(@NonNull FirestoreRecyclerOptions<ChatRoomModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull UserChatsRecyclerAdapter.ViewHolder holder, int position, @NonNull ChatRoomModel model) {
//        holder.name.setText(model.getName());
//        holder.lastMessage.setText(model.getLastMessage());
//        holder.time.setText(model.getLastMessage());
//
//        Glide.with(holder.itemView.getContext())
//                .load(model.getImage())
//                .into(holder.imageView);
        FirebaseUtils.getOtherUserFromChatRoom(model.getSenderIds())
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        boolean lastMsgSender = model.getLastMessageSenderId().equals(FirebaseUtils.currentUserId());

                        Users users = task.getResult().toObject(Users.class);
                        holder.name.setText(users.getName());
                        if (lastMsgSender){
                            holder.lastMessage.setText("You: "+model.getLastMessage());
                        }
                        else {
                            holder.lastMessage.setText(model.getLastMessage());
                        }
                        holder.time.setText(FirebaseUtils.getTimestampToString(model.getLastMessageTimeStamp()));

                        Glide.with(holder.itemView.getContext())
                                .load(users.getImage())
                                .into(holder.imageView);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(v.getContext(), ChatActivity.class);
                                intent.putExtra("name",users.getName());
                                intent.putExtra("image",users.getImage());
                                intent.putExtra("associatedId",users.getAssociatedId());
                                intent.putExtra("lastSeen",users.getLast_seen_status());
                                v.getContext().startActivity(intent);
                            }
                        });


                    }else {
                        Toast.makeText(holder.itemView.getContext(), "Error "+task.getException(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @NonNull
    @Override
    public UserChatsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_layout, parent, false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView imageView;
        private TextView name, lastMessage, time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.main_img);
            name = itemView.findViewById(R.id.main_message_name);
            lastMessage = itemView.findViewById(R.id.main_lastMsg);
            time = itemView.findViewById(R.id.main_lastMstTime);
        }
    }
}
