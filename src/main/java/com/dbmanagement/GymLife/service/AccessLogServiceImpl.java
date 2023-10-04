package com.dbmanagement.GymLife.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbmanagement.GymLife.dao.AppDAO;
import com.dbmanagement.GymLife.dao.MemberDAO;
import com.dbmanagement.GymLife.entity.AccessLog;
import com.dbmanagement.GymLife.entity.Member;

@Service
public class AccessLogServiceImpl implements AccessLogService {

    public MemberDAO memberDAO;
    public AppDAO appDAO;

    @Autowired
    public AccessLogServiceImpl(MemberDAO memberDAO, AppDAO appDAO) {
        this.memberDAO = memberDAO;
        this.appDAO = appDAO;
    }

    @Override
    public void setAccessLogAugust() {

        for (int dateInt = 1; dateInt <= 30; dateInt++) {
            String year = "2023";
            String month = "09";
            String date = String.valueOf(dateInt);
            String dateString = year + "-" + month + "-" + date;

            // get all members that are registered in June
            List<Member> members = memberDAO.retrieveAllGymmers();
            for (int i = 0; i < 82; i++) {
                Member member = members.get(i);

                int hourIn = getRandomNumberUsingNextInt(6, 25);
                int minuteIn = getRandomNumberUsingNextInt(0, 60);

                String hourInStr = convertIntToString(hourIn);
                String minuteInStr = convertIntToString(minuteIn);

                int hourOut = hourIn + getRandomNumberUsingNextInt(1, 4);
                int minuteOut = getRandomNumberUsingNextInt(0, 60);
                if (hourOut > 24) {
                    hourOut = 24;
                }

                String hourOutStr = convertIntToString(hourOut);
                String minuteOutStr = convertIntToString(minuteOut);

                String timeIn = hourInStr + ":" + minuteInStr;
                String timeOut = hourOutStr + ":" + minuteOutStr;

                AccessLog newAccessLog = new AccessLog(dateString, member, timeIn, timeOut);
                appDAO.save(newAccessLog);

            }
        }
    }

    public int getRandomNumberUsingNextInt(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    public String convertIntToString(int number) {
        String result;
        if (number >= 0 && number <= 9) {
            result = "0" + number; // Add a leading '0' if it's a single digit
        } else {
            result = Integer.toString(number); // Convert to string directly
        }
        return result;
    }

    @Override
    public List<Integer> getTotalAccessByTime() {
        List<Integer> result = new ArrayList<>();

        for (int i = 6; i <= 24; i++) {
            result.add(0);
        }

        List<AccessLog> allAccess = appDAO.retrieve1000AccessLog();
        for (AccessLog access : allAccess) {
            String timeIn = access.getTimeAccessIn();
            String time = timeIn.substring(0, 2);
            int timeInt = Integer.parseInt(time);

            int index = timeInt - 6;
            result.set(index, result.get(index) + 1);
        }

        return result;
    }

}
