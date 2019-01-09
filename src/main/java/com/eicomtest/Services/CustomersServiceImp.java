package com.eicomtest.Services;

import com.eicomtest.models.Customer;
import org.javalite.activejdbc.LazyList;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("customersService")
@Transactional
public class CustomersServiceImp implements CustomersService{
    public String findAllCustomers() {
        List<Customer> customers = Customer.findAll();
        String json = ((LazyList<Customer>) customers).toJson(true);
        return json;
    }
    public boolean findCustomersId(int id) {
        if (Customer.count("id = ?", id) > 0)
            return true;
        return false;
    }
}
