package me.whiteship.chapter01.item03.functionalinterface;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UsageOfFunctions {

    public static void main(String[] args) {
        List<LocalDate> dates = new ArrayList<>();
        dates.add(LocalDate.of(1982, 7, 15));
        dates.add(LocalDate.of(2011, 3, 2));
        dates.add(LocalDate.of(2013, 1, 28));

        List<Integer> before2000 = dates.stream()
                // 데이터를 걸러내므로 Predicate
                .filter(d -> d.isBefore(LocalDate.of(2000, 1, 1)))
                // Function
                .map(LocalDate::getYear)
                // Collector
                .collect(Collectors.toList());
    }
}
