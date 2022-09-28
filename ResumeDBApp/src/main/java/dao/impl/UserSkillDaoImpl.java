package dao.impl;

import dao.inter.AbstractDao;
import entity.Skill;
import entity.User;
import entity.UserSkill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import dao.inter.UserSkillDaoInter;

public class UserSkillDaoImpl extends AbstractDao implements UserSkillDaoInter {

    public UserSkill getUserSkill(ResultSet resultSet) throws Exception {
        int userSkillId = resultSet.getInt("id");
        int userId = resultSet.getInt("user_id");
        int skillId = resultSet.getInt("skill_id");
        int power = resultSet.getInt("power");
        String skillName = resultSet.getString("skill_name");

        return new UserSkill(userSkillId, new User(userId), new Skill(skillId, skillName), power);
    }

    @Override
    public List<UserSkill> getAllSkillByUser(int userId) {
        List<UserSkill> result = new ArrayList<>();
        try ( Connection c = connect()) {
            PreparedStatement preparedStmt = c.prepareStatement("select "
                    + "us.id,"
                    + "us.user_id,"
                    + "u.*, "
                    + "us.skill_id, "
                    + "s.name as skill_name, "
                    + "us.power "
                    + "from user_skill us "
                    + "left join user u on us.user_id = u.id "
                    + "left join skill s on us.skill_id = s.id "
                    + "where us.user_id = ?;");
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
    
    @Override
    public void insertUserSkill(UserSkill u) {
        Connection conn;
        boolean b = true;
        try {
            conn = connect();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO user_skill (skill_id , user_id ,power)"
                    + " VALUES (? , ? ,  ? ) ;");

            stmt.setInt(1, u.getSkill().getId());
            stmt.setInt(2, u.getUser().getId());
            stmt.setInt(3, u.getPower());

            b = stmt.execute();

        } catch (Exception ex) {
            System.err.println(ex);
            b = false;
        }
    }

    public void updateUserSkill(UserSkill u) {
        Connection conn;
        boolean b = true;
        try {
            conn = connect();
            PreparedStatement stmt = conn.prepareStatement("UPDATE user_skill SET skill_id = ? , user_id =? ,power =?  WHERE id = ? ;");

            stmt.setInt(1, u.getSkill().getId());
            stmt.setInt(2, u.getUser().getId());
            stmt.setInt(3, u.getPower());

            stmt.setInt(4, u.getId());

            b = stmt.execute();

        } catch (Exception ex) {
            System.err.println(ex);
            b = false;
        }
    }
    @Override
    public void removeUserSkill(int id) {
        Connection conn;
        try {
            conn = connect();

            PreparedStatement stmt = conn.prepareStatement("DELETE FROM user_skill WHERE ID=?;");
            stmt.setInt(1, id);
            conn.close();

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
} 
