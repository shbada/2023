package com.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class Customer {
    private long id;
    private String firstName;
    private String lastName;

    public Customer(long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }
}

@SpringBootApplication
public class JdbcApplication {
    private static final Logger log = LoggerFactory.getLogger(JdbcApplication.class);

    @Bean
    ApplicationRunner run(JdbcTemplate jdbcTemplate) { // springboot 가 기본적으로 빈으로 등록(jdbc lib가 있고, db가 연결되어있으면)
        return args -> {
            System.out.println("Hello World" + jdbcTemplate);
            jdbcTemplate.execute("DROP TABLE customers IF EXISTS");
            jdbcTemplate.execute("CREATE TABLE customers(" +
                    "id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))");

            // Split up the array of whole names into an array of first/last names
            List<Object[]> splitUpNames = Arrays.asList("John Woo", "Jeff Dean", "Josh Bloch", "Josh Long").stream()
                    .map(name -> name.split(" "))
                    .collect(Collectors.toList());

            // Use a Java 8 stream to print out each tuple of the list
            splitUpNames.forEach(name -> log.info(String.format("Inserting customer record for %s %s", name[0], name[1])));

            // Uses JdbcTemplate's batchUpdate operation to bulk load data
            jdbcTemplate.batchUpdate("INSERT INTO customers(first_name, last_name) VALUES (?,?)", splitUpNames);

            log.info("Querying for customer records where first_name = 'Josh':");
            jdbcTemplate.query(
                    "SELECT id, first_name, last_name FROM customers WHERE first_name = ?", new Object[] { "Josh" },
                    (rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"))
            ).forEach(customer -> log.info(customer.toString()));
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(JdbcApplication.class, args);
    }

}
