
package api.vdp.visa.com.instagramapp.repository.vo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InstagramLikeResponse {

    @SerializedName("data")
    @Expose
    private Object data;

    @SerializedName("meta")
    @Expose
    private Meta meta;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

}
