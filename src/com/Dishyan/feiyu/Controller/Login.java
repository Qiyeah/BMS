package com.Dishyan.feiyu.Controller;

import com.Dishyan.feiyu.DAO.UserDAO;
import com.Dishyan.feiyu.Modle.Admins;
import com.Dishyan.feiyu.Modle.Users;
import com.Dishyan.feiyu.Util.GetData;
import com.Dishyan.feiyu.View.AdminPage;
import com.Dishyan.feiyu.View.userPage;

/**
 * Created by Qiyeah on 2015/10/4.
 */
public class Login {
    public void login() {
        for (int i = 0; i < 1; ) {
            GetData gd = new GetData();
            System.out.println("-------------------------��ӭʹ��ͼ�����ϵͳ----------------------------");
            System.out.println("|-1.��½ϵͳ\n|-2.ϵͳע��\n|-3.�ر�ϵͳ");
            System.out.println("-------------------------------------------------------------------------");
            String cmd1 = gd.getData();
            if ("1".equals(cmd1)) {
                i++;
                UserDAO ud = new UserDAO();
                System.out.println("��ѡ���½��ʽ��\n1. �û�\n2. ����Ա");
                String cmd2 = gd.getData();
                for (int j = 0; j < 1; ) {
                    boolean lg;
                    if ("1".equals(cmd2)) {
                        System.out.println("please enter your user name��");
                        String userName = gd.getData();
                        System.out.println("please enter your password��");
                        String userPass = gd.getData();
                        lg = ud.checkUserLogin(userName, userPass);
                        if (lg) {
                            System.out.println("ҳ����ʾ��land successfully ,connect to first page... ...");
                            j++;
                            Users user = ud.getUsers(userName);
                            new userPage().userCenter(user);
                        }
                    } else if ("2".equals(cmd2)) {
                        System.out.println("please enter your user name��");
                        String userName = gd.getData();
                        System.out.println("please enter your password��");
                        String userPass = gd.getData();
                        lg = ud.checkAdminLogin(userName, userPass);
                        if (lg) {
                            System.out.println("ҳ����ʾ��land successfully ,connect to first page... ...");
                            j++;
                            Admins admin = ud.getAdmin(userName);
                            new AdminPage().managerCenter(admin);
                        }
                    } else {
                        System.out.println("ҳ����ʾ��������������������");
                    }
                }
            } else if ("2".equals(cmd1)) {
                i++;
                new Register().reg_User();
            } else if ("3".equals(cmd1)) {
                i++;
                System.out.println("ҳ����ʾ��ϵͳ�����˳�... ...");
                System.exit(0);
            } else {
                System.out.println("ҳ����ʾ����������������ʾ����");
            }
        }
    }
}
