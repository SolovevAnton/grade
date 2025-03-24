package com.solovev.kiteshop.conveter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.extern.slf4j.Slf4j;

import java.time.Year;

@Converter(autoApply = true)
@Slf4j
public class YearConverter implements AttributeConverter<Year, Short> {

    @Override
    public Short convertToDatabaseColumn(Year attribute) {
        short year = (short) attribute.getValue();
        log.debug("Convert Year [" + attribute + "] to short [" + year + "]");
        return year;
    }

    @Override
    public Year convertToEntityAttribute(Short dbValue) {
        Year year = Year.of(dbValue);
        log.debug("Convert Short [" + dbValue + "] to Year [" + year + "]");
        return year;
    }
}
