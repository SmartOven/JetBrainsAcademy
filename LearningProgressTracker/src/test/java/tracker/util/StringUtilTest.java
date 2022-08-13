package tracker.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilTest {

    @Test
    void noSpacesTest() {
        Integer[] startEnd = StringUtil.findLastWhitespacesSequence("abc");
        assertNull(startEnd);
    }

    @Test
    void oneSpaceTest() {
        Integer[] startEnd = StringUtil.findLastWhitespacesSequence("ab c");
        assertEquals(2, startEnd[0]);
        assertEquals(2, startEnd[1]);
    }

    @Test
    void oneGroupOfSpacesTest() {
        Integer[] startEnd = StringUtil.findLastWhitespacesSequence("ab   c");
        assertEquals(2, startEnd[0]);
        assertEquals(4, startEnd[1]);
    }

    @Test
    void multipleGroupsOfSpacesTest() {
        Integer[] startEnd = StringUtil.findLastWhitespacesSequence("a  b   c");
        assertEquals(4, startEnd[0]);
        assertEquals(6, startEnd[1]);
    }

    @Test
    void multipleGroupsOfWhitespacesTest() {
        Integer[] startEnd = StringUtil.findLastWhitespacesSequence("a  b\t \t c");
        assertEquals(4, startEnd[0]);
        assertEquals(7, startEnd[1]);
    }
}