package com.xiayule.getll.draw.job.impl;

import com.xiayule.getll.draw.job.AutoPlayJob;
import com.xiayule.getll.service.OwnService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by tan on 14-11-16.
 * 自动领取流量币任务
 */
public class AutoReceiveJob implements AutoPlayJob {

    private static Logger logger = LogManager.getLogger(AutoPlayJob.class.getName());

    private OwnService ownService;

    @Override
    public void autoPlay(String mobile) {
        try {
            if (ownService.isHasFlowScoreTransferGifts(mobile)){

                Thread.sleep(1000);

                ownService.transferGiftsReceiveAll(mobile);

                Thread.sleep(1000);
            }
        } catch (Exception e) {
            logger.info(mobile + " AutoReceiveJob: 出错");
        }
    }

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger logger) {
        AutoReceiveJob.logger = logger;
    }

    public OwnService getOwnService() {
        return ownService;
    }

    public void setOwnService(OwnService ownService) {
        this.ownService = ownService;
    }
}
