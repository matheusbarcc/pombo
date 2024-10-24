package com.pruu.pombo.factories;

import com.pruu.pombo.model.entity.Complaint;
import com.pruu.pombo.model.entity.Publication;
import com.pruu.pombo.model.entity.User;
import com.pruu.pombo.model.enums.Reason;

public class ComplaintFactory {
    public static Complaint createComplaint(User user, Publication publication) {
        Complaint c = new Complaint();
        c.setUser(user);
        c.setPublication(publication);
        c.setReason(Reason.SCAM);

        return c;
    }
}
