package dao.impl;

import entity.Country;
import entity.User;
import dao.inter.AbstractDao;

import java.sql.*;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import dao.inter.UserDaoInter;

public class UserDaoImpl extends AbstractDao implements UserDaoInter {

    @Override
    public List<User> getAll() {
        List<User> result = new ArrayList<>();
        try ( Connection c = connect()) {
            Statement stmt = c.createStatement();
            stmt.execute("select u.*, n.nationality as nationality, c.name as birthplace from user u"
                    + " left join country n on u.nationality_id = n.id"
                    + " left join country c on u.birthplace_id = c.id;");
            ResultSet resultSet = stmt.getResultSet();
            while (resultSet.next()) {
                User u = getUser(resultSet);
                result.add(u);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    public User getUser(ResultSet resultSet) throws Exception {

        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String surname = resultSet.getString("surname");
        String email = resultSet.getString("email");
        String profileDesc = resultSet.getString("profile_description");
        int nationalityId = resultSet.getInt("nationality_id");
        int birthplaceId = resultSet.getInt("birthplace_id");
        String phone = resultSet.getString("phone");
        String address = resultSet.getString("address");
        Date birthDate = resultSet.getDate("birthdate");
        String nationalityStr = resultSet.getString("nationality");
        String birthPlaceStr = resultSet.getString("birthplace");

        Country nationality = new Country(nationalityId, null, nationalityStr);
        Country birthPlace = new Country(birthplaceId, birthPlaceStr, null);

        return new User(id, name, surname, email, profileDesc, phone, address, birthPlace, birthDate, nationality);
    }

    @Override
    public User getById(int userId) {
        User result = null;
        try ( Connection c = connect()) {
            Statement stmt = c.createStatement();
            stmt.execute("SELECT "
                    + "u.*,"
                    + "n.nationality,"
                    + "c.name birthplace "
                    + "FROM user u"
                    + " left join country n on u.nationality_id = n.id"
                    + " left join country c on u.birthplace_id =  c.id where u.id = " + userId);
            ResultSet resultSet = stmt.getResultSet();
            while (resultSet.next()) {
                result = getUser(resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void addUser(User u) {
        try ( Connection c = connect()) {
            PreparedStatement preparedStmt = c.prepareStatement("insert into user(name,surname,email,"
                    + "phone,address,profile_description) "
                    + "values(?,?,?,?,?,?)");
            preparedStmt.setString(1, u.getName());
            preparedStmt.setString(2, u.getSurname());
            preparedStmt.setString(3, u.getEmail());
            preparedStmt.setString(4, u.getPhone());
            preparedStmt.setString(5, u.getAddress());
            preparedStmt.setString(6, u.getProfileDesc());
            preparedStmt.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void updateUser(User u) {
        try ( Connection c = connect()) {
            PreparedStatement preparedStmt = c.prepareStatement("update user set name = ?,"
                    + "surname = ?,email = ?,phone = ?,address = ?, profile_description = ?, birthdate = ? where id = ?");
            preparedStmt.setString(1, u.getName());
            preparedStmt.setString(2, u.getSurname());
            preparedStmt.setString(3, u.getEmail());
            preparedStmt.setString(4, u.getPhone());
            preparedStmt.setString(5, u.getAddress());
            preparedStmt.setString(6, u.getProfileDesc());
            preparedStmt.setDate(7, u.getBirthDate());
            preparedStmt.setInt(8, u.getId());
            preparedStmt.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void removeUser(int id) {
        try ( Connection c = connect()) {
            Statement stmt = c.createStatement();
            stmt.execute("delete from user where id = " + id);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
