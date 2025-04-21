package com.naivez.repository;

import com.naivez.dto.user.UserFilter;
import com.naivez.entity.User;

import java.util.List;

public interface FilterUserRepository {

    List<User> findAllByFilter(UserFilter filter);
}
