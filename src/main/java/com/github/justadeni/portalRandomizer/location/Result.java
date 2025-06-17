package com.github.justadeni.portalRandomizer.location;

public sealed interface Result<T> permits Result.Success, Result.Failure {
    record Success<T>(T value) implements Result<T> {}
    final class Failure<T> implements Result<T> {}
}
