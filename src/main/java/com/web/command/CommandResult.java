package com.web.command;

public class CommandResult {
    private final boolean isRedirect;
    private final String page;

    public CommandResult(boolean isRedirect, String page) {
        this.isRedirect = isRedirect;
        this.page = page;
    }

    public boolean isRedirect() {
        return isRedirect;
    }

    public String getPage() {
        return page;
    }

    public static CommandResult forward(String page) {
        return new CommandResult(false, page);
    }

    public static CommandResult redirect(String page) {
        return new CommandResult(true, page);
    }
}
