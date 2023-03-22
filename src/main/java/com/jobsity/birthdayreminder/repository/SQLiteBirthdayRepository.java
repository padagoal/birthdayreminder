package com.jobsity.birthdayreminder.repository;

import com.jobsity.birthdayreminder.beans.Friend;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SQLiteBirthdayRepository implements BirthdayRepository{

    private static final Logger logger = LogManager.getLogger(SQLiteBirthdayRepository.class);
    private String url = "jdbc:sqlite:./friends.db";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    /**
     * This method check if is it a Database to connect
     */
    public void initializeDatabase() throws SQLException{

        try (Connection con = DriverManager.getConnection(url)) {
            Statement statement = con.createStatement();
            statement.execute("SELECT 1 FROM friends");
            logger.info("Success connecting with db {}",url);
        }

    }

    /**
     * This method return the list of birthdays matching the param of date
     * @param birthdayDateSearch the date for search on database
     * @return the list of birthdays
     */
    @Override
    public List<Friend> getBirthdaysByDate(LocalDate birthdayDateSearch)  {
        logger.info("Checking for birthdays on database");
        //CHECK IF IT IS LEAP YEAR OR FEB 28
        String whereStatement = whereStatementCreator(birthdayDateSearch);
        try {
            Connection con = DriverManager.getConnection(url);
            Statement statement = con.createStatement();
            String query = "SELECT id,last_name,first_name, date_of_birth, email, phone FROM friends "
                  + whereStatement;
            logger.info("QUERY: "+query);
            try (PreparedStatement preparedStatement = con.prepareStatement(query)) {

                logger.info("Todays Birthdays are:");
                return getData(preparedStatement);
            }
        }catch (Exception e){
            logger.error("Error with getting birthdays",e);
        }
        return null;
    }

    /**
     *
     * This method search on the database for all friends to send the reminder of birthdays
     *
     * @return a list of all friends in the database
     * @throws SQLException
     */
    @Override
    public List<Friend> getAllFriends(){
        try{
            Connection con = DriverManager.getConnection(url);
            Statement statement = con.createStatement();
            String query = "SELECT id,last_name,first_name, date_of_birth, email, phone FROM friends ";
            try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
                logger.info("Getting all Friends from the list to send a Reminder:");
                return getData(preparedStatement);
            }
        }catch (SQLException e){
            logger.error("Error with getAllFriends()",e);
        }
        return null;
    }

    /**
     * This method handle the creation for the proper WHERE used on the search for birthdays,
     * where it handles the special condition for Feb 28 or Feb 29 for the leap years.
     * @param dateSearch the date is use for writing the proper Where Clause
     * @return String WHERE condition
     */
    private String whereStatementCreator(LocalDate dateSearch){

        String responseWhere = "WHERE date_of_birth like '%/%"+dateSearch.getMonthValue()
                +"/%"+dateSearch.getDayOfMonth()+"'";
        if(!dateSearch.isLeapYear()){
            if(dateSearch.getMonthValue()==2 && dateSearch.getDayOfMonth()==28){
                responseWhere = "WHERE date_of_birth like '%/02/28' OR date_of_birth like '%/02/29'";
            }
        }

        return responseWhere;
    }


    /**
     * This method handle the result of the database, using ResultSet and mapping in the proper fields
     * @param preparedStatement Receive the statement with query on it
     * @return the List of Friends that meet the SELECT condition
     */
    private List<Friend> getData(PreparedStatement preparedStatement){
        List<Friend> friendList = new ArrayList<>();

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String lastName = resultSet.getString("last_name");
                String firstName = resultSet.getString("first_name");
                LocalDate birthdate = LocalDate.parse(resultSet.getString("date_of_birth"),formatter);
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                friendList.add(new Friend(id,lastName,firstName, birthdate, email,phone));
            }
        } catch (SQLException e) {
            logger.error("Error with method getData()",e);
        }
        for (Friend f:friendList
             ) {
            logger.info(f.getId()+ " "+f.getFirstName()+ " "+f.getDateOfBirth());
        }
        return friendList;
    }

}
