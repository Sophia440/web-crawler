package com.web.parser;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TermsParser implements Parser {
    private static final String DELIMITER = ",";

    @Override
    public List<String> parse(String data) {
        return Stream.of(data.split(DELIMITER))
                .map(String::new)
                .map(String::trim)
                .collect(Collectors.toList());
    }
}
