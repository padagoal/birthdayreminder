package com.jobsity.birthdayreminder.service;

import com.jobsity.birthdayreminder.ContactChannel;
import com.jobsity.birthdayreminder.EmailContactChannel;
import com.jobsity.birthdayreminder.SMSContactChannel;
import com.jobsity.birthdayreminder.beans.Friend;
import com.jobsity.birthdayreminder.repository.BirthdayRepository;
import com.jobsity.birthdayreminder.repository.CSVBirthdayRepository;
import com.jobsity.birthdayreminder.repository.SQLiteBirthdayRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class BirthdayServiceImpl implements BirthdayService {

    private static final Logger logger = LogManager.getLogger(BirthdayServiceImpl.class);

    ContactChannel emailChannel = new EmailContactChannel();
    ContactChannel smsChannel = new SMSContactChannel();

    BirthdayRepository birthdayRepository = null;

    public BirthdayServiceImpl(){
        initialConnection();
    }

    private void initialConnection(){
        try{
            birthdayRepository   = new SQLiteBirthdayRepository();
            birthdayRepository.initializeDatabase();
        }catch (Exception e) {
            logger.error("Problems connecting with SQLitedatabase", e);
            logger.info("Switching to use CSV file");
            try{
                birthdayRepository   = new CSVBirthdayRepository();
            }catch (Exception ex){
                logger.error("Problems connecting with CSVFile",ex);
                System.exit(1);
            }
        }
    }


    /**
     * This method finds all the birthdays given a certain date, first search on database or in a CSV file
     * @param date the date for search
     * @return a optional of the list
     */
    @Override
    public Optional<List<Friend>> getBirthdaysForSendGreetings(LocalDate date) {
        List<Friend> birthdayList = null;

        birthdayList = birthdayRepository.getBirthdaysByDate(date);


        return Optional.of(birthdayList);
    }

    /**
     * This method call the repository to retrieved all the friends for the reminder
     * @return a optional list of friends
     */
    @Override
    public Optional<List<Friend>> getFriendsToSendReminders()  {
        List<Friend> friendToSendReminder = null;
        try{
            friendToSendReminder = birthdayRepository.getAllFriends();
        }catch (Exception e){
            logger.error("Error with obtainings Friends for Reminders",e);
        }

        return Optional.ofNullable(friendToSendReminder);
    }


    /**
     * This method handle the send of greetings and reminders, where compares the two list to know to whom send a
     * greetings and to whom send a reminder
     * @param birthdayList list of friends who's the birthday to send the greetings
     * @param reminderList list of friends to send the reminders
     */
    @Override
    public void sendGreetingsAndReminders(List<Friend> birthdayList,List<Friend> reminderList) {

        for (Friend friendBirthday : birthdayList) {
            if (friendBirthday.getEmail() != null) {
                emailChannel.sendBirthdayMessage(friendBirthday);
            }
            if (friendBirthday.getPhone() != null) {
                smsChannel.sendBirthdayMessage(friendBirthday);
            }

            if(reminderList!= null && !reminderList.isEmpty()){
                reminderList.stream().filter(
                        friendToRemind -> !friendToRemind.getId().equals(friendBirthday.getId())).forEach(
                        friendToRemind -> {
                            if (friendToRemind.getEmail() != null) {
                                emailChannel.sendReminderMessage(friendToRemind, friendBirthday);
                            }
                            if (friendToRemind.getPhone() != null) {
                                smsChannel.sendReminderMessage(friendToRemind, friendBirthday);
                            }
                        });
            }
        }
    }
}
