/*
 * Celest
 * Copyright (C) 2023  DashNetwork
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as
 * published by the Free Software Foundation.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package xyz.dashnetwork.celest.inject.backend.handler;

import xyz.dashnetwork.celest.utils.reflection.velocity.connection.client.ReflectedClientPlaySessionHandler;
import xyz.dashnetwork.celest.utils.reflection.velocity.protocol.packet.chat.session.ReflectedSessionPlayerChat;
import xyz.dashnetwork.celest.utils.reflection.velocity.protocol.packet.chat.session.ReflectedSessionPlayerCommand;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

public final class CelestClientPlaySessionHandler implements InvocationHandler {

    private final ReflectedClientPlaySessionHandler handler;

    public CelestClientPlaySessionHandler(ReflectedClientPlaySessionHandler handler) { this.handler = handler; }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws ReflectiveOperationException {
        String name = method.getName();
        Class<?>[] types = method.getParameterTypes();

        if (name.equals("handle")) {
            if (Arrays.equals(types, ReflectedSessionPlayerChat.array())) {
                ReflectedSessionPlayerChat chat = new ReflectedSessionPlayerChat(args[0]);
                chat.setSigned(false);

                args[0] = chat.original();
            }

            if (Arrays.equals(types, ReflectedSessionPlayerCommand.array())) {
                ReflectedSessionPlayerCommand command = new ReflectedSessionPlayerCommand(args[0]);
                command.setSalt(0);

                args[0] = command.original();
            }
        }

        return handler.passOriginalMethod(method, args);
    }

}
