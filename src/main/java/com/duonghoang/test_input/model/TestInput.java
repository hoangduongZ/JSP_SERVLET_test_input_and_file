package com.duonghoang.test_input.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestInput {
    private Long id;
    private String textInput;
    private Integer numberInput;
    private LocalDate dateInput;
    private Boolean gender;
    private String hobbies;
    private String country;
    private String filePath;
}
