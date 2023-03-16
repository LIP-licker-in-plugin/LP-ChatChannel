package com.licker2689.lcc.commands;

import com.darksoldier1404.dppc.utils.DataContainer;
import com.licker2689.lcc.ChatChannel;
import com.licker2689.lcc.functions.LCCFunction;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class LCCCommand implements CommandExecutor, TabCompleter {
    private final DataContainer data = ChatChannel.data;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(data.getPrefix() + "플레이어만 사용 가능합니다.");
            return false;
        }
        Player p = (Player) sender;
        if(args.length == 0) {
            if(p.isOp()) {
                p.sendMessage(data.getPrefix() + "/lcc force : 채팅 채널 강제사용 설정 (토글)");
            }
            p.sendMessage(data.getPrefix() + "/lcc 선택 <채널번호> - 해당 채널로 채팅 채널이 변경됩니다.");
            p.sendMessage(data.getPrefix() + "/lcc 전체 - 전체 채팅으로 전환 합니다.");
            return false;
        }
        if(args[0].equalsIgnoreCase("force")) {
            if(!p.isOp()) {
                p.sendMessage(data.getPrefix() + "권한이 없습니다.");
                return false;
            }
            LCCFunction.force(p);
            return false;
        }
        if(args[0].equalsIgnoreCase("전체")) {
            LCCFunction.setDefault(p);
            return false;
        }
        if(args[0].equalsIgnoreCase("선택")) {
            if(args.length == 1) {
                p.sendMessage(data.getPrefix() + "/lcc 선택 <채널번호> - 해당 채널로 채팅 채널이 변경됩니다.");
                return false;
            }
            LCCFunction.setChannel(p, args[1]);
            return false;
        }
        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(args.length == 1) {
            if(sender.isOp()) {
                return Arrays.asList("전체", "선택", "force");
            }else{
                return Arrays.asList("전체", "선택");
            }
        }
        return null;
    }
}
