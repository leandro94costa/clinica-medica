package br.com.clinica.util;

public class ValidationUtils {

    public boolean checkIntegerLenght(Integer num, Integer limit) {

        if (String.valueOf(num).length() <= limit) {

            return true;

        } else {

            return false;
        }
    }

    public boolean checkLongLenght(Long num, Integer limit) {

        if (String.valueOf(num).length() <= limit) {

            return true;

        } else {

            return false;
        }
    }
}
