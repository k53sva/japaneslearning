package com.melody.education.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.melody.education.R;
import com.melody.education.fragment.LessonFragment;
import com.melody.education.model.Album;
import com.melody.education.ui.LearningActivity;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.MyViewHolder> {

    private Context mContext;
    private List<Album> albumList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView overflow;
        public ImageView thumbnail;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }


    public LessonAdapter(Context mContext, List<Album> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Album album = albumList.get(position);
        holder.title.setText(album.getName());
        holder.count.setText(album.getDescription());
        holder.overflow.setOnClickListener(v -> showPopupMenu(holder.overflow, position));
        Picasso.with(mContext)
                .load("http://www.japaneselearning.somee.com/image/beginner_lesson_1.jpg")
                .placeholder(R.drawable.album1)
                .into(holder.thumbnail);

    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view, int position) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(position));
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        int position;

        public MyMenuItemClickListener(int postion) {
            this.position = postion;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play_next:
                    startLearningActivity(position);
                    return true;
                default:
            }
            return false;
        }

    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    private void startLearningActivity(int selectedIndex) {
        Intent intent = new Intent(mContext, LearningActivity.class);
        intent.putExtra(LessonFragment.EXTRA_INDEX, selectedIndex);
        mContext.startActivity(intent);
    }
}
