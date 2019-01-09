package com.eicomtest.Services;

import java.util.Date;
import java.util.List;
import com.eicomtest.models.Requests;
import org.javalite.activejdbc.LazyList;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("requestsServices")
@Transactional
public class RequestsServicesImp implements RequestsServices {

    public String findAllRequests() {
        List<Requests> requests = Requests.findAll();
        String json = ((LazyList<Requests>) requests).toJson(true);
        return json;
    }

    public String findIdRequests(int n) {
        List<Requests> requests = Requests.where("id = ?", n);
        String json = ((LazyList<Requests>) requests).toJson(true);
        return json;
    }

    public String findDateRequests(String d) {
        List<Requests> requests = Requests.where("date = ?", d);
        String json = ((LazyList<Requests>) requests).toJson(true);
        return json;
    }

    public boolean isRequestExist(Requests r) {
        if (Requests.count("id = ?", r.id) > 0)
            return true;
        return false;
    }

    public void saveRequest(Requests r) {

        r.date = checkTheDate(r);

        if ( r.quantity == 0 )
            r.quantity = 1;

        r.amount = calculateAmount(r);

        r.set("id", r.id, "date", r.date, "description", r.description, "customer_id", r.customer_id, "quantity", r.quantity, "unitary_value", r.unitary_value, "amount", r.amount);
        r.insert();
    }

    Date checkTheDate(Requests r){
        Date d = new Date();
        if ( r.date == null ){
            r.date = d;
        }
        return r.date;
    }

    double calculateAmount(Requests r){
        r.amount = r.quantity * r.unitary_value;
        if ( r.quantity > 5 && r.quantity < 10 )
            r.amount = r.amount * 0.95;
        else if ( r.quantity >= 10 )
            r.amount = r.amount * 0.90;
        return r.amount;
    }

}