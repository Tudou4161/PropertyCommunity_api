package io.propertyService.Service.Repository.Interface;

import io.propertyService.Service.Domain.IsEntity.Account;

import java.util.List;

public interface UserRepository {

    /**
     * @Author: 지영석
     * @Methods: save, findById, delete, findByUsername, findAll
     */

    void save(Account account);

    void delete(Long id);

    Account findById(Long id);

    Account findByUserEmail(String email);

    List<Account> findByUserEmail2(String email);

    List<Account> findAll();
}
