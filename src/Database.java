/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
/**
 *
 * @author lozan
 */
public class Database {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cychat-data", "root", "");//enter username and password of local mySQL workbench

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM `cychat-data`.`login`");

            while(resultSet.next()){
                System.out.println(resultSet.getString("username"));
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
