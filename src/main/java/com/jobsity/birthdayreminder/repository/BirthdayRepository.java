package com.jobsity.birthdayreminder.repository;

import com.jobsity.birthdayreminder.beans.Friend;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public interface BirthdayRepository {

    void initializeDatabase() throws SQLException;

    List<Friend> getBirthdaysByDate(LocalDate birthdayDateSearch) ;

    List<Friend> getAllFriends() throws Exception;
}