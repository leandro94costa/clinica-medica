package br.com.clinica.util;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class FormatterUtil {

    public LocalDate formatDate(String data) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date;

        try {

            date = LocalDate.parse(data, formatter);

        } catch (DateTimeException e) {

            throw new DateTimeException(e.getMessage());
        }

        return date;
    }

    public LocalTime formatTime(String horario) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime time;

        try {

            time = LocalTime.parse(horario, formatter);

        } catch (DateTimeException e) {

            throw new DateTimeException(e.getMessage());
        }

        return time;
    }
}
