package com.jobsity.birthdayreminder;

import com.jobsity.birthdayreminder.beans.Friend;

public interface ContactChannel {

    void sendBirthdayMessage(Friend friend);

    void sendReminderMessage(Friend friend, Friend birthdayFriend);
}
