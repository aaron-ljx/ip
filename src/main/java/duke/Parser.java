package duke;

/**
 * This class is responsible for parsing user inputs and
 * returns a ParsedAnswer object that contains information that has been parsed by this class.
 */
public class Parser {
    private String input;

    Parser(String input) {
        this.input = input;
    }

    void assertInputNotEmpty() {
        assert !input.isEmpty() : "Input cannot be left blank.";
    }

    String getCommandThroughRegex(String regex) {
        String command = "";
        if (regex.equalsIgnoreCase("/by")) {
            command = "deadline";
        } else if (regex.equalsIgnoreCase("/at")) {
            command = "event";
        } else {
            command = "todo";
        }

        return command;
    }


    ParsedAnswer parseInputWithRegex(String regex, String[] inputToParse) {
        String command = getCommandThroughRegex(regex);
        if (regex.equalsIgnoreCase("none")) {
            try {
                String desc = inputToParse[1];
                ParsedAnswer pa = new ParsedAnswer(command, -1);
                pa.setDesc(desc);
                return pa;
            } catch (Exception e) {
                ParsedAnswer pa = new ParsedAnswer("error", -1);
                pa.setDesc("The description cannot be empty!");
                return pa;
            }
        } else {
            try {
                String[] eventParseBy = inputToParse[1].split(regex);
                ParsedAnswer pa;
                if (eventParseBy.length <= 1) {
                    pa = new ParsedAnswer("error", -1);
                    pa.setDesc("Please check that your input format is correct.");
                } else {
                    pa = new ParsedAnswer(command, -1);
                    pa.setDesc(eventParseBy[0]);
                    pa.setDate(eventParseBy[1]);
                }
                return pa;
            } catch (Exception e) {
                ParsedAnswer pa = new ParsedAnswer("error", -1);
                pa.setDesc("The description cannot be empty!");
                return pa;
            }
        }
    }

    public ParsedAnswer parse() {
        assertInputNotEmpty();
        String[] parsedString = input.toLowerCase().split(" ", 2);
        switch (parsedString[0]) {
            case "bye":
                return new ParsedAnswer("bye", -1);

            case "list":
                return new ParsedAnswer("list", -1);

            case "unmark":
                try {
                    int idx = Integer.parseInt(parsedString[1]);
                    return new ParsedAnswer("unmark", idx);
                } catch (Exception e) {
                    return new ParsedAnswer("unmark", -1);
                }

            case "mark":
                try {
                    int idx = Integer.parseInt(parsedString[1]);
                    return new ParsedAnswer("mark", idx);
                } catch (Exception e) {
                    return new ParsedAnswer("mark", -1);
                }

            case "delete":
                try {
                    int idx = Integer.parseInt(parsedString[1]);
                    return new ParsedAnswer("delete", idx);
                } catch (Exception e) {
                    return new ParsedAnswer("delete", -1);
                }

            case "todo":
                return parseInputWithRegex("none", parsedString);

            case "event":
                return parseInputWithRegex("/at", parsedString);

            case "deadline":
                return parseInputWithRegex("/by", parsedString);

            case "find":
                try {
                    ParsedAnswer pa = new ParsedAnswer("find", -1);
                    pa.setDesc(parsedString[1]);
                    return pa;
                } catch (Exception e) {
                    ParsedAnswer pa = new ParsedAnswer("error", -1);
                    pa.setDesc("Please specify what you're searching for.");
                    return pa;
                }

            case "update":
                String[] parsedUpdateString = parsedString[1].toLowerCase().split(" ", 2);
                try {
                    switch (parsedUpdateString[0]) {
                        case "todo":
                            String[] parsedToDoUpdate = parsedUpdateString[1].split(" ", 2);
                            try {
                                int index = Integer.parseInt(parsedToDoUpdate[0]);
                                String updatedDesc = parsedToDoUpdate[1];
                                ParsedAnswer pa = new ParsedAnswer("update todo", index);
                                pa.setDesc(updatedDesc);
                                return pa;

                            } catch (NumberFormatException e) {
                                ParsedAnswer pa = new ParsedAnswer("error", -1);
                                pa.setDesc("Please specify an item.");
                                return pa;
                            }

                        case "event":
                            String[] parsedEventUpdate = parsedUpdateString[1].split(" ", 2);
                            try {
                                int index = Integer.parseInt(parsedEventUpdate[0]);
                                String[] parsedEventDescAndDate = parsedEventUpdate[1].split("/at", 2);
                                ParsedAnswer pa = new ParsedAnswer("update event", index);
                                if (parsedEventDescAndDate.length <= 1) {
                                    System.out.println("only desc");
                                    pa.setDesc(parsedEventDescAndDate[0]);
                                } else {
                                    System.out.println("descdate");
                                    System.out.println(parsedEventDescAndDate[1]);
                                    pa.setDesc(parsedEventDescAndDate[0]);
                                    pa.setDate(parsedEventDescAndDate[1]);
                                }

                                return pa;

                            } catch (NumberFormatException e) {
                                ParsedAnswer pa = new ParsedAnswer("error", -1);
                                pa.setDesc("Please specify an item.");
                                return pa;
                            }

                        case "deadline":
                            String[] parsedDeadlineUpdate = parsedUpdateString[1].split(" ", 2);
                            try {
                                int index = Integer.parseInt(parsedDeadlineUpdate[0]);
                                String[] parsedDeadlineDescAndDate =parsedDeadlineUpdate[1].split("/by", 2);
                                ParsedAnswer pa = new ParsedAnswer("update deadline", index);
                                if (parsedDeadlineDescAndDate.length <= 1) {
                                    pa.setDesc(parsedDeadlineDescAndDate[0]);
                                } else {
                                    pa.setDesc(parsedDeadlineDescAndDate[0]);
                                    pa.setDate(parsedDeadlineDescAndDate[1]);
                                }

                                return pa;

                            } catch (NumberFormatException e) {
                                ParsedAnswer pa = new ParsedAnswer("error", -1);
                                pa.setDesc("Please specify an item.");
                                return pa;
                            }
                        default:
                            ParsedAnswer pa = new ParsedAnswer("error", -1);
                            pa.setDesc("Sorry, but I have no idea what you want to update.");
                            return pa;
                    }

                } catch (Exception e) {
                    ParsedAnswer pa = new ParsedAnswer("error", -1);
                    pa.setDesc("There has been an error in updating your item. Please try again.");
                    return pa;
                }
            default:
                ParsedAnswer pa = new ParsedAnswer("error", -1);
                pa.setDesc("Sorry, but I have no idea what you mean.");
                return pa;
        }
    }
}
