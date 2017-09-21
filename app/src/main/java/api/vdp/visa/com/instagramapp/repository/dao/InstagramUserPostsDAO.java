package api.vdp.visa.com.instagramapp.repository.dao;

import java.util.List;

/**
 * Created by svuddara on 9/16/17.
 */

public class InstagramUserPostsDAO {


    private List<PostDAO> postDAOs;

    private Throwable error;

    private InstagramUserPostsDAO(InstagramUserImagesDAOBuilder builder){

        this.postDAOs = builder.postDAOs;
        this.error = builder.error;
    }


    public List<PostDAO> getPostDAOs() {
        return postDAOs;
    }

    public void setPostDAOs(List<PostDAO> postDAOs) {
        this.postDAOs = postDAOs;
    }

    public Throwable getError() {
        return error;
    }

    public static class InstagramUserImagesDAOBuilder{

        private Throwable error;
        private List<PostDAO> postDAOs;


        public InstagramUserImagesDAOBuilder images(List<PostDAO> postDAOs){
            this.postDAOs = postDAOs;
            return this;
        }

        public InstagramUserImagesDAOBuilder error(Throwable error){
            this.error = error;
            return this;
        }

        public InstagramUserPostsDAO build(){
            return new InstagramUserPostsDAO(this);
        }
    };
}
