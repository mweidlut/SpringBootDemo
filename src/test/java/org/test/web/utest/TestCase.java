package org.test.web.utest;

import jersey.repackaged.com.google.common.collect.ImmutableList;
import jersey.repackaged.com.google.common.collect.ImmutableListMultimap;
import org.junit.Test;

public class TestCase {

    @Test
    public void testMultimap() {
        ImmutableListMultimap.Builder<Integer, String> builder = ImmutableListMultimap.builder();
        builder.put(1, "a").put(1, "b").put(2, "c");

        ImmutableListMultimap<Integer, String> immutableListMultimap = builder.build();
        ImmutableList<String> valueList = immutableListMultimap.get(1);

        System.out.println(valueList.toString());
    }
}
