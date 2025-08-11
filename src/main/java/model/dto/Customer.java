package model.dto;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

public class Customer {

    private String id;
    private String title;
    private String name;
    private LocalDate dob;
    private double salary;
    private String province;
    private String city;
    private String streetAddress;
    private String postalCode;
}
