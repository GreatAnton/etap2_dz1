package edu2.innotech;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Currency {

    RUR("RUR", "Российский рубль"),
    USD("USD", "Доллар США"),
    EUR("EUR", "Евро"),
    BYN("BYN", "Белорусский рубль"),
	TRY("TRY", "Турецкая лира");

    private String code;
    private String name;

}
