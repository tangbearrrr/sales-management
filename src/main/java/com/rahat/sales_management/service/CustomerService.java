package com.rahat.sales_management.service;

import com.rahat.sales_management.model.Customer;
import com.rahat.sales_management.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public List<Customer> findAll() {
        // todo pagination
        return customerRepository.findAll();
    }

    public Customer findById(Integer id) {
        return customerRepository.findById(id)
                .orElseThrow();
    }

    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer update(Integer id, Customer customer) {
        findById(id);
        customer.setId(id);
        return customerRepository.save(customer);
    }

    public void delete(Integer id) {
        customerRepository.deleteById(id);
    }
}
