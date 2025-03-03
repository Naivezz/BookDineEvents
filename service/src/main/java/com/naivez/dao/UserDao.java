package com.naivez.dao;

import com.naivez.dto.UserDto;
import com.naivez.entity.User;
import com.naivez.entity.User_;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import java.util.List;

import static com.naivez.entity.QUser.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao {

    private static final UserDao INSTANCE = new UserDao();

    public static UserDao getInstance() {
        return INSTANCE;
    }

    public List<User> findAllByFirstname(Session session, UserDto userDto){
        return new JPAQuery<User>(session)
                .select(user)
                .from(user)
                .where(user.firstName.eq(userDto.getFirstName()))
                .fetch();
    }

    public List<User> findByFirstnameAndLastName(Session session, UserDto userDto){
        var predicate = QPredicate.builder()
                .add(userDto.getFirstName(), user.firstName::eq)
                .add(userDto.getLastName(), user.lastName::eq)
                .buildAnd();

        return new JPAQuery<User>(session)
                .select(user)
                .from(user)
                .where(predicate)
                .setHint("javax.persistence.fetchgraph",session.getEntityGraph("withReservationAndBlackList"))
                .fetch();
    }

    public List<User> findByAnyField(Session session, UserDto userDto) {
        var predicate = QPredicate.builder()
                .add(userDto.getFirstName(), user.firstName::eq)
                .add(userDto.getLastName(), user.lastName::eq)
                .add(userDto.getEmail(), user.email::eq)
                .add(userDto.getPassword(), user.password::eq)
                .add(userDto.getPhoneNumber(), user.phoneNumber::eq)
                .buildOr();

        return new JPAQuery<User>(session)
                .select(user)
                .from(user)
                .where(predicate)
                .setHint("javax.persistence.fetchgraph",session.getEntityGraph("withReservationAndBlackList"))
                .fetch();
    }

    public List<User> findByFirstNameAndEmail(Session session, UserDto userDto) {
        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(User.class);
        var user = criteria.from(User.class);

        criteria.select(user).where(
                cb.equal(user.get(User_.firstName), userDto.getFirstName()),
                cb.equal(user.get(User_.email), userDto.getEmail())
        );

        return session.createQuery(criteria)
                .setHint("javax.persistence.fetchgraph",session.getEntityGraph("withReservationAndBlackList"))
                .list();
    }

}
