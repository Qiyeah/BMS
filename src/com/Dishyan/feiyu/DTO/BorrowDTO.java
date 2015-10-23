package com.Dishyan.feiyu.DTO;

import com.Dishyan.feiyu.DAO.BaseDAO;
import com.Dishyan.feiyu.DAO.BookDAO;
import com.Dishyan.feiyu.Modle.*;
import com.Dishyan.feiyu.Util.GetData;
import com.Dishyan.feiyu.Util.RandID;
import com.Dishyan.feiyu.View.userPage;

import java.awt.print.Book;
import java.util.ArrayList;

/**
 * Created by Qiyeah on 2015/10/15.
 */
public class BorrowDTO extends BaseDAO {
    private Users user;
    private Borrow br;
    private BorrowInfo bri;
    private Book bk;
    private GetData gd = new GetData();

    public BorrowDTO() {
    }

    public BorrowDTO(Users user, Book bk, Borrow br, BorrowInfo bri) {
        this.setUser(user);
        this.setBr(br);
        this.setBri(bri);
        this.setBk(bk);
    }

    public Borrow getBr() {
        return br;
    }

    public void setBr(Borrow br) {
        this.br = br;
    }

    public Book getBk() {
        return bk;
    }

    public void setBk(Book bk) {
        this.bk = bk;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }


    public BorrowInfo getBri() {
        return bri;
    }

    public void setBri(BorrowInfo bri) {
        this.bri = bri;
    }

    public void borrow(Users user, Books bk, Borrow br) {
        if (user.getUser_Money() >= 0 && bk.getBk_State() == 1) {
            boolean brFlag = doUpdate("insert into 1Borrow (br_ID,user_ID) values(?,?)", new RandID().getID(RandID.BorrowID), user.getUser_ID());
            if (brFlag) {
                System.out.println("���鶯����ʼ");
                boolean briFlag = doUpdate("insert into BorrowInfo (bri_ID,br_ID,bk_ID)values(?,?,?)", new RandID().getID(RandID.BorrowInfoID), br.getBr_ID(), bk.getBk_ID());
                if (briFlag) {
                    System.out.println("��������������");
                    boolean userFlag = doUpdate("update Users set user_Money =user_Money - ? where user_ID = ?", bk.getBk_Price(), user.getUser_ID());
                    boolean bkFlag = doUpdate("update Books set bk_Count = bk_Count - 1 where bk_ID = ?", bk.getBk_ID());

                    if (userFlag && bkFlag) {
                        System.out.printf("�������");
                        String cmd = gd.getData();
                        if (cmd.matches("[Yy]")) {
                            borrow(user, bk, br);
                        } else {
                            new userPage().userCenter(user);
                        }
                    }
                }
            } else {
                System.out.println("���鶯�����ɹ�");
            }
        }
    }

    public Books getBook() {
        /*
        borrow��״̬��1�����鶯����� 0�����ѻ�
        borrowInfo��״̬��0��������Ѿ��黹  1���鱻�û����
        book��״̬ �� 0�����ɽ�  1���ɽ�  2��Ԥ�¼�  3��Ԥ�ϼ�
        Users��״̬ ��0������״̬��a,����������ﵽ����b,�������ڳ�ʱ��c,�ʻ����Ϊ���������Ѿ���ʧ���۳�
            ����󣩣� 1������״̬ 3���û�ע��

        1.��borrow��������һ����¼ br_ID �������ɹ�����ǰ��¼�е�br_State ��ֵ��Ϊ1����֮Ϊ0
        2.��borrowInfo��������N����¼ bri_ID  �������ɹ�����ǰ��¼�е�bri_State ��ֵ��Ϊ1����֮Ϊ0
        3.һ���û����Խ��Σ�һ�ο��Խ�౾
        4.�����ǰ��������ɽ� bk_State = 1�������û���״̬Ϊ user_State = 1��
        5.�漰���Ķ����У�Borrow1 , Users ,BorrowInfo,Book
        6.���鶯������ʱ���Ѿ�������Users�����Book����Book������ͨ���û���ѯͼ����е���Ϣ�õ�����

     */
        BookDAO bkDAO = new BookDAO();
        //��һ����ȡ�û�����Ķ���
        GetData gd = new GetData();
        //��ѯͼ�����
        ArrayList<BookType> bigType = bkDAO.getBookType("0");
        //��ӡ��ѯ���
        printTypeResults(bigType);
        System.out.println("������Ҫ��ѯ��ͼ�鶥���ࣺ");
        int index = Integer.parseInt(gd.getData()) - 1;
        //��ȡѡ�����Ŀ��ID
        BookType subType = bigType.get(index);
        //���һ�ȡ������Ŀ������Ŀ
        ArrayList<BookType> smallType = bkDAO.getBookType(subType.getBt_RID());
        //��ӡ��ѯ���
        printTypeResults(smallType);
        System.out.println("������Ҫ��ѯ��ͼ�鶥���ࣺ");
        int subIndex = Integer.parseInt(gd.getData()) - 1;
        String bt_ID = smallType.get(subIndex).getBt_ID();
        //���һ�ȡ������Ŀ������Ŀ��ͼ���б�
        ArrayList<Books> books = bkDAO.findBooks(bt_ID);
        printBookResults(books);
        System.out.println("ѡ������ͼ�飺");
        int bkIndex = Integer.parseInt(gd.getData()) - 1;
        return books.get(bkIndex);
    }

    public void printTypeResults(ArrayList<BookType> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println("|--" + (i + 1) + "." + list.get(i).getBt_Name());
        }
    }

    public void printBookResults(ArrayList<Books> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println("|--" + (i + 1) + "." + list.get(i).getBk_Name());
        }
    }


}
