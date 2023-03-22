package com.jobsity.birthdayreminder;

import com.jobsity.birthdayreminder.beans.Friend;
import com.jobsity.birthdayreminder.service.BirthdayServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class BirthdayReminder {

    private static final Logger logger = LogManager.getLogger(BirthdayReminder.class);


    public static void main(String[] args) throws Exception {
        System.setProperty("log4j.configurationFile", "./src/main/resources/log4j2.xml");
        logger.info("Starting BirthdayReminder....");

        BirthdayServiceImpl birthdayService = new BirthdayServiceImpl();

        LocalDate today = LocalDate.now();

        Optional<List<Friend>> birthdayListOp = birthdayService.getBirthdaysForSendGreetings(today);

        if(birthdayListOp.isEmpty() || (birthdayListOp.get()).size()==0){
            logger.info("There's no birthday on this day");
            System.exit(0);
        }

        Optional<List<Friend>> friendForReminderListOp = birthdayService.getFriendsToSendReminders();

        if(friendForReminderListOp.isEmpty() || (friendForReminderListOp.get()).size()==0){
            logger.info("There's no friends to send a Reminder");
        }

        birthdayService.sendGreetingsAndReminders(birthdayListOp.get(),friendForReminderListOp.get());
    }

}

