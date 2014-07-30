package com.xiayule.getll.service;

/**
 * Created by tan on 14-7-29.
 */
public interface RegisterCodeService {
    /**
     * 生成注册码，将注册码存储如 redis，并将生成的注册码返回
     * @return
     */
    public String generateRegisterCode();

    /**
     * 检查注册号是否合法
     * @param registerCode
     * @return
     */
    public boolean isValid(String registerCode);

    /**
     * 删除指定注册号
     * @param registerCode
     */
    public void removeRegisterCode(String registerCode);
}
