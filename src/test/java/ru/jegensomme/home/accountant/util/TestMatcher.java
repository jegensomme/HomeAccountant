package ru.jegensomme.home.accountant.util;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public record TestMatcher<T>(String... fieldsToIgnore) {
    public static <T> TestMatcher<T> usingIgnoringFieldsComparator(String... fieldsToIgnore) {
        return new TestMatcher<>(fieldsToIgnore);
    }

    public void assertMatch(T actual, T expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields(fieldsToIgnore).isEqualTo(expected);
    }

    @SafeVarargs
    public final void assertMatch(Iterable<T> actual, T... expected) {
        assertMatch(actual, List.of(expected));
    }

    public void assertMatch(Iterable<T> actual, Iterable<T> expected) {
        assertThat(actual).usingRecursiveFieldByFieldElementComparatorIgnoringFields(fieldsToIgnore).isEqualTo(expected);
    }
}
