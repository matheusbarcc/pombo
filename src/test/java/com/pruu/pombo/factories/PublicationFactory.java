package com.pruu.pombo.factories;

import com.pruu.pombo.model.entity.Publication;
import com.pruu.pombo.model.entity.User;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PublicationFactory {
    public static Publication createPublication(User user) {
        Publication p = new Publication();
        p.setUser(user);
        p.setContent("Lorem ipsum dolor sit amet");
        p.setLikes(new ArrayList<>());
        p.setComplaints(new ArrayList<>());
        p.setAttachment(null);

        return p;
    }
}
