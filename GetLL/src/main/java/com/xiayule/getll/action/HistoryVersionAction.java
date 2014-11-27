package com.xiayule.getll.action;

import com.opensymphony.xwork2.Action;
import com.xiayule.getll.db.model.VersionHistory;
import com.xiayule.getll.db.service.HistoryVersionService;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tan on 14-11-27.
 */
public class HistoryVersionAction {


    private String title;
    private String content;
    private String versionName;
    private Date time;

    private HistoryVersionService historyVersionService;

    public String show() throws Exception {

        List<VersionHistory> versionHistories = historyVersionService.findAllVersionHistory();

        System.out.println(versionHistories);

        // 封装数据
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("versionHistories", versionHistories);

        // 传递数据到jsp
        HttpServletRequest request = ServletActionContext.getRequest();
        request.setAttribute("model", model);


        return Action.SUCCESS;
    }

    /**
     * 显示创建的jsp
     * @return
     */
    public String newHistoryVersion() {


        return Action.SUCCESS;
    }

    public String createHistoryVersion() {
//        todo: time 没有传过来

        System.out.println("title:" + title
                + " contnt:" + content
                + " versionName:" + versionName
                + " time:" + time);

        return Action.SUCCESS;
    }

    // set and get methods


    public void setHistoryVersionService(HistoryVersionService historyVersionService) {
        this.historyVersionService = historyVersionService;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public HistoryVersionService getHistoryVersionService() {
        return historyVersionService;
    }
}
