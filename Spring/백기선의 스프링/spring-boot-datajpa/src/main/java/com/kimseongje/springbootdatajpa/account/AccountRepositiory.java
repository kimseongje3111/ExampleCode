package com.kimseongje.springbootdatajpa.account;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepositiory extends JpaRepository<Account, Long> {

}
