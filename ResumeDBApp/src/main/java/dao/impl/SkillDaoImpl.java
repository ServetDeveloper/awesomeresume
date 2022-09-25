package dao.impl;

import dao.inter.AbstractDao;
import entity.Country;
import entity.Skill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import dao.inter.CountryDaoInter;
import dao.inter.SkillDaoInter;


public class SkillDaoImpl extends AbstractDao implements SkillDaoInter {

    @Override
    public List<Skill> getAll() {
        List<Skill> list = new ArrayList<>();
        try(Connection c = connect()) {
            PreparedStatement preparedStmt = c.prepareStatement("select * from skill;");
            preparedStmt.execute();
            ResultSet rs = preparedStmt.getResultSet();
            while (rs.next()) {
                int id = rs.getInt("id");
                String skillName = rs.getString("name");
                Skill skill = new Skill(id, skillName);
                list.add(skill);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }
}
