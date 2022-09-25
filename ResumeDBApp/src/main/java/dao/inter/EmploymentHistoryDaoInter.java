package dao.inter;

import entity.EmploymentHistory;
import entity.UserSkill;

import java.util.List;

public interface EmploymentHistoryDaoInter {

    public List<EmploymentHistory> getAllEmploymentHistoryByUserId(int userId);
}
