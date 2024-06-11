package cn.bravedawn.common;

/**
 * @author : depers
 * @program : miaosha
 * @date : Created in 2024/6/5 19:05
 */
public enum RedisKeyEnum {

    LOGIN_USER_INFO("miaosha:login_user_info:%s", 3600, "用户登录信息"),
    ITEM_INFO("miaosha:item:%s", 300, "商品信息"),
    ;

    RedisKeyEnum(String key, int expireTime, String desc) {
        this.key = key;
        this.expireTime = expireTime;
        this.desc = desc;
    }

    private String key;
    private int expireTime; // 单位秒
    private String desc;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
