package ru.otus.homework.services.database;

import java.util.List;

public class ParamService {
    public String getValuesString(List<Param> params) {
        StringBuilder valuesString = new StringBuilder(params.get(0).getValue());
        for (int i = 1; i < params.size(); i++) {
            valuesString.append(", ").append(params.get(i).getValue());
        }
        return valuesString.toString();
    }

    public String getNamesString(List<Param> params) {
        StringBuilder namesString = new StringBuilder(params.get(0).getName());
        for (int i = 1; i < params.size(); i++) {
            namesString.append(", ").append(params.get(i).getName());
        }
        return namesString.toString();
    }
}
