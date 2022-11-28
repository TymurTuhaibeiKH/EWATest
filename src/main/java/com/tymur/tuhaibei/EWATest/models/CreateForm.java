package com.tymur.tuhaibei.EWATest.models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author tymur.tuhaibei
 */
@Builder
@Getter
@Setter
public class CreateForm {

    private Long clientId;

    private Long contractId;

    private String firstName;

    private String middleName;

    private String lastName;

    private String dayOfBirthday;

    private String phoneNumber;

    private String contractNumber;

    private String contractStart;

    private String contractEnd;

    private Long sumInsured;

    private Long contractPaymentAmount;

}
