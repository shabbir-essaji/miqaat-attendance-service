package com.project.miqaatattendanceservice;

import java.util.HashSet;
import java.util.Set;

public final class Utils {
    private Utils() {
    }

    public static Set<Integer> checkGet(Set<Integer> set) {
        return set == null ? new HashSet<>() : set;
    }
}
