# Sejong

Sejong is a personal task management chatbot that runs as a desktop GUI application. It helps you keep track of todos, deadlines, and events through a simple conversational interface.

## Quick Start

Prerequisites: **JDK 17**

1. Download the latest release JAR file.
2. Run the application:
   ```
   java -jar sejong.jar
   ```
3. Start adding tasks by typing commands in the input field.

## Features

### Adding a todo: `todo`

Adds a task without a date.

```
todo <description>
```

Example: `todo read book`

### Adding a deadline: `deadline`

Adds a task with a due date.

```
deadline <description> /by <yyyy-MM-dd>
```

Example: `deadline return book /by 2024-12-31`

### Adding an event: `event`

Adds a task with a start and end date.

```
event <description> /from <yyyy-MM-dd> /to <yyyy-MM-dd>
```

Example: `event team meeting /from 2024-12-01 /to 2024-12-02`

### Listing tasks: `list`

Shows all tasks in the list.

```
list
```

### Marking a task: `mark`

Marks a task as done.

```
mark <task number>
```

Example: `mark 1`

### Unmarking a task: `unmark`

Marks a task as not done.

```
unmark <task number>
```

Example: `unmark 1`

### Deleting a task: `delete`

Removes a task from the list.

```
delete <task number>
```

Example: `delete 2`

### Finding tasks: `find`

Searches tasks by keywords, date, type, or status. All filters are optional and can be combined.

```
find <keywords> [/date <yyyy-MM-dd>] [/type <todo|deadline|event>] [/status <done|pending>]
```

Examples:
- `find book` -- search by keyword
- `find /type deadline /status pending` -- all pending deadlines
- `find meeting /date 2024-12-15` -- keyword + date filter

### Exiting: `bye`

Exits the application.

```
bye
```

## Data Storage

Tasks are automatically saved to `./data/sejong.txt` and loaded on startup. Duplicate tasks (same type, description, and dates) are prevented.
