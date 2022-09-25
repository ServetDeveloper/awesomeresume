package dao.impl;

import dao.inter.AbstractDao;
import entity.Country;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import dao.inter.CountryDaoInter;


public class CountryDaoImpl extends AbstractDao implements CountryDaoInter {
    @Override
    public List<Country> getAll() {
        List<Country> result = new ArrayList<>();
        try(Connection c = connect()) {
            PreparedStatement preparedStmt = c.prepareStatement("select * from country;");
            preparedStmt.execute();
            ResultSet rs = preparedStmt.getResultSet();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nationality = rs.getString("nationality");
                String countryName = rs.getString("name");
                Country country = new Country(id, countryName, nationality);
                result.add(country);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
}
