/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.text.SimpleDateFormat;
import java.util.Date;
import testex.JokeException;

public interface IDateFormatter {

    public String getFormattedDate (String timezone, SimpleDateFormat formatter, Date time) throws JokeException;
}
