package org.jboss.errai.common.client.framework;

/**
 * Non-instantiable utility methods for comparing two or more values.
 *
 * @author Jonathan Fuerth <jfuerth@gmail.com>
 */
public class Comparisons {

  /**
   * Tests two potentially null object references for equality.
   * <p>
   * Specifically, this method returns true if and only if any of the
   * following conditions are met:
   * <ol>
   * <li>{@code o1 == o2} (satisfied if {@code o1} and {@code o2} are both null)
   * <li>{@code o1.equals(o2)}
   * </ol>
   *
   * @param o1
   *          One object to compare. Null is permitted.
   * @param o2
   *          The other object to compare. Null is permitted.
   * @return true if o1 and o2 are equal (either by reference equality or by
   *         Object.equals()); false otherwise
   */
  // MAINTAINERS BEWARE: Errai JPA generates code that uses this method.
  public static boolean nullSafeEquals(Object o1, Object o2) {
    return (o1 == o2) || (o1 != null && o1.equals(o2));
  }

  /**
   * Tests if one potentially null object reference is greater than another.
   *
   * @param o1
   *          One object to compare. Null is permitted.
   * @param o2
   *          The other object to compare. Null is permitted.
   * @return true if {@code o1 > o2} (either by primitive comparison or by
   *         Comparable.compareTo()); false otherwise
   */
  // MAINTAINERS BEWARE: Errai JPA generates code that uses this method.
  public static boolean nullSafeGreaterThan(Object o1, Object o2) {
    if (o1 == null || o2 == null) return false;
    if (o1 instanceof Number && o2 instanceof Number) {
      return ((Number) o1).doubleValue() > ((Number) o2).doubleValue();
    }
    if (o1 instanceof Comparable<?>) {
      return ((Comparable<Object>) o1).compareTo(o2) > 0;
    }
    throw new IllegalArgumentException(
            "Can't compare an instance of " + o1.getClass() + " to an instance of " + o2.getClass());
  }

  /**
   * Tests if one potentially null object reference is greater than another.
   *
   * @param o1
   *          One object to compare. Null is permitted.
   * @param o2
   *          The other object to compare. Null is permitted.
   * @return true if {@code o1 > o2} (either by primitive comparison or by
   *         Comparable.compareTo()); false otherwise
   */
  // MAINTAINERS BEWARE: Errai JPA generates code that uses this method.
  public static boolean nullSafeGreaterThanOrEqualTo(Object o1, Object o2) {
    if (o1 == null || o2 == null) return false;
    if (o1 instanceof Number && o2 instanceof Number) {
      return ((Number) o1).doubleValue() >= ((Number) o2).doubleValue();
    }
    if (o1 instanceof Comparable<?>) {
      return ((Comparable<Object>) o1).compareTo(o2) >= 0;
    }
    throw new IllegalArgumentException(
            "Can't compare an instance of " + o1.getClass() + " to an instance of " + o2.getClass());
  }

  /**
   * Tests if one potentially null object reference is greater than another.
   *
   * @param o1
   *          One object to compare. Null is permitted.
   * @param o2
   *          The other object to compare. Null is permitted.
   * @return true if {@code o1 > o2} (either by primitive comparison or by
   *         Comparable.compareTo()); false otherwise
   */
  // MAINTAINERS BEWARE: Errai JPA generates code that uses this method.
  public static boolean nullSafeLessThan(Object o1, Object o2) {
    if (o1 == null || o2 == null) return false;
    if (o1 instanceof Number && o2 instanceof Number) {
      return ((Number) o1).doubleValue() < ((Number) o2).doubleValue();
    }
    if (o1 instanceof Comparable<?>) {
      return ((Comparable<Object>) o1).compareTo(o2) < 0;
    }
    throw new IllegalArgumentException(
            "Can't compare an instance of " + o1.getClass() + " to an instance of " + o2.getClass());
  }

  /**
   * Tests if one potentially null object reference is greater than another.
   *
   * @param o1
   *          One object to compare. Null is permitted.
   * @param o2
   *          The other object to compare. Null is permitted.
   * @return true if {@code o1 > o2} (either by primitive comparison or by
   *         Comparable.compareTo()); false otherwise
   */
  // MAINTAINERS BEWARE: Errai JPA generates code that uses this method.
  public static boolean nullSafeLessThanOrEqualTo(Object o1, Object o2) {
    if (o1 == null || o2 == null) return false;
    if (o1 instanceof Number && o2 instanceof Number) {
      return ((Number) o1).doubleValue() <= ((Number) o2).doubleValue();
    }
    if (o1 instanceof Comparable<?>) {
      return ((Comparable<Object>) o1).compareTo(o2) <= 0;
    }
    throw new IllegalArgumentException(
            "Can't compare an instance of " + o1.getClass() + " to an instance of " + o2.getClass());
  }

  /**
   * Compares one potentially null Comparable to another.
   *
   * @param c1
   *          One object to compare. Null is permitted.
   * @param c2
   *          The other object to compare. Null is permitted.
   * @return 0 if c1 and c2 are both null; -1 if c1 is null and c2 is not; 1 if
   *         c1 is not null and c2 is. Otherwise returns c1.compareTo(c2).
   */
  // MAINTAINERS BEWARE: Errai JPA generates code that uses this method.
  public static int nullSafeCompare(Comparable c1, Comparable c2) {
    if (c1 == null && c2 == null) return 0;
    if (c1 == null && c2 != null) return -1;
    if (c1 != null && c2 == null) return 1;
    return c1.compareTo(c2);
  }

  /**
   * Checks of the given value matches the given JPQL wildcard pattern.
   *
   * @param value
   *          The string value to test. May be null.
   * @param pattern
   *          The JPQL pattern to test against. Special characters are "{@code _}
   *          ", which matches any single character, and "{@code %}", which
   *          matches 0 or more characters.
   */
  // MAINTAINERS BEWARE: Errai JPA generates code that uses this method.
  public static Boolean like(String value, String pattern, String escapeChar) {
    if (value == null || pattern == null) {
      return null;
    }
    return value.matches(sqlWildcardToRegex(pattern, escapeChar));
  }

  private static String sqlWildcardToRegex(String pattern, String escapeChar) {
    char esc;
    if (escapeChar == null) {
      esc = 'x'; // (not used in this case)
    }
    else if (escapeChar.length() != 1) {
      throw new IllegalArgumentException("In LIKE x ESCAPE e, e must be a single-character string");
    }
    else {
      esc = escapeChar.charAt(0);
    }

    StringBuilder sb = new StringBuilder(pattern.length());
    for (int i = 0; i < pattern.length(); i++) {
      char ch = pattern.charAt(i);
      if (ch == esc) {
        // advance to next character and don't treat as wildcard
        ch = pattern.charAt(++i);
      }
      else if (ch == '_') {
        // wildcard: match any one char
        sb.append('.');
        continue;
      }
      else if (ch == '%') {
        // wildcard: match 0 or more chars
        sb.append(".*");
        continue;
      }

      // append non-jpql-wildcard char (escaping if it's a special regex char)
      switch(ch) {
      case '.':
      case '\\':
      case '+':
      case '*':
      case '?':
      case '[':
      case '^':
      case ']':
      case '$':
      case '(':
      case ')':
      case '{':
      case '}':
      case '=':
      case '!':
      case '<':
      case '>':
      case '|':
      case ':':
      case '-':
        sb.append('\\');
        // FALLTHROUGH

      default:
        sb.append(ch);
      }
    }
    return sb.toString();
  }
}
