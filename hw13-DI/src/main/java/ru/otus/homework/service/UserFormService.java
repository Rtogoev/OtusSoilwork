package ru.otus.homework.service;

import org.springframework.stereotype.Service;
import ru.otus.homework.model.AddressDataSet;
import ru.otus.homework.model.PhoneDataSet;
import ru.otus.homework.model.User;
import ru.otus.homework.model.UserForm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserFormService {

    private final UserService userService;

    public UserFormService(UserService userService) {
        this.userService = userService;
    }

    public List<UserForm> getAll() {
        List<UserForm> userForms = new ArrayList<>();
        for (User user : userService.getAll()) {
            StringBuilder stringBuffer = new StringBuilder();
            for (PhoneDataSet phoneDataSet : user.getPhoneDataSet()) {
                stringBuffer.append(phoneDataSet.getNumber());
            }
            userForms.add(
                    new UserForm(
                            user.getId().toString(),
                            user.getName(),
                            String.valueOf(user.getAge()),
                            user.getAddressDataSet().getStreet(),
                            stringBuffer.toString()
                    )
            );
        }
        return userForms;
    }

    public void saveAsUser(UserForm userForm) {
        userService.create(
                new User(
                        userForm.getName(),
                        Integer.parseInt(userForm.getAge()),
                        new AddressDataSet(userForm.getAddress()),
                        Collections.singleton(new PhoneDataSet(userForm.getPhone()))
                )
        );
    }
}
