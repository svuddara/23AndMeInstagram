package api.vdp.visa.com.instagramapp.repository.webservices;

import java.util.Map;

import api.vdp.visa.com.instagramapp.repository.vo.InstagramImagesResponse;
import api.vdp.visa.com.instagramapp.repository.vo.InstagramLikeResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by svuddara on 9/16/17.
 */


public interface InstagramApiService {


    @GET("/v1/users/self/media/recent")
    Call<InstagramImagesResponse> getUserImages(@QueryMap Map<String,String> queryParam);

    @POST("/v1/media/{media-id}/likes")
    Call<InstagramLikeResponse> postLikes(@Path("media-id") String mediaId, @QueryMap Map<String,String> queryParam);
}
