package xyz.dashnetwork.celest.command.commands;

import com.velocitypowered.api.command.CommandSource;
import xyz.dashnetwork.celest.command.CelestCommand;
import xyz.dashnetwork.celest.command.arguments.Arguments;
import xyz.dashnetwork.celest.utils.chat.builder.MessageBuilder;
import xyz.dashnetwork.celest.utils.connection.User;

import java.util.function.Predicate;

public final class CommandTest extends CelestCommand {
    
    public CommandTest() {
        super("test");
        
        setPermission(User::isOwner, true);
    }
    
    @Override
    protected void execute(CommandSource source, String label, Arguments arguments) {
        Predicate<User> predicate = User::isOwner;
        predicate = predicate.negate();

        MessageBuilder builder = new MessageBuilder();
        builder.append("test1");
        builder.append("\ntest2").filter(predicate);
        builder.append("\ntest3").hover("test");
        builder.append("\ntest4").hover("test", predicate);
        builder.append("\ntest5").filter(predicate).hover("test");
        builder.append("\ntest5").filter(predicate).hover("test", predicate);

        builder.broadcast();
    }
    
}
