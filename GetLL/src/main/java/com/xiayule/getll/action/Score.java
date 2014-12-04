package com.xiayule.getll.action;

import com.opensymphony.xwork2.Action;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by tan on 14-8-19.
 */
@Component
@Scope("prototype")
public class Score implements Action {
    private String productType_n;
    //id
    private String id_n;
    //giveCredit
    private String gc_n;
    //prizeName
    private String pn_n;
    //nowLostCredit
    private String nc_n;
    private String redirect;


    @Override
    public String execute() throws Exception {
//        System.out.println("productType: " + productType_n);
//        System.out.println("id_n:" + id_n);
//        System.out.println("gc_n:" + gc_n);
//        System.out.println("pn_n:" + pn_n);
//        System.out.println("nc_n:" + nc_n);
//        System.out.println("redirect:" + redirect);

        return Action.SUCCESS;
    }

    // get and set methods


    public String getProductType_n() {
        return productType_n;
    }

    public void setProductType_n(String productType_n) {
        this.productType_n = productType_n;
    }

    public String getId_n() {
        return id_n;
    }

    public void setId_n(String id_n) {
        this.id_n = id_n;
    }

    public String getGc_n() {
        return gc_n;
    }

    public void setGc_n(String gc_n) {
        this.gc_n = gc_n;
    }

    public String getPn_n() {
        return pn_n;
    }

    public void setPn_n(String pn_n) {
        this.pn_n = pn_n;
    }

    public String getNc_n() {
        return nc_n;
    }

    public void setNc_n(String nc_n) {
        this.nc_n = nc_n;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }
}
