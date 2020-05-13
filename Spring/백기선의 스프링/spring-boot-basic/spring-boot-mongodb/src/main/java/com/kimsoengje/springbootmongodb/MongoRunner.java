package com.kimsoengje.springbootmongodb;

import com.kimsoengje.springbootmongodb.account.Account;
import com.kimsoengje.springbootmongodb.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MongoRunner implements ApplicationRunner {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Account account = new Account();
        account.setUsername("root");
        account.setPassword("52ajrdj");
        account.setEmail("tjdwp3s@gmail.com");

        accountRepository.save(account);
    }
}
