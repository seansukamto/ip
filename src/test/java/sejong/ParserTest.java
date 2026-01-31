package sejong;

import org.junit.jupiter.api.Test;
import sejong.command.ByeCommand;
import sejong.command.Command;
import sejong.command.DeadlineCommand;
import sejong.command.DeleteCommand;
import sejong.command.EventCommand;
import sejong.command.FindCommand;
import sejong.command.ListCommand;
import sejong.command.MarkCommand;
import sejong.command.TodoCommand;
import sejong.command.UnmarkCommand;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for Parser.
 * Tests various parsing methods for command inputs with valid and invalid formats.
 */
public class ParserTest {

    @Test
    public void parseTaskIndex_validInput_success() throws SejongException {
        assertEquals(0, Parser.parseTaskIndex("mark 1"));
        assertEquals(4, Parser.parseTaskIndex("delete 5"));
        assertEquals(99, Parser.parseTaskIndex("unmark 100"));
    }

    @Test
    public void parseTaskIndex_invalidNumber_throwsException() {
        SejongException exception = assertThrows(SejongException.class, () -> {
            Parser.parseTaskIndex("mark abc");
        });
        assertEquals("OOPS!!! Please provide a valid task number.", exception.getMessage());
    }

    @Test
    public void parseTaskIndex_noNumber_throwsException() {
        SejongException exception = assertThrows(SejongException.class, () -> {
            Parser.parseTaskIndex("mark");
        });
        assertEquals("OOPS!!! Please provide a valid task number.", exception.getMessage());
    }

    @Test
    public void parseTaskIndex_multipleNumbers_throwsException() {
        SejongException exception = assertThrows(SejongException.class, () -> {
            Parser.parseTaskIndex("mark 1 2 3");
        });
        assertEquals("OOPS!!! Please provide a valid task number.", exception.getMessage());
    }

    @Test
    public void parseTaskIndex_extraSpaces_success() throws SejongException {
        assertEquals(2, Parser.parseTaskIndex("mark   3"));
        assertEquals(0, Parser.parseTaskIndex("delete  1"));
    }

    @Test
    public void parseDeadlineCommand_validInput_success() throws SejongException {
        String[] result = Parser.parseDeadlineCommand("deadline return book /by 2019-12-02");
        assertArrayEquals(new String[]{"return book", "2019-12-02"}, result);
    }

    @Test
    public void parseDeadlineCommand_withExtraSpaces_success() throws SejongException {
        String[] result = Parser.parseDeadlineCommand("deadline   return book   /by   2019-12-02  ");
        assertArrayEquals(new String[]{"return book", "2019-12-02"}, result);
    }

    @Test
    public void parseDeadlineCommand_emptyDescription_throwsException() {
        SejongException exception = assertThrows(SejongException.class, () -> {
            Parser.parseDeadlineCommand("deadline");
        });
        assertEquals("OOPS!!! The description of a deadline cannot be empty.", exception.getMessage());
    }

    @Test
    public void parseDeadlineCommand_noBy_throwsException() {
        SejongException exception = assertThrows(SejongException.class, () -> {
            Parser.parseDeadlineCommand("deadline return book");
        });
        assertEquals("OOPS!!! Please specify when the deadline is using /by.", exception.getMessage());
    }

    @Test
    public void parseDeadlineCommand_emptyDate_throwsException() {
        SejongException exception = assertThrows(SejongException.class, () -> {
            Parser.parseDeadlineCommand("deadline return book /by");
        });
        assertEquals("OOPS!!! The deadline time cannot be empty.", exception.getMessage());
    }

    @Test
    public void parseDeadlineCommand_descriptionMissing_throwsException() {
        SejongException exception = assertThrows(SejongException.class, () -> {
            Parser.parseDeadlineCommand("deadline /by 2019-12-02");
        });
        assertEquals("OOPS!!! The description of a deadline cannot be empty.", exception.getMessage());
    }

    @Test
    public void parseEventCommand_validInput_success() throws SejongException {
        String[] result = Parser.parseEventCommand("event project meeting /from 2019-12-02 /to 2019-12-03");
        assertArrayEquals(new String[]{"project meeting", "2019-12-02", "2019-12-03"}, result);
    }

    @Test
    public void parseEventCommand_withExtraSpaces_success() throws SejongException {
        String[] result = Parser.parseEventCommand("event   meeting   /from   2019-12-02   /to   2019-12-03  ");
        assertArrayEquals(new String[]{"meeting", "2019-12-02", "2019-12-03"}, result);
    }

    @Test
    public void parseEventCommand_emptyDescription_throwsException() {
        SejongException exception = assertThrows(SejongException.class, () -> {
            Parser.parseEventCommand("event");
        });
        assertEquals("OOPS!!! The description of an event cannot be empty.", exception.getMessage());
    }

    @Test
    public void parseEventCommand_missingFrom_throwsException() {
        SejongException exception = assertThrows(SejongException.class, () -> {
            Parser.parseEventCommand("event meeting /to 2019-12-03");
        });
        assertEquals("OOPS!!! Please specify the event time using /from and /to.", exception.getMessage());
    }

    @Test
    public void parseEventCommand_missingTo_throwsException() {
        SejongException exception = assertThrows(SejongException.class, () -> {
            Parser.parseEventCommand("event meeting /from 2019-12-02");
        });
        assertEquals("OOPS!!! Please specify the event time using /from and /to.", exception.getMessage());
    }

    @Test
    public void parseEventCommand_wrongOrder_throwsException() {
        SejongException exception = assertThrows(SejongException.class, () -> {
            Parser.parseEventCommand("event meeting /to 2019-12-03 /from 2019-12-02");
        });
        assertEquals("OOPS!!! Please use /from before /to.", exception.getMessage());
    }

    @Test
    public void parseEventCommand_emptyFromDate_throwsException() {
        SejongException exception = assertThrows(SejongException.class, () -> {
            Parser.parseEventCommand("event meeting /from /to 2019-12-03");
        });
        assertEquals("OOPS!!! The event start time cannot be empty.", exception.getMessage());
    }

    @Test
    public void parseEventCommand_emptyToDate_throwsException() {
        SejongException exception = assertThrows(SejongException.class, () -> {
            Parser.parseEventCommand("event meeting /from 2019-12-02 /to");
        });
        assertEquals("OOPS!!! The event end time cannot be empty.", exception.getMessage());
    }

    @Test
    public void parseFindCommand_validInput_success() throws SejongException {
        LocalDate result = Parser.parseFindCommand("find 2019-12-02");
        assertEquals(LocalDate.of(2019, 12, 2), result);
    }

    @Test
    public void parseFindCommand_emptyDate_throwsException() {
        SejongException exception = assertThrows(SejongException.class, () -> {
            Parser.parseFindCommand("find");
        });
        assertEquals("OOPS!!! Please provide a date to search for (yyyy-MM-dd format).", exception.getMessage());
    }

    @Test
    public void parseFindCommand_invalidDateFormat_throwsException() {
        SejongException exception = assertThrows(SejongException.class, () -> {
            Parser.parseFindCommand("find 02-12-2019");
        });
        assertEquals("Invalid date format! Please use yyyy-MM-dd format (e.g., 2019-12-02)", exception.getMessage());
    }

    @Test
    public void parseFindCommand_invalidDate_throwsException() {
        SejongException exception = assertThrows(SejongException.class, () -> {
            Parser.parseFindCommand("find 2019-13-32");
        });
        assertEquals("Invalid date format! Please use yyyy-MM-dd format (e.g., 2019-12-02)", exception.getMessage());
    }

    @Test
    public void parseTodoCommand_validInput_success() throws SejongException {
        String result = Parser.parseTodoCommand("todo read book");
        assertEquals("read book", result);
    }

    @Test
    public void parseTodoCommand_emptyDescription_throwsException() {
        SejongException exception = assertThrows(SejongException.class, () -> {
            Parser.parseTodoCommand("todo");
        });
        assertEquals("OOPS!!! The description of a todo cannot be empty.", exception.getMessage());
    }

    @Test
    public void parseTodoCommand_onlySpaces_throwsException() {
        SejongException exception = assertThrows(SejongException.class, () -> {
            Parser.parseTodoCommand("todo   ");
        });
        assertEquals("OOPS!!! The description of a todo cannot be empty.", exception.getMessage());
    }

    @Test
    public void parse_byeCommand_returnsCorrectCommand() throws SejongException {
        Command command = Parser.parse("bye");
        assertTrue(command instanceof ByeCommand);
    }

    @Test
    public void parse_listCommand_returnsCorrectCommand() throws SejongException {
        Command command = Parser.parse("list");
        assertTrue(command instanceof ListCommand);
    }

    @Test
    public void parse_markCommand_returnsCorrectCommand() throws SejongException {
        Command command = Parser.parse("mark 1");
        assertTrue(command instanceof MarkCommand);
    }

    @Test
    public void parse_unmarkCommand_returnsCorrectCommand() throws SejongException {
        Command command = Parser.parse("unmark 1");
        assertTrue(command instanceof UnmarkCommand);
    }

    @Test
    public void parse_deleteCommand_returnsCorrectCommand() throws SejongException {
        Command command = Parser.parse("delete 1");
        assertTrue(command instanceof DeleteCommand);
    }

    @Test
    public void parse_todoCommand_returnsCorrectCommand() throws SejongException {
        Command command = Parser.parse("todo read book");
        assertTrue(command instanceof TodoCommand);
    }

    @Test
    public void parse_deadlineCommand_returnsCorrectCommand() throws SejongException {
        Command command = Parser.parse("deadline return book /by 2019-12-02");
        assertTrue(command instanceof DeadlineCommand);
    }

    @Test
    public void parse_eventCommand_returnsCorrectCommand() throws SejongException {
        Command command = Parser.parse("event meeting /from 2019-12-02 /to 2019-12-03");
        assertTrue(command instanceof EventCommand);
    }

    @Test
    public void parse_findCommand_returnsCorrectCommand() throws SejongException {
        Command command = Parser.parse("find 2019-12-02");
        assertTrue(command instanceof FindCommand);
    }

    @Test
    public void parse_unknownCommand_throwsException() {
        SejongException exception = assertThrows(SejongException.class, () -> {
            Parser.parse("unknown command");
        });
        assertEquals("OOPS!!! I'm sorry, but I don't know what that means :-(", exception.getMessage());
    }
}
