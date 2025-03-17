package com.naivez.repository;

import com.naivez.dto.UserDto;
import com.naivez.entity.User;
import com.naivez.entity.User_;
import com.naivez.repository.predicate.QPredicate;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.naivez.entity.QUser.user;

@Repository
public class UserRepository extends RepositoryBase<Long, User> {

    public UserRepository(EntityManager entityManager) {
        super(User.class, entityManager);
    }

    public List<User> findByAnyField(Session session, UserDto userDto) {
        var predicate = QPredicate.builder()
                .add(userDto.getFirstName(), user.firstName::eq)
                .add(userDto.getLastName(), user.lastName::eq)
                .add(userDto.getEmail(), user.email::eq)
                .add(userDto.getPassword(), user.password::eq)
                .add(userDto.getPhoneNumber(), user.phoneNumber::eq)
                .add(userDto.isBlacklisted(),user.isBlacklisted::eq)
                .buildOr();

        return new JPAQuery<User>(session)
                .select(user)
                .from(user)
                .where(predicate)
                .setHint("javax.persistence.fetchgraph",session.getEntityGraph("withReservations"))
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
                .setHint("javax.persistence.fetchgraph",session.getEntityGraph("withReservations"))
                .list();
    }

}
