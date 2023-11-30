package com.application.BitHub.misc;

import java.io.Serializable;
import java.util.Random;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

public class RandomIdGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) {
        Random random = new Random();
        // Generate a random integer ID (you can adjust the range as needed)
        return random.nextInt(Integer.MAX_VALUE);
    }
}
