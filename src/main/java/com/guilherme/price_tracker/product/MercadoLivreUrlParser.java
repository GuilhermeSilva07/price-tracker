package com.guilherme.price_tracker.product;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class MercadoLivreUrlParser {

    private static final Pattern ITEM_ID_PATTERN = Pattern.compile("(?i)MLB-?(\\d{9,11})");

    private MercadoLivreUrlParser() {
    }

    static Optional<String> extractItemId(String url) {
        Matcher matcher = ITEM_ID_PATTERN.matcher(url);
        if (!matcher.find()) {
            return Optional.empty();
        }
        return Optional.of("MLB" + matcher.group(1));
    }
}
