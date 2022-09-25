package dao.impl;

import dao.inter.AbstractDao;
import entity.Country;
import entity.Skill;
import entity.User;
import entity.UserSkill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import dao.inter.UserDaoInter;
import dao.inter.UserSkillDaoInter;


public class UserSkillDaoImpl extends AbstractDao implements UserSkillDaoInter {
    public UserSkill getUserSkill(ResultSet resultSet) throws Exception{
        int userId = resultSet.getInt("id");
        int skillId = resultSet.getInt("skill_id");
        int power = resultSet.getInt("power");
        String skillName = resultSet.getString("skill_name");


        return new UserSkill(null, new User(userId), new Skill(skillId, skillName), power);
    }

    @Override
    public List<UserSkill> getAllSkillByUser(int userId) {
        List<UserSkill> result = new ArrayList<>();
        try(Connection c = connect()) {
            PreparedStatement preparedStmt = c.prepareStatement("select " +
                    "u.*, " +
                    "us.skill_id, " +
                    "s.name as skill_name, " +
                    "us.power " +
                    "from user_skill us " +
                    "left join user u on us.user_id = u.id " +
                    "left join skill s on us.skill_id = s.id " +
                    "where us.user_id = ?;");
            preparedStmt.setInt(1, userId);
            preparedStmt.execute();
            ResultSet resultSet = preparedStmt.getResultSet();
            while (resultSet.next()) {
                UserSkill u = getUserSkill(resultSet);
                result.add(u);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }


}
