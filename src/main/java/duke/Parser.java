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
            command = "event";
        } else if (regex.equalsIgnoreCase("/at")) {
            command = "deadline";
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

            default:
                ParsedAnswer pa = new ParsedAnswer("error", -1);
                pa.setDesc("Sorry, but I have no idea what you mean.");
                return pa;
        }
    }
}
