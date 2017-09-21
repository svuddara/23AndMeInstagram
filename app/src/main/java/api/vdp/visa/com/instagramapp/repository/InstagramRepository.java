package api.vdp.visa.com.instagramapp.repository;

import android.arch.lifecycle.LiveData;

import api.vdp.visa.com.instagramapp.repository.dao.InstagramLikeDAO;
import api.vdp.visa.com.instagramapp.repository.dao.InstagramUserPostsDAO;

/**
 * Created by svuddara on 9/16/17.
 */

public interface InstagramRepository {

     public LiveData<InstagramUserPostsDAO> getUserImages(String accessToken);

     public LiveData<InstagramLikeDAO> changeLikesStatus(String accessToken, String mediaId,boolean isLike);
}
