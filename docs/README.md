# Slowbro User Guide

Slowbro is a command-line task manager that helps you keep track of tasks,
deadlines, and events.

## Getting started

Start Slowbro and enter one command at a time. Slowbro saves your task list
automatically, so your tasks remain available the next time you start the
application.

## Adding tasks

### To-do tasks

Use `todo` to add a task without a deadline or time period.

**Format**

```text
todo <description>
```

**Example**

```text
todo Read chapter 5
```

Slowbro adds the task to your list and assigns it a task number.

### Deadlines

Use `deadline` to add a task that must be completed by a specific date or
time.

**Format**

```text
deadline <description> /by <date or time>
```

**Example**

```text
deadline Submit assignment /by Friday
```

### Events

Use `event` to add an activity with a starting and ending time.

**Format**

```text
event <description> /from <start> /to <end>
```

**Example**

```text
event Team meeting /from Monday 2pm /to Monday 4pm
```

## Viewing tasks

### List all tasks

Use `list` to display every task in your task list.

```text
list
```

Each task is shown with its task number. Use this number when marking or
deleting a task.

### Find tasks

Use `find` to search for tasks whose descriptions contain a keyword.

**Format**

```text
find <keyword>
```

**Example**

```text
find assignment
```

Slowbro displays all matching tasks.

## Updating tasks

### Mark a task as done

Use `mark` followed by the task number.

**Format**

```text
mark <task number>
```

**Example**

```text
mark 2
```

### Mark a task as not done

Use `unmark` followed by the task number to change a completed task back to
an incomplete task.

**Format**

```text
unmark <task number>
```

**Example**

```text
unmark 2
```

## Deleting tasks

Use `delete` followed by the task number to remove a task.

**Format**

```text
delete <task number>
```

**Example**

```text
delete 2
```

> [!WARNING]
> Deleting a task removes it from your task list. Check the task number
> carefully before entering the command.

## Exiting Slowbro

Use `bye` to close the application.

```text
bye
```

Your task list is saved automatically before you exit.

## Command summary

| Command | Purpose |
| --- | --- |
| `todo <description>` | Add a task |
| `deadline <description> /by <date or time>` | Add a deadline |
| `event <description> /from <start> /to <end>` | Add an event |
| `list` | Display all tasks |
| `find <keyword>` | Search for matching tasks |
| `mark <task number>` | Mark a task as done |
| `unmark <task number>` | Mark a task as not done |
| `delete <task number>` | Delete a task |
| `bye` | Exit Slowbro |
