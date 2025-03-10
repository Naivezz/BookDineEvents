package com.naivez.integration.dao;

import com.naivez.dao.BlackListRepository;
import com.naivez.entity.BlackList;
import com.naivez.integration.entity.IntegrationTestBase;
import com.naivez.util.DataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class BlackListRepositoryIT extends IntegrationTestBase {

    private BlackListRepository blackListRepository;

    @BeforeEach
    void setUp() {
        blackListRepository = new BlackListRepository(BlackList.class, session);
    }

    @Test
    void createBlackList() {
        var user = DataBuilder.createUser();
        var restaurant = DataBuilder.createRestaurant();
        session.persist(user);
        session.persist(restaurant);
        session.flush();
        var blackList = DataBuilder.createBlackList();
        blackList.setUser(user);
        blackList.setRestaurant(restaurant);

        blackListRepository.save(blackList);
        session.clear();

        assertThat(session.get(BlackList.class, blackList.getId())).isNotNull();
    }

    @Test
    void updateBlackList() {
        var user = DataBuilder.createUser();
        var restaurant = DataBuilder.createRestaurant();
        session.persist(user);
        session.persist(restaurant);
        session.flush();
        var blackList = DataBuilder.createBlackList();
        blackList.setUser(user);
        blackList.setRestaurant(restaurant);
        blackListRepository.save(blackList);
        session.clear();

        blackList.setReason("Bad activity");
        blackListRepository.update(blackList);
        session.flush();
        session.clear();
        BlackList updatedBlackList = session.get(BlackList.class, blackList.getId());

        assertThat(updatedBlackList).isEqualTo(blackList);
    }

    @Test
    void findBlackListById() {
        var user = DataBuilder.createUser();
        var restaurant = DataBuilder.createRestaurant();
        session.persist(user);
        session.persist(restaurant);
        session.flush();
        var blackList = DataBuilder.createBlackList();
        blackList.setUser(user);
        blackList.setRestaurant(restaurant);
        blackListRepository.save(blackList);
        session.clear();

        var expectedBlackList = blackListRepository.findById(blackList.getId());

        assertThat(expectedBlackList).isEqualTo(Optional.of(blackList));
    }

    @Test
    void findAllBlackLists() {
        var user1 = DataBuilder.createUser();
        var restaurant1 = DataBuilder.createRestaurant();
        session.persist(user1);
        session.persist(restaurant1);
        session.flush();
        var user2 = DataBuilder.createUser();
        var restaurant2 = DataBuilder.createRestaurant();
        session.persist(user2);
        session.persist(restaurant2);
        session.flush();
        var blackList1 = DataBuilder.createBlackList();
        blackList1.setUser(user1);
        blackList1.setRestaurant(restaurant1);
        blackListRepository.save(blackList1);
        session.flush();
        var blackList2 = DataBuilder.createBlackList();
        blackList2.setUser(user2);
        blackList2.setRestaurant(restaurant2);
        blackListRepository.save(blackList2);
        session.flush();
        session.clear();

        List<BlackList> blackLists = blackListRepository.findAll();

        assertThat(blackLists)
                .isNotEmpty()
                .containsExactlyInAnyOrder(blackList1, blackList2);
    }

    @Test
    void deleteBlackList() {
        var user = DataBuilder.createUser();
        var restaurant = DataBuilder.createRestaurant();
        session.persist(user);
        session.persist(restaurant);
        session.flush();
        var blackList = DataBuilder.createBlackList();
        blackList.setUser(user);
        blackList.setRestaurant(restaurant);
        blackListRepository.save(blackList);
        session.clear();

        blackListRepository.delete(blackList.getId());

        assertThat(session.get(BlackList.class, blackList.getId())).isNull();
    }
}
