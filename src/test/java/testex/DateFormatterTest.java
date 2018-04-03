/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testex;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author micha
 */
public class DateFormatterTest {

    /**
     *
     * @throws JokeException
     */
    @Test
    @DisplayName("Test to see if format is correct")
    public void getFormattedDateTest() throws JokeException {
        Date time = new Date();
        String dateTimeFormat = "dd MMM yyyy hh:mm aa";
        SimpleDateFormat simpleFormat = new SimpleDateFormat(dateTimeFormat);
        simpleFormat.setTimeZone(TimeZone.getTimeZone("Europe/Kiev"));
        assertEquals(simpleFormat.format(time), new DateFormatter().getFormattedDate("Europe/Kiev", new SimpleDateFormat(dateTimeFormat), time));
    }

    @Test
    @DisplayName("Test to see if correct exception is thrown")
    public void getFormattedDateFailTest() {
        assertThrows(JokeException.class, () -> {
            Date time = new Date();
            String dateTimeFormat = "dd MMM yyyy hh:mm aa";
            new DateFormatter().getFormattedDate("illegal input", new SimpleDateFormat(dateTimeFormat), time);
        });
    }
}
