package coloryr.colormirai.plugin.mirai_http_api.context.messageModule;

import coloryr.colormirai.plugin.mirai_http_api.Authed;
import coloryr.colormirai.plugin.mirai_http_api.MiraiHttpUtils;
import coloryr.colormirai.plugin.mirai_http_api.context.PostBaseMessage;
import coloryr.colormirai.plugin.mirai_http_api.obj.StateCode;
import coloryr.colormirai.plugin.mirai_http_api.obj.message.MessageDTO;
import coloryr.colormirai.plugin.mirai_http_api.obj.message.SendDTO;
import coloryr.colormirai.plugin.mirai_http_api.obj.message.SendRetDTO;
import coloryr.colormirai.robot.BotStart;
import coloryr.colormirai.robot.MessageSaveObj;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.message.MessageReceipt;
import net.mamoe.mirai.message.data.QuoteReply;

public class SendGroupMessage extends PostBaseMessage {
    @Override
    public Object toDo(Authed authed, SendDTO parameters) {
        QuoteReply quoteReply = null;
        if (parameters.quote != 0) {
            quoteReply = new QuoteReply(authed.cacheQueue.get(parameters.quote));
        }

        Bot bot = authed.bot;

        Group group = bot.getGroup(parameters.target);
        if (group == null) {
            group = bot.getGroup(parameters.group);
        }
        if (group == null) {
            return StateCode.NoElement;
        }

        MessageReceipt<Contact> receipt = MiraiHttpUtils.sendMessage(quoteReply, MessageDTO.toMessageChain(group, parameters.messageChain), group);
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
