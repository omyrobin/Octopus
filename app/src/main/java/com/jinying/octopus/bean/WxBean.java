package com.jinying.octopus.bean;

/**
 * Created by omyrobin on 2017/9/1.
 */

public class WxBean {

    /**
     * access_token : XbdwGFQL2OEPlxweeqZUsiilkSCutYCtekOZbbc2m3EawWQGSTB2utfnYliRLofINODIkyD_lvxSu6GKEPYDlg
     * expires_in : 7200
     * refresh_token : voIkrZY-v_ZGCwQszXwEA1Hw73GNMf1Y569rTf-QDfdPraJjSZW0fRJk9Wf_NwxAVpTgl7xofbkf3jcm_xrH0Q
     * openid : oMI6c05WLeYtXBy28p56f85MA32c
     * scope : snsapi_userinfo
     * unionid : ojMEvuP4gPRdT0TSSqaBn7sHZhjY
     */

    private String access_token;
    private int expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
    private String unionid;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
