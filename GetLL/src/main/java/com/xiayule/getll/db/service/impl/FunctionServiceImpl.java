package com.xiayule.getll.db.service.impl;

import com.xiayule.getll.db.dao.FunctionDao;
import com.xiayule.getll.db.model.Function;
import com.xiayule.getll.db.service.FunctionService;

import java.util.List;

/**
 * Created by tan on 15-2-10.
 */
public class FunctionServiceImpl implements FunctionService{

    private FunctionDao functionDao;

    public Function getByMobile(String mobile) {
        return functionDao.getByMobile(mobile);
    }

    public Boolean statusForFriend(String mobile) {

        Function function = functionDao.getByMobile(mobile);
        if (function == null) {
            return false;
        } else {
            return function.getForFriend();
        }

    }

    public Boolean statusAutoReceive(String mobile) {
        Function function = functionDao.getByMobile(mobile);
        if (function == null) {
            return false;
        } else {
            return function.getAutoReceive();
        }
    }

    public void setForFriend(String mobile, Boolean forFriend) {
        functionDao.updateForFriend(mobile, forFriend);
    }

    public void setAutoReceive(String mobile, Boolean autoReceive) {
        functionDao.updateAutoReceive(mobile, autoReceive);
    }

    public void save(Function function) {
        functionDao.save(function);
    }

    @Override
    public List<String> getAllForFriend() {
        return functionDao.getAllForFriend();
    }

    @Override
    public List<String> getAllAutoReceive() {
        return functionDao.getAllAutoReceive();
    }

    // set and get methods

    public FunctionDao getFunctionDao() {
        return functionDao;
    }

    public void setFunctionDao(FunctionDao functionDao) {
        this.functionDao = functionDao;
    }
}
