package com.cyptical.librarymanagementsystem.request;


import com.cyptical.librarymanagementsystem.models.AccountStatus;
import com.cyptical.librarymanagementsystem.models.Student;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentCreateRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String contact;
    private String address;
    private String email;

    public Student to(){
        return Student.builder()
                .address(address)
                .contact(contact)
                .email(email)
                .name(name)
                .accountStatus(AccountStatus.ACTIVE)
                .build();
    }

}
