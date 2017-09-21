package api.vdp.visa.com.instagramapp.repository.dao;

/**
 * Created by svuddara on 9/16/17.
 */

public class PostDAO {

    private String id;

    private String imageUrl;

    private boolean hasUserLiked;

    private int likeCount;

    private int height;

    private int width;

    private String caption;

    private String profilePicture;

    private String userName;

    private String createdTime;

    private PostDAO(ImageDAOBuilder builder){
        this.id = builder.id;
        this.imageUrl = builder.imageUrl;
        this.hasUserLiked = builder.hasUserLiked;
        this.likeCount = builder.likeCount;
        this.height = builder.height;
        this.width = builder.width;
        this.caption = builder.caption;
        this.profilePicture = builder.profilePicture;
        this.userName = builder.userName;
        this.createdTime = builder.createdTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isHasUserLiked() {
        return hasUserLiked;
    }

    public void setHasUserLiked(boolean hasUserLiked) {
        this.hasUserLiked = hasUserLiked;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public static class ImageDAOBuilder {

        private String id;

        private String imageUrl;

        private boolean hasUserLiked;

        private int likeCount;

        private int height;

        private int width;

        private String caption;

        private String profilePicture;

        private String userName;

        private String createdTime;

        public ImageDAOBuilder id(String id){
            this.id = id;
            return this;
        }

        public ImageDAOBuilder imageUrl(String imageUrl){
            this.imageUrl =  imageUrl;
            return this;
        }

        public ImageDAOBuilder hasUserLiked(boolean hasUserLiked){
            this.hasUserLiked =  hasUserLiked;
            return this;
        }

        public ImageDAOBuilder likeCount(int likeCount){
            this.likeCount =  likeCount;
            return this;
        }

        public ImageDAOBuilder height(int height){
            this.height = height;
            return this;
        }

        public ImageDAOBuilder width(int width){
            this.width = width;
            return this;
        }

        public ImageDAOBuilder caption(String caption){
            this.caption = caption;
            return this;
        }

        public ImageDAOBuilder profilePicture(String profilePicture){
            this.profilePicture = profilePicture;
            return this;
        }

        public ImageDAOBuilder userName(String userName){
            this.userName = userName;
            return this;
        }

        public ImageDAOBuilder createdTime(String createdTime){
            this.createdTime = createdTime;
            return this;
        }

        public PostDAO build(){
            return new PostDAO(this);
        }
    };
}
