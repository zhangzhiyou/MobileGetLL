package com.xiayule.getll.service;

/**
 * Created by tan on 14-9-8.
 */
public interface AutoPlayService {
    /**
     * 为自己摇奖
     * @param mobile 自己的手机号
     */
    public void autoPlayForSelf(String mobile);

    /**
     * 为朋友摇奖
     * @param myMobile 自己的手机号
     * @param friendMobile
     */
    public void autoPlayForFriend(String myMobile, String friendMobile);
}
