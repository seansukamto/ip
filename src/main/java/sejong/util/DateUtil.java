package sejong.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import sejong.SejongException;

/**
 * Utility class for date parsing and formatting operations.
 * Centralizes date-related functionality to avoid code duplication.
 */
public class DateUtil {
    /** Standard date format for user input (yyyy-MM-dd). */
    public static final String INPUT_DATE_FORMAT = "yyyy-MM-dd";
    
    /** Display date format for user output (MMM dd yyyy). */
    public static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy");

    /**
     * Private constructor to prevent instantiation of utility class.
     */
    private DateUtil() {
        throw new AssertionError("DateUtil is a utility class and should not be instantiated");
    }

    /**
     * Parses a date string in yyyy-MM-dd format.
     *
     * @param dateStr Date string to parse.
     * @return Parsed LocalDate.
     * @throws SejongException If the format is invalid.
     */
    public static LocalDate parseDate(String dateStr) throws SejongException {
        assert dateStr != null : "Date string should not be null";
        try {
            return LocalDate.parse(dateStr);
        } catch (DateTimeParseException e) {
            throw new SejongException("Invalid date format! Please use " + INPUT_DATE_FORMAT 
                    + " format (e.g., 2019-12-02)");
        }
    }

    /**
     * Formats a LocalDate for display to the user.
     *
     * @param date Date to format.
     * @return Formatted date string (e.g., "Feb 13 2026").
     */
    public static String formatForDisplay(LocalDate date) {
        assert date != null : "Date should not be null";
        return date.format(DISPLAY_FORMATTER);
    }

    /**
     * Validates that the start date is not after the end date.
     *
     * @param from Start date.
     * @param to   End date.
     * @throws SejongException If start date is after end date.
     */
    public static void validateDateRange(LocalDate from, LocalDate to) throws SejongException {
        assert from != null : "Start date should not be null";
        assert to != null : "End date should not be null";
        if (from.isAfter(to)) {
            throw new SejongException("Start date cannot be after end date!");
        }
    }
}
