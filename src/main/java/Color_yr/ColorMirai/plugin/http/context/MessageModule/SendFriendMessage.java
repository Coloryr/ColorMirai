package Color_yr.ColorMirai.plugin.http.context.MessageModule;

import Color_yr.ColorMirai.plugin.http.Authed;
import Color_yr.ColorMirai.plugin.http.Utils;
import Color_yr.ColorMirai.plugin.http.obj.StateCode;
import Color_yr.ColorMirai.plugin.http.obj.message.SendDTO;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.message.data.QuoteReply;

import java.util.Map;

public class SendFriendMessage extends PostBaseMessage {
    @Override
    public Object toDo(Authed authed, SendDTO parameters) {
        QuoteReply quoteReply = null;
        if (parameters.quote != 0) {
            quoteReply = new QuoteReply(authed.cacheQueue.get(parameters.quote));
        }

        Bot bot = authed.bot;

        Contact contact = bot.getFriend(parameters.target);
        if(contact == null)
        {
            contact = bot.getFriend(parameters.qq);
        }
        if(contact == null)
        {
            return StateCode.NoElement;
        }

        receipt = Utils.sendMessage(quoteReply, parameters.messageChain)
    }
}
