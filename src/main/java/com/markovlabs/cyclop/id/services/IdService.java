package com.markovlabs.cyclop.id.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

@Service
public class IdService {
    
    private final TimeBasedGenerator uuidGenerator ;
    
    @Autowired
    public IdService(EthernetAddress ethernetAddress) {
        this.uuidGenerator = Generators.timeBasedGenerator(ethernetAddress);
    }
    
    public String getUUID(){
        return uuidGenerator.generate()
                            .toString();
    }
}
