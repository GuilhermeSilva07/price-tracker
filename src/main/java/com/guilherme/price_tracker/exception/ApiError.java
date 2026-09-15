package com.guilherme.price_tracker.exception;

import java.time.Instant;

public record ApiError(Instant timestamp, int status, String error, String message) {
}
