package com.Dishyan.feiyu.View;

import com.Dishyan.feiyu.DAO.SendBackDAO;
import com.Dishyan.feiyu.Modle.Users;

/**
 * Created by Qiyeah on 2015/10/15.
 */
public class SendBackBookPage {
    public void sendBackbook(Users user) {
        SendBackDAO sdb = new SendBackDAO();
        if (sdb.getBorrowList(user)) {

        }

    }
}
