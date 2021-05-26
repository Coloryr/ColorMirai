package Color_yr.ColorMirai.plugin.http.context.MessageModule;

import Color_yr.ColorMirai.plugin.http.Authed;
import Color_yr.ColorMirai.plugin.http.Utils;
import Color_yr.ColorMirai.plugin.http.obj.StateCode;
import Color_yr.ColorMirai.plugin.http.obj.message.MessageDTO;
import Color_yr.ColorMirai.plugin.http.obj.message.SendDTO;
import Color_yr.ColorMirai.plugin.http.obj.message.SendRetDTO;
import Color_yr.ColorMirai.robot.BotStart;
import Color_yr.ColorMirai.robot.MessageSaveObj;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.message.MessageReceipt;
import net.mamoe.mirai.message.data.QuoteReply;

public class SendFriendMessage extends PostBaseMessage {
    @Override
    public Object toDo(Authed authed, SendDTO parameters) {
        QuoteReply quoteReply = null;
        if (parameters.quote != 0) {
            quoteReply = new QuoteReply(authed.cacheQueue.get(parameters.quote));
        }

        Bot bot = authed.bot;

        Friend friend = bot.getFriend(parameters.target);
        if (friend == null) {
            friend = bot.getFriend(parameters.qq);
        }
        if (friend == null) {
            return StateCode.NoElement;
        }

        MessageReceipt<Contact> receipt = Utils.sendMessage(quoteReply, MessageDTO.toMessageChain(friend, parameters.messageChain), friend);
        authed.cacheQueue.add(receipt.getSource());
        int id = receipt.getSource().getIds()[0];
        BotStart.addMessage(bot.getId(), id, new MessageSaveObj() {{
            this.source = receipt.getSource();
            this.time = receipt.getSource().getTime();
            this.sourceQQ = bot.getId();
            this.id = receipt.getSource().getIds()[0];
        }});
        return new SendRetDTO() {{
            this.messageId = receipt.getSource().getIds().length == 0 ? 0 : receipt.getSource().getIds()[0];
        }};
    }
}
