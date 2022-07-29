/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mspace;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author lenovo
 */
@ManagedBean
@RequestScoped
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String phonenumber;
    private String name;
    private String location;
    private Map<String, Object> session = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

    public Model() {
    }

    public Model(Long id, String email, String phonenumber, String name, String location) {
        this.id = id;
        this.email = email;
        this.phonenumber = phonenumber;
        this.name = name;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;

    }

    DBConnection db = new DBConnection();

    Connection conn = db.get_connection();

    public List<Model> getListAll() throws SQLException {
        ArrayList listing = new ArrayList();

        try {
            java.sql.Statement stmt = conn.createStatement();

            String sql = "select * from records";

            stmt.execute(sql);
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Model model = new Model();
                model.setId(rs.getLong("id"));
                model.setEmail(rs.getString("email"));
                model.setPhonenumber(rs.getString("phonenumber"));
                model.setName(rs.getString("name"));
                model.setLocation(rs.getString("location"));
                listing.add(model);
            }

        } catch (SQLException e) {
            System.out.println(e);
        }

        return listing;

    }

    public String insertInto() {
        String query = "INSERT INTO records(email,phonenumber,name,location) VALUES('" + email + "','" + phonenumber + "','" + name + "','" + location + "')";

        try {

            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);

        } catch (SQLException e) {

            System.out.println(e);

        }
        return "index.xhtml?faces-redirect=true";
    }

    public String next() {
        return "registration.xhtml?faces-redirect=true";
    }

    /*public String update(Long uid) {
        String query = "UPDATE records set phonenumber='" + phonenumber + "',email='" + email + "',name='" + name + "',location='" + location + "' where id='" + uid;

        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        return "/index.xhtml?faces-redirect=true";
    }*/
    public String edit(int id) {

        System.out.println(id);
        try {

            java.sql.Statement stmt = conn.createStatement();

            String sql = "select * from records where id = " + id;

            stmt.execute(sql);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Model record = new Model();
                record.setId(rs.getLong("id"));
                record.setEmail(rs.getString("email"));
                record.setPhonenumber(rs.getString("phonenumber"));
                record.setName(rs.getString("name"));
                record.setLocation(rs.getString("location"));
                session.put("recordedit", record);

                conn.close();
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        return "/edit.xhtml?faces-redirect=true";
    }

    public String update(Model mode) {
//int result = 0;  
        try {
            PreparedStatement stmt = conn.prepareStatement(
                    "update records set email=?,phonenumber=?,name=?,location=? where id=?");
            stmt.setString(1, mode.getEmail());
            stmt.setString(2, mode.getPhonenumber());
            stmt.setString(3, mode.getName());
            stmt.setString(4, mode.getLocation());
            stmt.setLong(5, mode.getId());
            stmt.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println();
        }
        return "/index.xhtml?faces-redirect=true";
    }

    public void deleteRecords(Long uid) {

        String query = "delete from records where id = " + uid;
        try {
            PreparedStatement stmt = conn.prepareStatement("delete from records where id = " + uid);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

}
