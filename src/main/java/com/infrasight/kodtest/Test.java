package com.infrasight.kodtest;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.net.URISyntaxException;
import java.util.GregorianCalendar;

public class Test {
    public static void main(String[] args) throws URISyntaxException, JsonProcessingException {
        String ex = "HEJDÄR&NUÄRDETKLART";
        System.out.println(ex.replace(ex.substring(0, 1), "Test"));
    }
}
