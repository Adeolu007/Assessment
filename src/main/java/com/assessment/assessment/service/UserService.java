package com.assessment.assessment.service;

import com.cbfacademy.apiassessment.dto.Response;
import com.cbfacademy.apiassessment.dto.UserRequest;
import com.cbfacademy.apiassessment.dto.UserRequestDto;

import java.util.List;

public interface UserService {
    Response registerUser(UserRequest userRequest);
    List<Response> allUsers();
    Response fetchUser(Long id);
    Response balanceEnquiry(String accountNumber);
    Response nameEnquiry (String accountNumber);
    String deleteUser(Long id);
    Response updateUser(long id, UserRequestDto userRequestDTO);
}
