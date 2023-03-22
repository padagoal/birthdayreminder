package com.jobsity.birthdayreminder.service;

import com.jobsity.birthdayreminder.beans.Friend;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BirthdayService {

    Optional<List<Friend>> getBirthdaysForSendGreetings(LocalDate date);

    Optional<List<Friend>> getFriendsToSendReminders();

    void sendGreetingsAndReminders(List<Friend> birthdayList,List<Friend> reminderList);

}
