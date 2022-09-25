package dao.impl;

import dao.inter.AbstractDao;
import entity.EmploymentHistory;
import entity.Skill;
import entity.User;
import entity.UserSkill;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import dao.inter.UserSkillDaoInter;
import dao.inter.EmploymentHistoryDaoInter;


public class EmploymentHistoryDaoImpl extends AbstractDao implements EmploymentHistoryDaoInter {
    public EmploymentHistory getEmploymentHistory(ResultSet resultSet) throws Exception{
        String header = resultSet.getString("header");
        Date beginDate = resultSet.getDate("begin_date");
        Date endDate = resultSet.getDate("end_date");
        String jobDescription = resultSet.getString("job_description");
        int userId = resultSet.getInt("user_id");
        EmploymentHistory emp = new EmploymentHistory(null, header, beginDate, endDate, jobDescription, new User(userId));
        return emp;
    }

    @Override
    public List<EmploymentHistory> getAllEmploymentHistoryByUserId(int userId) {
        List<EmploymentHistory> result = new ArrayList<>();
        try(Connection c = connect()) {
            PreparedStatement preparedStmt = c.prepareStatement("select * from employment_history " +
                    "where id = ?;");
            preparedStmt.setInt(1, userId);
            preparedStmt.execute();
            ResultSet resultSet = preparedStmt.getResultSet();
            while (resultSet.next()) {
                EmploymentHistory emp = getEmploymentHistory(resultSet);
                result.add(emp);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }


}
