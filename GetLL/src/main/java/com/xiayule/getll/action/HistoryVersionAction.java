package com.xiayule.getll.action;

import com.opensymphony.xwork2.Action;
import com.xiayule.getll.db.model.VersionHistory;
import com.xiayule.getll.db.service.HistoryVersionService;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tan on 14-11-27.
 */
public class HistoryVersionAction implements Action {

    private HistoryVersionService historyVersionService;

    @Override
    public String execute() throws Exception {

        List<VersionHistory> versionHistories = historyVersionService.findAllVersionHistory();

        System.out.println(versionHistories);

        // 封装数据
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("versionHistories", versionHistories);

        // 传递数据到jsp
        HttpServletRequest request = ServletActionContext.getRequest();
        request.setAttribute("model", model);


        return SUCCESS;
    }


    // set and get methods


    public void setHistoryVersionService(HistoryVersionService historyVersionService) {
        this.historyVersionService = historyVersionService;
    }
}
