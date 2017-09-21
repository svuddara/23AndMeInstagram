package api.vdp.visa.com.instagramapp.ui.posts;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import api.vdp.visa.com.instagramapp.R;

import api.vdp.visa.com.instagramapp.repository.dao.InstagramLikeDAO;
import api.vdp.visa.com.instagramapp.ui.login.LoginActivity;
import api.vdp.visa.com.instagramapp.utils.Constants;
import api.vdp.visa.com.instagramapp.utils.CookieUtils;
import api.vdp.visa.com.instagramapp.utils.SharedPreferencesUtils;
import api.vdp.visa.com.instagramapp.viewmodel.InstagramViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import api.vdp.visa.com.instagramapp.utils.OAuthConstants;
import api.vdp.visa.com.instagramapp.repository.dao.InstagramUserPostsDAO;
import api.vdp.visa.com.instagramapp.repository.dao.PostDAO;

public class PostsListActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    Context context;

    PostAdapter postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos_list);
        ButterKnife.bind(this);
        context = this;
        populatePostsView();
    }

    private void populatePostsView(){
        String accessToken =SharedPreferencesUtils.getAccessTokenFromSharedPreferences(this,OAuthConstants.ACCESS_TOKEN);
        final InstagramViewModel instagramViewModel = ViewModelProviders.of(this).get(InstagramViewModel.class);
        LiveData<InstagramUserPostsDAO> imagesDAOLiveData = instagramViewModel.getUserImages(accessToken);
        imagesDAOLiveData.observe(this, new Observer<InstagramUserPostsDAO>() {
            @Override
            public void onChanged(@Nullable InstagramUserPostsDAO instagramUserPostsDAO) {
                if(instagramUserPostsDAO.getError() != null){
                    Toast.makeText(getApplicationContext(), Constants.ERROR_GETTING_POSTS, Toast.LENGTH_SHORT).show();
                }else{
                    List<PostDAO> postDAOList = instagramUserPostsDAO.getPostDAOs();
                    postAdapter = new PostAdapter(context, postDAOList,instagramViewModel);
                    recyclerView.setAdapter(postAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scrolling,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void updateUserLikeStatus(final PostDAO postDAO, final PostAdapter.PhotoViewHolder holder){
        String accessToken = SharedPreferencesUtils.getAccessTokenFromSharedPreferences(context, OAuthConstants.ACCESS_TOKEN);
        final InstagramViewModel instagramViewModel = ViewModelProviders.of(this).get(InstagramViewModel.class);
        LiveData<InstagramLikeDAO> likeDAOLiveData = instagramViewModel.changeLikeStatus(accessToken, postDAO.getId(),!postDAO.isHasUserLiked());
        likeDAOLiveData.observe(this, new Observer<InstagramLikeDAO>() {
            @Override
            public void onChanged(@Nullable InstagramLikeDAO instagramLikeDAO) {
                if(instagramLikeDAO.getStatus() == Constants.HTTP_OK){
                    updateImageAndLikeCount(postDAO,holder);
                }else{
                    Toast.makeText(getApplicationContext(), Constants.ERROR_POSTING_LIKE, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private  void updateImageAndLikeCount(PostDAO postDAO, PostAdapter.PhotoViewHolder holder){
        int likeCount = 0;
        boolean hasUserLiked = false;
        if(postDAO.isHasUserLiked()){
            likeCount = postDAO.getLikeCount()-1;
            hasUserLiked = false;
        }else{
            likeCount = postDAO.getLikeCount()+1;
            hasUserLiked = true;
        }
        postDAO.setLikeCount(likeCount);
        postDAO.setHasUserLiked(hasUserLiked);
        holder.txtLikes.setText(postAdapter.getLikeText(likeCount));
        holder.imgLikes.setImageResource(postAdapter.getLikeImage(hasUserLiked));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getTitle().equals(Constants.LOGOUT)){
            clearSharedPreferences();
            clearBrowserCookies();
            MoveToLoginScreen();
        }
        return super.onOptionsItemSelected(item);
    }

    private void clearBrowserCookies(){
        CookieUtils.clearCookies(getApplicationContext());
    }

    private void clearSharedPreferences(){
        SharedPreferencesUtils.clearPreference(context);
    }

    private void MoveToLoginScreen(){
        Intent loginIntent = new Intent(this,LoginActivity.class);
        loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
    }
}
