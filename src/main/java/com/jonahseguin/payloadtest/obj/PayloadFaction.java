package com.jonahseguin.payloadtest.obj;

import com.jonahseguin.payload.object.obj.ObjectCacheable;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

@Entity("payloadTestFactions")
@Getter
@Setter
public class PayloadFaction implements ObjectCacheable {

    @Id
    private ObjectId id;

    @Property("name")
    private String name;
    private double balance = 0.00;

    public PayloadFaction() {
    }

    public PayloadFaction(String name) {
        this.name = name;
    }

    @Override
    public String getIdentifier() {
        return name;
    }

    @Override
    public boolean persist() {
        return true;
    }
}
