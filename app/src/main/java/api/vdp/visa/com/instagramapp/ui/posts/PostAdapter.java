package api.vdp.visa.com.instagramapp.ui.posts;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

import api.vdp.visa.com.instagramapp.R;
import api.vdp.visa.com.instagramapp.repository.dao.PostDAO;
import api.vdp.visa.com.instagramapp.viewmodel.InstagramViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by svuddara on 9/16/17.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PhotoViewHolder> {

    private List<PostDAO> postDAOList;
    private Context context;
    private InstagramViewModel instagramViewModel;

    public PostAdapter(Context context, List<PostDAO> postDAOList, InstagramViewModel instagramViewModel){
        this.postDAOList = postDAOList;
        this.context = context;
        this.instagramViewModel = instagramViewModel;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_adapter_row, parent, false);
        return new PhotoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PhotoViewHolder holder, int position) {
        final PostDAO postDAO = postDAOList.get(position);
        holder.imgPicture.setImageResource(0);
        Picasso.with(context).load(postDAO.getImageUrl()).into(holder.imgPicture);
        holder.txtCaption.setText(postDAO.getCaption());
        holder.imgProfilePicture.setImageResource(0);
        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.GRAY)
                .borderWidthDp(1)
                .cornerRadiusDp(20)
                .oval(true)
                .build();

        Picasso.with(context)
                .load(postDAO.getProfilePicture())
                .transform(transformation)
                .into(holder
                        .imgProfilePicture);

        holder.txtUserName.setText(postDAO.getUserName());
        holder.txtCreatedTime.setText(getTimeCreated(postDAO.getCreatedTime()));
        holder.txtLikes.setText(getLikeText(postDAO.getLikeCount()));
        holder.imgLikes.setImageResource(getLikeImage(postDAO.isHasUserLiked()));
        holder.imgLikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(context instanceof PostsListActivity){
                    ((PostsListActivity)context).updateUserLikeStatus(postDAO,holder);
                }
            }
        });
    }


    public int getLikeImage(boolean hasUserLiked){
        int resourceId;
        if(hasUserLiked){
            resourceId = R.drawable.heartfilled;
        }else{
            resourceId = R.drawable.heart;
        }
        return resourceId;
    }

    private String getTimeCreated(String time){
        Long createdTime = Long.valueOf(time) * 1000;
        return new String(DateUtils.getRelativeTimeSpanString(context,createdTime).toString());
    }

    public String getLikeText(int likeCount){
        String likeText;

        if(likeCount == 0){
            likeText = "";
        }else if(likeCount == 1){
            likeText = "1 Like";
        }else{
            likeText = likeCount+" Likes";
        }
        return likeText;
    }



    @Override
    public int getItemCount() {
        return postDAOList.size();
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.imgProfilePicture)
        public ImageView imgProfilePicture;

        @BindView(R.id.txtUserName)
        public TextView txtUserName;

        @BindView(R.id.imgPicture)
        public  ImageView imgPicture;

        @BindView(R.id.txtCaption)
        public TextView txtCaption;

        @BindView(R.id.txtLikes)
        public  TextView txtLikes;

        @BindView(R.id.txtTime)
        public TextView txtCreatedTime;

        @BindView(R.id.likesImage)
        public ImageView imgLikes;


        public PhotoViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
