package ru.job4j.cinema.store;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.Main;
import ru.job4j.cinema.model.Session;

import java.util.List;

class SessionsDBStoreTest {

    @Test
    public void whenAddSession() {
        SessionsDBStore store = new SessionsDBStore(new Main().loadPool());
        Session session = new Session(0, "name", new byte[1]);
        store.add(session);
        Session inDB = store.findById(session.getId());
        Assertions.assertThat(inDB.getName()).isEqualTo(session.getName());
    }

    @Test
    public void whenFindAll() {
        SessionsDBStore store = new SessionsDBStore(new Main().loadPool());
        Session session = new Session(0, "name", new byte[1]);
        Session session2 = new Session(0, "name2", new byte[1]);
        List<Session> resultBeforeAdd = store.findAll();
        store.add(session);
        store.add(session2);
        List<Session> result = store.findAll();
        Assertions.assertThat(resultBeforeAdd.size() + 2).isEqualTo(result.size());
        Assertions.assertThat(result).contains(session, session2);
    }
}