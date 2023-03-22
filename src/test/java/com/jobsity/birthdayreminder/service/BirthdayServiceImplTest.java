package com.jobsity.birthdayreminder.service;


import com.jobsity.birthdayreminder.beans.Friend;
import com.jobsity.birthdayreminder.repository.BirthdayRepository;
import org.apache.logging.log4j.core.util.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class BirthdayServiceImplTest {

    BirthdayServiceImpl birthdayService = Mockito.mock(BirthdayServiceImpl.class);
    BirthdayRepository birthdayRepository = Mockito.mock(BirthdayRepository.class);
    List<Friend> normalListFriend = null;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");


    void setNormalListFriend(){

        Friend f1 = new Friend(1L,"gonzalez","Derlis",
                LocalDate.parse("1990/02/28",formatter),"d.gonza@example.com","+595999999");
        Friend f2 = new Friend(1L,"gimenez","Igor",
                LocalDate.parse("1990/02/29",formatter),"d.gonza@example.com","+595999999");
        Friend f3 = new Friend(1L,"Alfonzo","Arturo",
                LocalDate.parse("1990/02/28",formatter),"d.gonza@example.com","+595999999");
        Friend f4 = new Friend(1L,"Cino","Gustavo",
                LocalDate.parse("1990/03/21",formatter),"d.gonza@example.com","+595999999");
        Friend f5 = new Friend(1L,"Guanes","Luis",
                LocalDate.now(),"d.gonza@example.com","+595999999");

        normalListFriend = new ArrayList<>();
        normalListFriend.add(f1);
        normalListFriend.add(f2);
        normalListFriend.add(f3);
        normalListFriend.add(f4);
        normalListFriend.add(f5);

    }

    void setMixListFriendFeb28Feb29(){
        Friend f1 = new Friend(1L,"gonzalez","Derlis",
                LocalDate.parse("1990/02/28",formatter),"d.gonza@example.com","+595999999");
        Friend f2 = new Friend(1L,"gimenez","Igor",
                LocalDate.parse("1990/02/29",formatter),"d.gonza@example.com","+595999999");
        Friend f3 = new Friend(1L,"Alfonzo","Arturo",
                LocalDate.parse("1990/02/28",formatter),"d.gonza@example.com","+595999999");

        normalListFriend = new ArrayList<>();
        normalListFriend.add(f1);
        normalListFriend.add(f2);
        normalListFriend.add(f3);

    }


    @Test
    void getFriendsToSendReminders() throws Exception {
        setNormalListFriend();
        when(birthdayRepository.getAllFriends()).thenReturn(normalListFriend);
        Assert.isNonEmpty(birthdayService.getFriendsToSendReminders());
    }

    @Test
    void noFriendsToSendReminder() throws Exception {
        when(birthdayRepository.getAllFriends()).thenReturn(null);
        Assert.isEmpty(birthdayService.getFriendsToSendReminders());
    }

    @Test
    void getBirthdaysForSpecialConditionFebraury(){
        LocalDate februaryDate = LocalDate.parse("2023/02/28",formatter);
        birthdayService = new BirthdayServiceImpl();
        Optional<List<Friend>> listOp =birthdayService.getBirthdaysForSendGreetings(februaryDate);
        boolean onlyRequiredDate = true;
        if(listOp.isPresent()){
            for (Friend f:listOp.get()) {
                if (f.getDateOfBirth().getMonthValue() != 2){
                     if(f.getDateOfBirth().getDayOfMonth() != 28 ||
                            f.getDateOfBirth().getDayOfMonth() != 29) {
                         onlyRequiredDate = false;
                         break;
                        }
                    onlyRequiredDate = false;
                    break;
                    }
            }
        }

        assertTrue(onlyRequiredDate,"All the birthdays are between the Feb 28 and Feb 29");
    }

}