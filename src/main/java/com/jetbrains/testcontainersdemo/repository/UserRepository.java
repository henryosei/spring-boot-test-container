package com.jetbrains.testcontainersdemo.repository;

import com.jetbrains.testcontainersdemo.Customer;
import org.springframework.data.repository.ListCrudRepository;

public interface UserRepository extends ListCrudRepository<Customer, Long> {
}
