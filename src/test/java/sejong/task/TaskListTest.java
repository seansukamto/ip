package sejong.task;

import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sejong.SejongException;

/**
 * Test class for TaskList.
 * Tests task management operations including add, delete, mark, unmark, and find.
 */
public class TaskListTest {
    private TaskList taskList;
    private Todo sampleTodo;
    private Deadline sampleDeadline;
    private Event sampleEvent;

    @BeforeEach
    public void setUp() throws SejongException {
        taskList = new TaskList();
        sampleTodo = new Todo("read book");
        sampleDeadline = new Deadline("return book", "2024-12-31");
        sampleEvent = new Event("team meeting", "2024-12-01", "2024-12-02");
    }

    @Test
    public void addTask_singleTask_success() {
        taskList.addTask(sampleTodo);
        assertEquals(1, taskList.size());
    }

    @Test
    public void addTask_multipleTasks_success() {
        taskList.addTask(sampleTodo);
        taskList.addTask(sampleDeadline);
        taskList.addTask(sampleEvent);
        assertEquals(3, taskList.size());
    }

    @Test
    public void deleteTask_validIndex_success() throws SejongException {
        taskList.addTask(sampleTodo);
        taskList.addTask(sampleDeadline);
        
        Task deleted = taskList.deleteTask(0);
        assertEquals(sampleTodo, deleted);
        assertEquals(1, taskList.size());
    }

    @Test
    public void deleteTask_lastTask_success() throws SejongException {
        taskList.addTask(sampleTodo);
        taskList.addTask(sampleDeadline);
        
        Task deleted = taskList.deleteTask(1);
        assertEquals(sampleDeadline, deleted);
        assertEquals(1, taskList.size());
    }

    @Test
    public void deleteTask_negativeIndex_throwsException() {
        taskList.addTask(sampleTodo);
        
        SejongException exception = assertThrows(SejongException.class, () -> {
            taskList.deleteTask(-1);
        });
        assertEquals("OOPS!!! Please provide a valid task number.", exception.getMessage());
    }

    @Test
    public void deleteTask_indexTooLarge_throwsException() {
        taskList.addTask(sampleTodo);
        
        SejongException exception = assertThrows(SejongException.class, () -> {
            taskList.deleteTask(5);
        });
        assertEquals("OOPS!!! Please provide a valid task number.", exception.getMessage());
    }

    @Test
    public void deleteTask_emptyList_throwsException() {
        SejongException exception = assertThrows(SejongException.class, () -> {
            taskList.deleteTask(0);
        });
        assertEquals("OOPS!!! Please provide a valid task number.", exception.getMessage());
    }

    @Test
    public void getTask_validIndex_success() throws SejongException {
        taskList.addTask(sampleTodo);
        taskList.addTask(sampleDeadline);
        
        Task retrieved = taskList.getTask(1);
        assertEquals(sampleDeadline, retrieved);
    }

    @Test
    public void getTask_firstTask_success() throws SejongException {
        taskList.addTask(sampleTodo);
        taskList.addTask(sampleDeadline);
        
        Task retrieved = taskList.getTask(0);
        assertEquals(sampleTodo, retrieved);
    }

    @Test
    public void getTask_invalidIndex_throwsException() {
        taskList.addTask(sampleTodo);
        
        SejongException exception = assertThrows(SejongException.class, () -> {
            taskList.getTask(10);
        });
        assertEquals("OOPS!!! Please provide a valid task number.", exception.getMessage());
    }

    @Test
    public void markTask_validIndex_success() throws SejongException {
        taskList.addTask(sampleTodo);
        assertFalse(sampleTodo.isDone());
        
        Task marked = taskList.markTask(0);
        assertTrue(marked.isDone());
        assertEquals(sampleTodo, marked);
    }

    @Test
    public void markTask_alreadyMarked_remainsMarked() throws SejongException {
        taskList.addTask(sampleTodo);
        taskList.markTask(0);
        assertTrue(sampleTodo.isDone());
        
        taskList.markTask(0);
        assertTrue(sampleTodo.isDone());
    }

    @Test
    public void markTask_invalidIndex_throwsException() {
        taskList.addTask(sampleTodo);
        
        SejongException exception = assertThrows(SejongException.class, () -> {
            taskList.markTask(-1);
        });
        assertEquals("OOPS!!! Please provide a valid task number.", exception.getMessage());
    }

    @Test
    public void unmarkTask_validIndex_success() throws SejongException {
        taskList.addTask(sampleTodo);
        taskList.markTask(0);
        assertTrue(sampleTodo.isDone());
        
        Task unmarked = taskList.unmarkTask(0);
        assertFalse(unmarked.isDone());
        assertEquals(sampleTodo, unmarked);
    }

    @Test
    public void unmarkTask_alreadyUnmarked_remainsUnmarked() throws SejongException {
        taskList.addTask(sampleTodo);
        assertFalse(sampleTodo.isDone());
        
        taskList.unmarkTask(0);
        assertFalse(sampleTodo.isDone());
    }

    @Test
    public void unmarkTask_invalidIndex_throwsException() {
        taskList.addTask(sampleTodo);
        
        SejongException exception = assertThrows(SejongException.class, () -> {
            taskList.unmarkTask(100);
        });
        assertEquals("OOPS!!! Please provide a valid task number.", exception.getMessage());
    }

    @Test
    public void findTasksOnDate_deadlineOnDate_found() throws SejongException {
        Deadline deadline = new Deadline("submit assignment", "2024-12-15");
        taskList.addTask(deadline);
        taskList.addTask(sampleTodo);
        
        List<Task> found = taskList.findTasksOnDate(LocalDate.of(2024, 12, 15));
        assertEquals(1, found.size());
        assertEquals(deadline, found.get(0));
    }

    @Test
    public void findTasksOnDate_eventOnDate_found() throws SejongException {
        Event event = new Event("conference", "2024-12-10", "2024-12-12");
        taskList.addTask(event);
        taskList.addTask(sampleTodo);
        
        List<Task> found = taskList.findTasksOnDate(LocalDate.of(2024, 12, 11));
        assertEquals(1, found.size());
        assertEquals(event, found.get(0));
    }

    @Test
    public void findTasksOnDate_eventStartDate_found() throws SejongException {
        Event event = new Event("conference", "2024-12-10", "2024-12-12");
        taskList.addTask(event);
        
        List<Task> found = taskList.findTasksOnDate(LocalDate.of(2024, 12, 10));
        assertEquals(1, found.size());
    }

    @Test
    public void findTasksOnDate_eventEndDate_found() throws SejongException {
        Event event = new Event("conference", "2024-12-10", "2024-12-12");
        taskList.addTask(event);
        
        List<Task> found = taskList.findTasksOnDate(LocalDate.of(2024, 12, 12));
        assertEquals(1, found.size());
    }

    @Test
    public void findTasksOnDate_eventBeforeStartDate_notFound() throws SejongException {
        Event event = new Event("conference", "2024-12-10", "2024-12-12");
        taskList.addTask(event);
        
        List<Task> found = taskList.findTasksOnDate(LocalDate.of(2024, 12, 9));
        assertEquals(0, found.size());
    }

    @Test
    public void findTasksOnDate_eventAfterEndDate_notFound() throws SejongException {
        Event event = new Event("conference", "2024-12-10", "2024-12-12");
        taskList.addTask(event);
        
        List<Task> found = taskList.findTasksOnDate(LocalDate.of(2024, 12, 13));
        assertEquals(0, found.size());
    }

    @Test
    public void findTasksOnDate_multipleTasksOnDate_foundAll() throws SejongException {
        Deadline deadline1 = new Deadline("task 1", "2024-12-15");
        Deadline deadline2 = new Deadline("task 2", "2024-12-15");
        Event event = new Event("meeting", "2024-12-14", "2024-12-16");
        
        taskList.addTask(deadline1);
        taskList.addTask(sampleTodo);
        taskList.addTask(deadline2);
        taskList.addTask(event);
        
        List<Task> found = taskList.findTasksOnDate(LocalDate.of(2024, 12, 15));
        assertEquals(3, found.size());
    }

    @Test
    public void findTasksOnDate_noTasksOnDate_emptyList() {
        taskList.addTask(sampleTodo);
        
        List<Task> found = taskList.findTasksOnDate(LocalDate.of(2024, 12, 15));
        assertEquals(0, found.size());
    }

    @Test
    public void findTasksOnDate_onlyTodos_emptyList() {
        taskList.addTask(sampleTodo);
        taskList.addTask(new Todo("another task"));
        
        List<Task> found = taskList.findTasksOnDate(LocalDate.of(2024, 12, 15));
        assertEquals(0, found.size());
    }

    @Test
    public void size_emptyList_returnsZero() {
        assertEquals(0, taskList.size());
    }

    @Test
    public void size_afterAddingTasks_returnsCorrectSize() {
        taskList.addTask(sampleTodo);
        taskList.addTask(sampleDeadline);
        taskList.addTask(sampleEvent);
        assertEquals(3, taskList.size());
    }

    @Test
    public void size_afterDeletingTask_returnsCorrectSize() throws SejongException {
        taskList.addTask(sampleTodo);
        taskList.addTask(sampleDeadline);
        assertEquals(2, taskList.size());
        
        taskList.deleteTask(0);
        assertEquals(1, taskList.size());
    }

    @Test
    public void getTasks_returnsCopyOfList() {
        taskList.addTask(sampleTodo);
        taskList.addTask(sampleDeadline);
        
        List<Task> tasks = taskList.getTasks();
        assertEquals(2, tasks.size());
        
        // Modify returned list shouldn't affect original
        tasks.clear();
        assertEquals(2, taskList.size());
    }

    @Test
    public void constructor_withTaskList_createsCopy() throws SejongException {
        taskList.addTask(sampleTodo);
        taskList.addTask(sampleDeadline);
        
        TaskList copy = new TaskList(taskList.getTasks());
        assertEquals(2, copy.size());
        assertEquals(sampleTodo, copy.getTask(0));
        assertEquals(sampleDeadline, copy.getTask(1));
    }
}
