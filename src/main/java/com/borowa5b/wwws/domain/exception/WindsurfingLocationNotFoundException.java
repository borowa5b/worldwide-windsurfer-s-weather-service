package com.borowa5b.wwws.domain.exception;

import org.zalando.problem.Status;

public class WindsurfingLocationNotFoundException extends DomainException {

    public WindsurfingLocationNotFoundException() {
        super(Status.NOT_FOUND, "Windsurfing location not found. Try changing date criteria.");
    }
}
