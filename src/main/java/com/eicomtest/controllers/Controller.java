package com.eicomtest.controllers;

import com.eicomtest.Services.CustomersService;
import com.eicomtest.Services.RequestsServices;
import com.eicomtest.models.Customer;
import com.eicomtest.models.Requests;
import com.google.gson.Gson;
import org.javalite.activejdbc.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

@RestController
public class Controller {

    @Autowired
    RequestsServices requestsServices;

    @Autowired
    CustomersService customersService;

    public Controller(){
    }

    @PostConstruct
    public void initIt() throws Exception {
        ResponseEntity<List<Requests>> forInitDB = listAllRequests();
    }


    @RequestMapping(path="/requests/", method=RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<Requests>> listAllRequests() {

        openConnection();

        Gson gson = new Gson();
        List<Requests> requests = gson.fromJson(requestsServices.findAllRequests(), ArrayList.class);

        if(requests.isEmpty()){
            return new ResponseEntity<List<Requests>>(HttpStatus.NO_CONTENT);
        }

        closeConnection();

        return new ResponseEntity<List<Requests>>(requests, HttpStatus.OK);
    }

    @RequestMapping(path="/requests/n/", method=RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<Requests>> listIdRequests() {

        return new ResponseEntity<List<Requests>>(HttpStatus.NO_CONTENT);

    }

    @RequestMapping(path="/requests/n/{n}", method=RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<Requests>> listIdRequests(@PathVariable(required = false) int n) {

        openConnection();

        Gson gson = new Gson();
        List<Requests> requests = gson.fromJson(requestsServices.findIdRequests(n), ArrayList.class);

        if(requests.isEmpty()){
            return new ResponseEntity<List<Requests>>(HttpStatus.NO_CONTENT);
        }

        closeConnection();

        return new ResponseEntity<List<Requests>>(requests, HttpStatus.OK);
    }

    @RequestMapping(path="/requests/date/{d}", method=RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<Requests>> listDateRequests(@PathVariable("d") String d) {

        openConnection();

        Gson gson = new Gson();
        List<Requests> requests = gson.fromJson(requestsServices.findDateRequests(d), ArrayList.class);

        if(requests.isEmpty()){
            return new ResponseEntity<List<Requests>>(HttpStatus.NO_CONTENT);
        }

        closeConnection();

        return new ResponseEntity<List<Requests>>(requests, HttpStatus.OK);
    }

    @RequestMapping(path = "/requests/", method = RequestMethod.POST, produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<String> createRequests(@RequestBody(required = false) ArrayList<Requests> requests, UriComponentsBuilder ucBuilder) throws IOException {

        if ( requests == null )
            return new ResponseEntity<String>("Falha: Nenhum pedido recebido.", HttpStatus.CONFLICT);
        if ( requests.size() > 10 )
            return new ResponseEntity<String>("Falha: O número de pedidos é maior que o permitido (10).", HttpStatus.CONFLICT);

        openConnection();
        Base.openTransaction();

        for(Requests r: requests) {
            if (requestsServices.isRequestExist(r)) {
                Base.rollbackTransaction();
                return new ResponseEntity<String>("O pedido " + r.id + " já existe no cadastro. Nenhum pedido foi efetivado.", HttpStatus.CONFLICT);
            }else if ( !customersService.findCustomersId(r.customer_id) ){
                Base.rollbackTransaction();
                return new ResponseEntity<String>("Falha: O cliente " + r.customer_id + " não existe. Nenhum pedido foi efetivado.", HttpStatus.CONFLICT);
            }else{
                requestsServices.saveRequest(r);
            }
        }

        Base.commitTransaction();
        closeConnection();

        return new ResponseEntity<String>("Todos os pedidos foram efetivados.", HttpStatus.CREATED);
    }

//----------------------------------------------------------------------------------------------------------/

    @RequestMapping(path="/customers/", method=RequestMethod.GET, produces={MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<Customer>> listAllCustomers() {

        openConnection();

        Gson gson = new Gson();
        List<Customer> customers = gson.fromJson(customersService.findAllCustomers(), ArrayList.class);

        if(customers.isEmpty()){
            return new ResponseEntity<List<Customer>>(HttpStatus.NO_CONTENT);
        }

        closeConnection();

        return new ResponseEntity<List<Customer>>(customers, HttpStatus.OK);
    }

    public void openConnection(){
        if ( !Base.hasConnection() )
            Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/eicon", "eicon", "eicon");
    }

    public void closeConnection(){
        Base.close();
    }

}
