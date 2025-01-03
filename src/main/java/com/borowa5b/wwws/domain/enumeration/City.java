package com.borowa5b.wwws.domain.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum City {

    JASTARNIA(54.69606f, 18.67873f, Country.PL),
    BRIDGETOWN(13.10732f, -59.62021f, Country.BB),
    FORTALEZA(-3.71722f, -38.54306f, Country.BR),
    PISSOURI(34.66942f, 32.70132f, Country.CY),
    LE_MORNE(-20.44509f, 57.32895f, Country.MU);

    private final float latitude;
    private final float longitude;
    private final Country country;

    @Override
    public String toString() {
        return this.name();
    }
}
