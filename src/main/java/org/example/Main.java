package org.example;


import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        System.out.println(new PerennialDate(LocalDate.parse(args[0])).toHumanReadable());
    }
}