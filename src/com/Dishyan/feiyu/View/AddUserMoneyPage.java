package com.Dishyan.feiyu.View;

import com.Dishyan.feiyu.DAO.UserDAO;
import com.Dishyan.feiyu.Modle.Users;
import com.Dishyan.feiyu.Util.GetData;

/**
 * Created by Qiyeah on 2015/10/15.
 */
public class AddUserMoneyPage {
    public void addUserMoney(Users user) {
        System.out.println("输入金额：");
        GetData gd = new GetData();
        double money = Double.parseDouble(gd.getData());
        UserDAO userDAO = new UserDAO();
        boolean flag = userDAO.addMoney(user, money);
        if (flag) {
            System.out.println("帐户 " + user.getUser_Name() + "充值成功，帐户余额为：" + userDAO.queryMoney(user));
            System.out.println("继续充值：Y / N");
            String cmd = gd.getData();
            if (cmd.matches("[Yy]")) {
                addUserMoney(user);
            } else {
                new userPage().userCenter(user);
            }
        }
    }
}
