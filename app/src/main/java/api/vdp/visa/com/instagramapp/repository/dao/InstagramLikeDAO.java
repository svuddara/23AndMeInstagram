package api.vdp.visa.com.instagramapp.repository.dao;

/**
 * Created by svuddara on 9/20/17.
 */

public class InstagramLikeDAO {

    private int status;

    private Throwable throwable;

    private InstagramLikeDAO(InstagramLikeDAOBuilder builder){
        this.status = builder.status;
        this.throwable = builder.throwable;
    }

    public int getStatus() {
        return status;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public static class InstagramLikeDAOBuilder{

        private int status;

        private Throwable throwable;

        public InstagramLikeDAOBuilder status(int status){
            this.status = status;
            return this;
        }

        public InstagramLikeDAOBuilder error(Throwable throwable){
            this.throwable = throwable;
            return this;
        }

        public InstagramLikeDAO build(){
            return new InstagramLikeDAO(this);
        }
    }
}
