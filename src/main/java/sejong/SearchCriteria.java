package sejong;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Encapsulates search criteria for finding tasks.
 * Supports filtering by keywords, date, task type, and completion status.
 * 
 * Note: This file was created with AI-Assisted code development using Cursor.
 */
public class SearchCriteria {
    private final List<String> keywords;
    private final LocalDate date;
    private final TaskType taskType;
    private final CompletionStatus status;

    /**
     * Task types for filtering.
     */
    public enum TaskType {
        TODO, DEADLINE, EVENT, ALL
    }

    /**
     * Completion status for filtering.
     */
    public enum CompletionStatus {
        DONE, PENDING, ALL
    }

    /**
     * Creates search criteria with all parameters.
     *
     * @param keywords List of keywords to search for (all must match).
     * @param date     Specific date to filter by (null for no date filter).
     * @param taskType Type of task to filter by.
     * @param status   Completion status to filter by.
     */
    public SearchCriteria(List<String> keywords, LocalDate date, 
                         TaskType taskType, CompletionStatus status) {
        assert keywords != null : "Keywords list should not be null";
        assert taskType != null : "Task type should not be null";
        assert status != null : "Status should not be null";
        
        this.keywords = new ArrayList<>(keywords);
        this.date = date;
        this.taskType = taskType;
        this.status = status;
    }

    /**
     * Creates search criteria with only keywords (backward compatibility).
     *
     * @param keywords List of keywords to search for.
     */
    public SearchCriteria(List<String> keywords) {
        this(keywords, null, TaskType.ALL, CompletionStatus.ALL);
    }

    /**
     * Gets the keywords to search for.
     *
     * @return List of keywords.
     */
    public List<String> getKeywords() {
        return new ArrayList<>(keywords);
    }

    /**
     * Gets the date filter.
     *
     * @return Date to filter by, or null if no date filter.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Gets the task type filter.
     *
     * @return Task type to filter by.
     */
    public TaskType getTaskType() {
        return taskType;
    }

    /**
     * Gets the completion status filter.
     *
     * @return Completion status to filter by.
     */
    public CompletionStatus getStatus() {
        return status;
    }

    /**
     * Checks if any keywords are specified.
     *
     * @return True if keywords are present, false otherwise.
     */
    public boolean hasKeywords() {
        return !keywords.isEmpty();
    }

    /**
     * Checks if a date filter is specified.
     *
     * @return True if date is specified, false otherwise.
     */
    public boolean hasDateFilter() {
        return date != null;
    }

    /**
     * Checks if a specific task type filter is specified.
     *
     * @return True if filtering by specific type, false if ALL.
     */
    public boolean hasTypeFilter() {
        return taskType != TaskType.ALL;
    }

    /**
     * Checks if a specific status filter is specified.
     *
     * @return True if filtering by specific status, false if ALL.
     */
    public boolean hasStatusFilter() {
        return status != CompletionStatus.ALL;
    }
}
