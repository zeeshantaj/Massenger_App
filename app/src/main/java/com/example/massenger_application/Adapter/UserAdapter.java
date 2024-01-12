package com.example.massenger_application.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.massenger_application.Chat.ChatActivity;
import com.example.massenger_application.Chat.ChatMessageModel;
import com.example.massenger_application.Chat.ChatRecyclerAdapter;
import com.example.massenger_application.Model.Users;
import com.example.massenger_application.R;
import com.example.massenger_application.Utils.FirebaseUtils;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class UserAdapter extends FirestoreRecyclerAdapter<Users, UserAdapter.ViewHolder> {


    private Context context;
    public UserAdapter(@NonNull FirestoreRecyclerOptions<Users> options,Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position, @NonNull Users users) {

       // Users users = usersList.get(position);
        holder.name.setText(users.getName());
        holder.status.setText(users.getStatus());
        Glide.with(holder.itemView.getContext())
                .load(users.getImage())
                .into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
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

        if (users.getAssociatedId() != null && users.getAssociatedId().equals(FirebaseUtils.currentUserId())){
            getSnapshots().getSnapshot(position).getReference().delete();
        }


    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_layout,parent,false);
        return new ViewHolder(view);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView imageView;
        private TextView name,status;
        private CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.user_img);
            name = itemView.findViewById(R.id.user_name);
            status = itemView.findViewById(R.id.user_status);
            cardView = itemView.findViewById(R.id.userItemCard);
        }
    }
}
