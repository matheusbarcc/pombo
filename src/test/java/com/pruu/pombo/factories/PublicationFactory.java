package com.pruu.pombo.factories;

import com.pruu.pombo.model.entity.Publication;
import com.pruu.pombo.model.entity.User;

public class PublicationFactory {
    public static Publication createPublication(User user) {
        Publication p = new Publication();
        p.setUser(user);
        p.setContent("New Content");

        return p;
    }
}
