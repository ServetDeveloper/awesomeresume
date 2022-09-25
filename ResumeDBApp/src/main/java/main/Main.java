package main;

import dao.impl.CountryDaoImpl;
import dao.inter.*;

public class Main {

    public static void main(String[] args) {
        SkillDaoInter empHistory = Context.instanceSkillDao(); //loosely coupling
        //UserDAOInter userDao2 = new UserDaoImpl();//thightly coupling
        System.out.println(empHistory.getAll());
    }
}
