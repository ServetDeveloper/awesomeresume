package dao.inter;

import entity.UserSkill;

import java.util.List;

public interface UserSkillDaoInter {
    public List<UserSkill> getAllSkillByUser(int userId);
    
    public void removeUserSkill(int id);
    
    public void insertUserSkill(UserSkill u);
}
