package com.buildingcompany.utility.adapters;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

public class XmlDateTimeAdapter extends XmlAdapter<String, Date> {
    private final SimpleDateFormat xmlDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

    @Override
    public String marshal(Date date) {
        return xmlDateTimeFormat.format(date);
    }
    @Override
    public Date unmarshal(String raw) {
        return Date.from(Instant.parse(raw));
    }
}
