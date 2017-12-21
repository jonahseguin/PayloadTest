package com.jonahseguin.payloadtest.obj;

import com.jonahseguin.payload.profile.profile.Profile;
import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("payloadTestProfiles")
@Getter
@Setter
public class PayloadProfile extends Profile {

    @Id
    private ObjectId id;

    private long lastLogin = 0;
    private double balance = 0;
    private long playTime = 0;

    public PayloadProfile() {
    }

    public PayloadProfile(String name, String uniqueId) {
        super(name, uniqueId);
    }
}
