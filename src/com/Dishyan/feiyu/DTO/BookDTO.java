package com.Dishyan.feiyu.DTO;

import com.Dishyan.feiyu.DAO.BookDAO;
import com.Dishyan.feiyu.DAO.BorrowDAO;
import com.Dishyan.feiyu.Modle.Books;
import com.Dishyan.feiyu.Modle.Users;
import com.Dishyan.feiyu.Util.GetData;
import com.Dishyan.feiyu.View.BorrowBookPage;
import com.Dishyan.feiyu.View.userPage;

import java.util.ArrayList;

/**
 * Created by Qiyeah on 2015/10/14.
 */
public class BookDTO {
    private GetData gd;
    private Users user;
    private Books book;

    public BookDTO(Users user, Books book, GetData gd) {
        this.setUser(user);
        this.setBook(book);
        this.setGd(gd);
    }

    public BookDTO() {

    }

    public GetData getGd() {
        return gd;
    }

    public void setGd(GetData gd) {
        this.gd = gd;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Books getBook() {
        return book;
    }

    public void setBook(Books book) {
        this.book = book;
    }

    public void printTypeList(ArrayList<String> bookType) {
        BookDAO bookDAO = new BookDAO();
        //图书顶级类

        System.out.println("-------------------------------bookType------------------------------------");
        for (int i = 0; i < bookType.size(); i++) {
            System.out.println((i + 1) + "." + bookType.get(i));
        }
    }

    public void printSmallTypeList(ArrayList<String> listType) {
        //图书小类
        BookDAO bookDAO = new BookDAO();
        System.out.println("-------------------------------listType------------------------------------");
        for (int i = 0; i < listType.size(); i++) {
            System.out.println((i + 1) + "." + listType.get(i));
        }
    }

    public void printBookList(String smallType) {
        BookDAO bookDAO = new BookDAO();
        int bk_Count = bookDAO.countBooks(smallType);
        int pages = bk_Count / 5 + 1;
        for (int i = 1; i <= pages + 1 && i >= 0; ) {
            ArrayList<Books> bookList = bookDAO.findBooks(smallType, i);
            if (i == pages + 1 || i == 0) {
                System.out.println("当前页不存在,跳转到首页");
                i = 1;
            } else {
                System.out.println("-------------------------------listBook------------------------------------");
                for (int j = 0; j < bookList.size(); j++) {
                    System.out.println((j + 1) + "." + bookList.get(j).getBk_Name());
                }
                System.out.println("N  下一页\t\tP  上一页");
                String cmd = gd.getData();
                if (cmd.matches("[Pp]")) {

                    i--;
                } else if (cmd.matches("[Nn]")) {
                    i++;
                } else if (cmd.matches("[1-5]")) {
                    bookList.get(Integer.parseInt(cmd) - 1);
                }
            }
        }
    }

    public void borrowBook() {
        BorrowDAO borrowDAO = new BorrowDAO();
        if (!borrowDAO.borrow(book.getBk_ID())) {
            String br_ID = borrowDAO.borrowBook(user);
            boolean brcg = borrowDAO.setBorrowInfo(br_ID, book.getBk_ID());
            if (brcg) {
                System.out.println("借书成功！");
                System.out.println("继续借书：Y / N");
                String cmd = gd.getData();
                if (cmd.matches("[Yy]")) {
                    new BorrowBookPage().borrowBook(user);
                } else {
                    new userPage().userCenter(user);
                }
            }
        } else {
            System.out.println(book.getBk_Name() + "处于不可借阅状态");
        }
    }
    /*public void xx(){
        BookDAO bookDAO = new BookDAO();
        //图书顶级类
        ArrayList<String> bookType = bookDAO.getBookType();
        System.out.println("-------------------------------bookType------------------------------------");
        for (int i = 0; i < bookType.size(); i++) {
            System.out.println((i + 1) + "." + bookType.get(i));
        }
        int index = Integer.parseInt(gd.getData()) - 1;
        String type = bookType.get(index);

        //图书小类
        ArrayList<String> listType = bookDAO.listBookType(type);
        System.out.println("-------------------------------listType------------------------------------");
        for (int i = 0; i < listType.size(); i++) {
            System.out.println((i + 1) + "." + listType.get(i));
        }
        int index1 = Integer.parseInt(gd.getData()) - 1;
        String bt_Name = listType.get(index1);


        int bk_Count = bookDAO.countBooks(type);
        int pages = bk_Count/5+1;
        for (int i = 1; i <= pages+1 && i >=0;) {
            GetData gd = new GetData();
            ArrayList<Books> bookList = bookDAO.findBooks(type, i);
            if (i==pages+1||i ==0){
                System.out.println("当前页不存在,跳转到首页");
                i=1;
            }else {
                System.out.println("-------------------------------listBook------------------------------------");
                for (int j = 0; j < bookList.size(); j++) {
                    System.out.println((j + 1) + "." + bookList.get(j).getBk_Name());
                }
                System.out.println("N  下一页\t\tP  上一页");
                String cmd = gd.getData();
                if (cmd.matches("[Pp]")){
                    i--;
                }else if (cmd.matches("[Nn]")){
                    i++;
                }else if (cmd.matches("[1-5]")){
                     bookList.get(Integer.parseInt(cmd)-1);
                }
            }
        }


        BorrowDAO borrowDAO = new BorrowDAO();
        if (!borrowDAO.borrow(book.getBk_ID())) {
            String br_ID = borrowDAO.borrowBook(user);
            boolean brcg = borrowDAO.setBorrowInfo(br_ID, book.getBk_ID());
            if (brcg) {
                System.out.println("借书成功！");
                System.out.println("继续借书：Y / N");
                String cmd = gd.getData();
                if (cmd.matches("[Yy]")){
                    new BorrowBookPage().borrowBook(user);
                }else {
                    new userPage().userCenter(user);
                }
            }
        } else {
            System.out.println(book.getBk_Name() + "处于不可借阅状态");
        }
    }*/
    /*public Books getBook(String type){

        int bk_Count = countBooks(type);
        int pages = bk_Count/5+1;
        for (int i = 1; i <= pages+1 && i >=0;) {
            GetData gd = new GetData();
            ArrayList<Books> bookList = findBooks(type, i);
            if (i==pages+1||i ==0){
                System.out.println("当前页不存在,跳转到首页");
                i=1;
            }else {
                System.out.println("-------------------------------listBook------------------------------------");
                for (int j = 0; j < bookList.size(); j++) {
                    System.out.println((j + 1) + "." + bookList.get(j).getBk_Name());
                }
                System.out.println("N  下一页\t\tP  上一页");
                String cmd = gd.getData();
                if (cmd.matches("[Pp]")){
                    i--;
                }else if (cmd.matches("[Nn]")){
                    i++;
                }else if (cmd.matches("[1-5]")){
                    return bookList.get(Integer.parseInt(cmd)-1);
                }
            }
        }
        return null;
    }*/
}
