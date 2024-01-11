package com.example.massenger_application.Chat;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.massenger_application.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;

public class ChatRecyclerAdapter extends FirestoreRecyclerAdapter<ChatMessageModel,ChatRecyclerAdapter.ViewHolder> {

    Context context;
    public ChatRecyclerAdapter(@NonNull FirestoreRecyclerOptions<ChatMessageModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatRecyclerAdapter.ViewHolder holder, int position, @NonNull ChatMessageModel model) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String currentUid = auth.getUid();
        if (model.getSenderId().equals(currentUid)){
            holder.receiverLayout.setVisibility(View.GONE);
            holder.senderLayout.setVisibility(View.VISIBLE);
            holder.sentText.setText(model.getMessage());

        }
        else {
            holder.senderLayout.setVisibility(View.GONE);
            holder.receiverLayout.setVisibility(View.VISIBLE);
            holder.receivedText.setText(model.getMessage());
        }
    }

    @NonNull
    @Override
    public ChatRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_layout,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout receiverLayout,senderLayout;
        private TextView receivedText,sentText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            receiverLayout = itemView.findViewById(R.id.received_chat_layout);
            receivedText = itemView.findViewById(R.id.received_chat_text);
            senderLayout = itemView.findViewById(R.id.sendder_chat_layout);
            sentText = itemView.findViewById(R.id.sender_chat_text);
        }
    }
}
