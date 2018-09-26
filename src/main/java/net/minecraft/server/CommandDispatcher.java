package net.minecraft.server;

import java.util.Iterator;

public class CommandDispatcher extends CommandHandler implements ICommandDispatcher {

    public CommandDispatcher() {
        this.a((ICommand) (new CommandTime()));
        this.a((ICommand) (new CommandGamemode()));
        this.a((ICommand) (new CommandDifficulty()));
        this.a((ICommand) (new CommandGamemodeDefault()));
        this.a((ICommand) (new CommandKill()));
        this.a((ICommand) (new CommandToggleDownfall()));
        this.a((ICommand) (new CommandWeather()));
        this.a((ICommand) (new CommandTp()));
        this.a((ICommand) (new CommandGive()));
        this.a((ICommand) (new CommandSeed()));
        this.a((ICommand) (new CommandTell()));
        this.a((ICommand) (new CommandSay()));
        this.a((ICommand) (new CommandSpawnpoint()));
        this.a((ICommand) (new CommandSetWorldSpawn()));
        this.a((ICommand) (new CommandGamerule()));
        this.a((ICommand) (new CommandClear()));
        this.a((ICommand) (new CommandScoreboard()));
        this.a((ICommand) (new CommandExecute()));
        this.a((ICommand) (new CommandSummon()));
        this.a((ICommand) (new CommandWorldBorder()));
        this.a((ICommand) (new CommandTitle()));
        if (MinecraftServer.getServer().ae()) {
            this.a((ICommand) (new CommandOp()));
            this.a((ICommand) (new CommandDeop()));
            this.a((ICommand) (new CommandStop()));
            this.a((ICommand) (new CommandSaveAll()));
            this.a((ICommand) (new CommandSaveOff()));
            this.a((ICommand) (new CommandSaveOn()));
            this.a((ICommand) (new CommandBanIp()));
            this.a((ICommand) (new CommandPardonIP()));
            this.a((ICommand) (new CommandBan()));
            this.a((ICommand) (new CommandBanList()));
            this.a((ICommand) (new CommandPardon()));
            this.a((ICommand) (new CommandKick()));
            this.a((ICommand) (new CommandList()));
            this.a((ICommand) (new CommandWhitelist()));
        } else {
            this.a((ICommand) (new CommandPublish()));
        }

        CommandAbstract.a((ICommandDispatcher) this);
    }

    public void a(ICommandListener icommandlistener, ICommand icommand, int i, String s, Object... aobject) {
        boolean flag = true;
        MinecraftServer minecraftserver = MinecraftServer.getServer();

        if (!icommandlistener.getSendCommandFeedback()) {
            flag = false;
        }

        ChatMessage chatmessage = new ChatMessage("chat.type.admin", new Object[] { icommandlistener.getName(), new ChatMessage(s, aobject)});

        chatmessage.getChatModifier().setColor(EnumChatFormat.GRAY);
        chatmessage.getChatModifier().setItalic(Boolean.valueOf(true));
        if (flag) {
            Iterator iterator = minecraftserver.getPlayerList().v().iterator();

            while (iterator.hasNext()) {
                EntityHuman entityhuman = (EntityHuman) iterator.next();

                if (entityhuman != icommandlistener && minecraftserver.getPlayerList().isOp(entityhuman.getProfile()) && icommand.canUse(icommandlistener)) {
                    boolean flag1 = icommandlistener instanceof MinecraftServer && MinecraftServer.getServer().r();
                    boolean flag2 = icommandlistener instanceof RemoteControlCommandListener && MinecraftServer.getServer().q();

                    if (flag1 || flag2 || !(icommandlistener instanceof RemoteControlCommandListener) && !(icommandlistener instanceof MinecraftServer)) {
                        entityhuman.sendMessage(chatmessage);
                    }
                }
            }
        }

        if (icommandlistener != minecraftserver && minecraftserver.worldServer[0].getGameRules().getBoolean("logAdminCommands") && !org.spigotmc.SpigotConfig.silentCommandBlocks) { // Spigot
            minecraftserver.sendMessage(chatmessage);
        }

        boolean flag3 = minecraftserver.worldServer[0].getGameRules().getBoolean("sendCommandFeedback");

        if (icommandlistener instanceof CommandBlockListenerAbstract) {
            flag3 = ((CommandBlockListenerAbstract) icommandlistener).m();
        }

        if ((i & 1) != 1 && flag3 || icommandlistener instanceof MinecraftServer) {
            icommandlistener.sendMessage(new ChatMessage(s, aobject));
        }

    }
}