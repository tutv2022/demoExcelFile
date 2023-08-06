package com.javatechie.report.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;



@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class Employee {

    private int id;
    private String name;
    private String designation;
    private double salary;
    private String doj;
}
