package dao.inter;

import entity.User;
import entity.UserSkill;

import java.util.List;

public interface UserDaoInter {
    List<User> getAll();

    User getById(int userId);

    void addUser(User u);

    void updateUser(User u);

    void removeUser(int id);

}
