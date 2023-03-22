package com.jobsity.birthdayreminder;

import com.jobsity.birthdayreminder.beans.Friend;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SMSContactChannel implements ContactChannel{

    private static final Logger logger = LogManager.getLogger(SMSContactChannel.class);

    @Override
    public void sendBirthdayMessage(Friend friend) {
        logger.info ("SENDING BIRTHDAY SMS MESSAGE to: "+friend.getFirstName());
    }

    @Override
    public void sendReminderMessage(Friend friend, Friend birthdayFriend) {
        logger.info ("SENDING BIRTHDAY SMS REMINDER MESSAGE to: "+friend.getFirstName()+
                " it's "+birthdayFriend.getFirstName()+" birthdays today");
    }

}
