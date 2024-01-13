/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package findjob;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author BusraGural
 */
public class Application {
    
    private int id, userId, advId;
    private Date applicationDate;

    public Application(int id, int userId, int advId, Date applicationDate) {
        this.id = id;
        this.userId = userId;
        this.advId = advId;
        this.applicationDate = applicationDate;
    }
    
    public Application(){
        
    }
    
    public void applyAdvertisement(Connection conn, Advertisement adv, int userId) throws SQLException {
        System.out.println("adv.getId" + adv.getId());
        System.out.println("userId" + userId);
        String query = "INSERT INTO applications (user_id, adv_id, application_date) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, adv.getId()); // Assuming Advertisement has an getId method
            preparedStatement.setDate(3, new java.sql.Date(System.currentTimeMillis())); 

            preparedStatement.executeUpdate();

            System.out.println("Application successful.");
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your needs
            throw e;
        }
    }
    
    public ArrayList<Application> getAppAdv(Connection conn, int user_id){
            ArrayList<Application> applicationList = new ArrayList<>();
            
            String sql = "SELECT * FROM get_applied_adv(?)";
            
            try (CallableStatement callableStatement = conn.prepareCall(sql)) {
                callableStatement.setInt(1, user_id);

                ResultSet resultSet = callableStatement.executeQuery();
                while (resultSet.next()) {
                    Application appl = new Application();
                    
                    appl.setId(resultSet.getInt("app_id"));
                    appl.setAdvId(resultSet.getInt("adv_id"));
                    appl.setUserId(resultSet.getInt("u_id"));
                    appl.setApplicationDate(resultSet.getDate("app_date"));

                    applicationList.add(appl);
                }
            } catch (SQLException ex) {
                Logger.getLogger(AccountPageUI.class.getName()).log(Level.SEVERE, null, ex);
            }
            return applicationList;
    }
    

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the advId
     */
    public int getAdvId() {
        return advId;
    }

    /**
     * @param advId the advId to set
     */
    public void setAdvId(int advId) {
        this.advId = advId;
    }

    /**
     * @return the applicationDate
     */
    public Date getApplicationDate() {
        return applicationDate;
    }

    /**
     * @param applicationDate the applicationDate to set
     */
    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    void removeApp(Connection conn, int appId) {
        String sql = "DELETE from applications where id=?";
        
        try(PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1,appId);
            
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            e.printStackTrace();;
            throw new RuntimeException("Silinirken hata meydana geldi!");
        }
    }
    
    
}
