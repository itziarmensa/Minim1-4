package edu.upc.dsa.Domain.Entity.VO;

import java.util.UUID;

public class RandomId {
    public static String getId() {
        return UUID.randomUUID().toString();
    }
}
