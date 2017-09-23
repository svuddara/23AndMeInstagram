package api.vdp.visa.com.instagramapp.repository.impl;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import api.vdp.visa.com.instagramapp.repository.dao.InstagramLikeDAO;
import api.vdp.visa.com.instagramapp.repository.vo.InstagramLikeResponse;
import api.vdp.visa.com.instagramapp.repository.webservices.InstagramApiService;
import api.vdp.visa.com.instagramapp.repository.InstagramRepository;
import api.vdp.visa.com.instagramapp.repository.dao.PostDAO;
import api.vdp.visa.com.instagramapp.repository.dao.InstagramUserPostsDAO;
import api.vdp.visa.com.instagramapp.repository.vo.InstagramPost;
import api.vdp.visa.com.instagramapp.repository.vo.InstagramImagesResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import api.vdp.visa.com.instagramapp.utils.OAuthConstants;

/**
 * Created by svuddara on 9/16/17.
 */

public class InstagramRepositoryImpl implements InstagramRepository {

    private static String BASE_URL = "https://api.instagram.com";
    InstagramApiService instagramApiService;

    public InstagramRepositoryImpl() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        instagramApiService = retrofit.create(InstagramApiService.class);
    }

    @Override
    public LiveData<InstagramUserPostsDAO> getUserImages(String accessToken) {

        final MutableLiveData<InstagramUserPostsDAO> userImagesLiveData = new MutableLiveData<>();

        Map<String, String> queryParamsMap = buildQueryMap(accessToken);
        Call<InstagramImagesResponse> userImagesResponse = instagramApiService.getUserImages(queryParamsMap);
        userImagesResponse.enqueue(new Callback<InstagramImagesResponse>() {
            @Override
            public void onResponse(Call<InstagramImagesResponse> call, Response<InstagramImagesResponse> response) {
                InstagramImagesResponse instagramImagesResponse = response.body();
                userImagesLiveData.setValue(getUserImagesDAO(instagramImagesResponse));
            }

            @Override
            public void onFailure(Call<InstagramImagesResponse> call, Throwable t) {
                userImagesLiveData.setValue(new InstagramUserPostsDAO.InstagramUserImagesDAOBuilder().error(t).build());
            }
        });

        return userImagesLiveData;
    }


    @Override
    public LiveData<InstagramLikeDAO> changeLikesStatus(String accessToken, String mediaId,boolean isLike) {
        final MutableLiveData<InstagramLikeDAO> likeStatusLiveData = new MutableLiveData<>();
        Call<InstagramLikeResponse> likeStatusChangeResponse =  getLikeStatusResponse(accessToken,mediaId,isLike);
        likeStatusChangeResponse.enqueue(new Callback<InstagramLikeResponse>() {
            @Override
            public void onResponse(Call<InstagramLikeResponse> call, Response<InstagramLikeResponse> response) {
                InstagramLikeResponse likeResponse = response.body();
                InstagramLikeDAO likeDAO = buildInstagramLikeDAO(likeResponse);
                likeStatusLiveData.setValue(likeDAO);
            }

            @Override
            public void onFailure(Call<InstagramLikeResponse> call, Throwable t) {
                InstagramLikeDAO likeDAO = new InstagramLikeDAO.InstagramLikeDAOBuilder().error(t).build();
                likeStatusLiveData.setValue(likeDAO);
            }
        });
        return likeStatusLiveData;
    }

    private Call<InstagramLikeResponse> getLikeStatusResponse(String accessToken,String mediaId,boolean isLike){
        Call<InstagramLikeResponse> likeStatusResponse = null;
        Map<String, String> queryParamsMap = buildQueryMap(accessToken);
        if(isLike){
            likeStatusResponse =  instagramApiService.postLikes(mediaId,queryParamsMap);
        }else{
            likeStatusResponse = instagramApiService.deleteLike(mediaId,queryParamsMap);
        }
        return likeStatusResponse;
    }

    private Map<String,String> buildQueryMap(String accessToken){
        Map<String, String> queryParamsMap = new HashMap<String, String>();
        queryParamsMap.put(OAuthConstants.ACCESS_TOKEN,accessToken);
        return queryParamsMap;
    }

    private InstagramUserPostsDAO getUserImagesDAO(InstagramImagesResponse instagramImagesResponse){
        List<InstagramPost> imageDetailsList = instagramImagesResponse.getData();
        List<PostDAO> postDAOList = new ArrayList<>();
        if(imageDetailsList != null){
            for(InstagramPost imageDetail:imageDetailsList){
                PostDAO postDAO = getImageDAO(imageDetail);
                postDAOList.add(postDAO);
            }
        }
        return new InstagramUserPostsDAO.InstagramUserImagesDAOBuilder().images(postDAOList)
                .build();
    }

    private PostDAO getImageDAO(InstagramPost imageDetail){
        String caption = "";
        if(imageDetail.getCaption() != null){
            caption = imageDetail.getCaption().getText();
        }
        return new PostDAO.ImageDAOBuilder()
                .id(imageDetail.getId())
                .likeCount(imageDetail.getLikes().getCount())
                .hasUserLiked(imageDetail.getUserHasLiked())
                .imageUrl(imageDetail.getImages().getStandardResolution().getUrl())
                .height(imageDetail.getImages().getStandardResolution().getHeight())
                .width(imageDetail.getImages().getStandardResolution().getWidth())
                .caption(caption)
                .createdTime(imageDetail.getCreatedTime())
                .userName(imageDetail.getUser().getUsername())
                .profilePicture(imageDetail.getUser().getProfilePicture())
                .build();
    }


    private InstagramLikeDAO buildInstagramLikeDAO(InstagramLikeResponse likeResponse){
        return new InstagramLikeDAO.InstagramLikeDAOBuilder()
                .status(likeResponse.getMeta().getCode())
                .build();
    }
}
