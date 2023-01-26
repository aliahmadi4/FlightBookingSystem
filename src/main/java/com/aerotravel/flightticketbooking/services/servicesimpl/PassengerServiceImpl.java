package com.aerotravel.flightticketbooking.services.servicesimpl;

import com.aerotravel.flightticketbooking.model.Passenger;
import com.aerotravel.flightticketbooking.repository.PassengerRepository;
import com.aerotravel.flightticketbooking.services.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class PassengerServiceImpl extends AbstractEntityServiceImpl<Passenger> implements PassengerService {

    private final PassengerRepository passengerRepository;
    private final String[] sortBy = new String[]{"lastName"};

    @Autowired
    public PassengerServiceImpl(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    @Override
    protected JpaRepository<Passenger, Long> getRepository() {
        return passengerRepository;
    }

    @Override
    protected String[] getSortByProperties() {
        return sortBy;
    }

    @Override
    public List<Passenger> getAllByByPassportNumber(String number) {
        return passengerRepository.findAllByPassportNumber(number);
    }
}
