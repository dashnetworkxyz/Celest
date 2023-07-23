/*
 * Celest
 * Copyright (C) 2023  DashNetwork
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.dashnetwork.celest.utils.log;

import xyz.dashnetwork.celest.Celest;
import xyz.dashnetwork.celest.utils.ConfigurationList;
import xyz.dashnetwork.celest.utils.chat.MessageUtils;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.chat.builder.Section;

public final class Logger {

    private static final org.slf4j.Logger logger = Celest.getLogger();

    public static void log(LogType type, boolean debug, String string, String... hovers) {
        if (!debug || ConfigurationList.DEBUG) {
            switch (type) {
                case INFO -> logger.info(string);
                case WARN -> logger.warn(string);
                case ERROR -> logger.error(string);
            }
        }

        String color = type.color();

        MessageBuilder builder = new MessageBuilder();
        Section section = builder.append("&f&l»&f [" + color + type.name() + "&f]: " + color + string);

        for (String hover : hovers)
            section.hover(hover);

        MessageUtils.broadcast(false, user -> user.getData().getDebug(), builder::build);
    }

    public static void throwable(Throwable throwable) {
        throwable.printStackTrace();

        String name = throwable.getClass().getName();
        StringBuilder hover = new StringBuilder();
        hover.append("&6").append(name).append(": ").append(throwable.getMessage());

        for (StackTraceElement element : throwable.getStackTrace())
            hover.append("\n&6at &7").append(element.getClassName()).append(": &6").append(element.getLineNumber());

        Throwable cause = throwable.getCause();

        if (cause != null) {
            hover.append("\n&6Caused by ").append(cause.getCause().getClass());

            for (StackTraceElement element : cause.getStackTrace())
                hover.append("\n&6at &7").append(element.getClassName()).append(": &6").append(element.getLineNumber());
        }

        MessageBuilder message = new MessageBuilder();
        message.append("&f&l»&f [&cTHROWABLE&f]:&c " + name).hover(hover.toString());

        MessageUtils.broadcast(false, user -> user.getData().getDebug(), message::build);
    }

}
