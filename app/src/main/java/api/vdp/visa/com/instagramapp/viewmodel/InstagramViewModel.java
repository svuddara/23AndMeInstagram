package api.vdp.visa.com.instagramapp.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import api.vdp.visa.com.instagramapp.repository.dao.InstagramLikeDAO;
import api.vdp.visa.com.instagramapp.repository.dao.InstagramUserPostsDAO;
import api.vdp.visa.com.instagramapp.repository.InstagramRepository;
import api.vdp.visa.com.instagramapp.repository.impl.InstagramRepositoryImpl;

/**
 * Created by svuddara on 9/16/17.
 */

public class InstagramViewModel extends ViewModel{

    private MediatorLiveData<InstagramUserPostsDAO> userImagesLiveData;
    private MediatorLiveData<InstagramLikeDAO> userLikesLiveData;
    private InstagramRepository instagramRepository;

    public InstagramViewModel(){
        userImagesLiveData = new MediatorLiveData<>();
        userLikesLiveData = new MediatorLiveData<>();
        instagramRepository = new InstagramRepositoryImpl();
    }


    public LiveData<InstagramUserPostsDAO> getUserImages(String accessToken){
        userImagesLiveData.addSource(
                instagramRepository.getUserImages(accessToken),
                new Observer<InstagramUserPostsDAO>() {
                    @Override
                    public void onChanged(@Nullable InstagramUserPostsDAO instagramUserPostsDAO) {
                        userImagesLiveData.setValue(instagramUserPostsDAO);
                    }
                }
        );

        return userImagesLiveData;
    }

    public  MediatorLiveData<InstagramLikeDAO> postUserLike(String accessToken, String mediaId){
        userLikesLiveData.addSource(
                instagramRepository.postLikes(accessToken,mediaId),
                new Observer<InstagramLikeDAO>() {
                    @Override
                    public void onChanged(@Nullable InstagramLikeDAO instagramLikeDAO) {
                        userLikesLiveData.setValue(instagramLikeDAO);
                    }
                }
        );
        return userLikesLiveData;
    }

}
