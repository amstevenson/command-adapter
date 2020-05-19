package com.twitchbot.commandadapter.repository;

import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CommandRepo {

    private final Jdbi jdbi;

    @Autowired
    public CommandRepo(Jdbi jdbi) {
        this.jdbi = jdbi;
    }
}
