package com.jobsity.birthdayreminder.repository;

import com.jobsity.birthdayreminder.beans.Friend;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CSVBirthdayRepository implements BirthdayRepository{

    private static final Logger logger = LogManager.getLogger(CSVBirthdayRepository.class);
    private final String csvFilePath = "./friends.csv";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    List<Friend> friendList = null;
    @Override
    public List<Friend> getBirthdaysByDate(LocalDate birthdayDateSearch) {

        List<Friend> friendList = new ArrayList<>();
        CSVParser parser = new CSVParserBuilder().withSeparator(';').withIgnoreQuotations(true).build();
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));

             CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(parser).withSkipLines(1).build()) {
            String[] line;

            while ((line = csvReader.readNext()) != null) {
                String id = line[0];
                String lastName= line[1];
                String firstName = line[2];
                LocalDate birthdate = LocalDate.parse(line[3],formatter);
                String email = line[4];
                String phone = line[5];

                //Handle the leap year
                if(birthdayDateSearch !=null) {
                    if (birthdate.getMonthValue() == 2 && birthdate.getDayOfMonth() == 29) {
                        if (birthdayDateSearch.getMonthValue() == 2 && birthdayDateSearch.getDayOfMonth() == 28 && !birthdayDateSearch.isLeapYear()) {
                            continue;
                        }
                        birthdate = birthdate.withDayOfMonth(28);
                    }
                    if(birthdate.getMonthValue()== birthdayDateSearch.getMonthValue() && birthdate.getDayOfMonth() == birthdayDateSearch.getDayOfMonth()){
                        friendList.add(new Friend(Long.parseLong(id),lastName,firstName,birthdate,email,phone));
                    }
                }else{
                    friendList.add(new Friend(Long.parseLong(id),lastName,firstName,birthdate,email,phone));
                }



            }
        }catch (Exception e){
            logger.error("Error getting friend List from CSV",e);
        }
        return friendList;
    }

    @Override
    public List<Friend> getAllFriends() throws Exception {
        if(friendList==null){
            return getBirthdaysByDate(null);
        }else{
            return friendList;
        }

    }
    @Override
    public void initializeDatabase() throws SQLException {
       //do nothing
    }
}
