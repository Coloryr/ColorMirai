package coloryr.colormirai.plugin.mirai_http_api.obj.message;

import coloryr.colormirai.plugin.mirai_http_api.Utils;
import coloryr.colormirai.plugin.mirai_http_api.obj.DTO;
import coloryr.colormirai.plugin.mirai_http_api.obj.EventDTO;
import coloryr.colormirai.plugin.mirai_http_api.obj.IgnoreEventDTO;
import coloryr.colormirai.plugin.mirai_http_api.obj.contact.MemberDTO;
import coloryr.colormirai.plugin.mirai_http_api.obj.contact.QQDTO;
import com.alibaba.fastjson.annotation.JSONType;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.contact.Group;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.GroupTempMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import net.mamoe.mirai.message.data.*;
import net.mamoe.mirai.utils.ExternalResource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@JSONType(seeAlso = {XmlDTO.class, VoiceDTO.class, ImageDTO.class,
        UnknownMessageDTO.class, StrangerMessagePacketDTO.class, TempMessagePacketDTO.class,
        PlainDTO.class, FriendMessagePacketDTO.class, ForwardMessageDTO.class, FlashImageDTO.class,
        FileMessageDTO.class, FaceDTO.class, AtDTO.class, AtAllDTO.class, AppDTO.class}, typeKey = "type")
public class MessageDTO implements DTO {
    public static EventDTO toDTO(MessageEvent event) {
        EventDTO pack;
        if (event instanceof FriendMessageEvent) {
            FriendMessageEvent event1 = (FriendMessageEvent) event;
            QQDTO dto = new QQDTO(event1.getSender());
            pack = new FriendMessagePacketDTO(dto);
        } else if (event instanceof GroupMessageEvent) {
            GroupMessageEvent event1 = (GroupMessageEvent) event;
            MemberDTO dto = new MemberDTO(event1.getSender());
            pack = new GroupMessagePacketDTO(dto);
        } else if (event instanceof GroupTempMessageEvent) {
            GroupTempMessageEvent event1 = (GroupTempMessageEvent) event;
            MemberDTO dto = new MemberDTO(event1.getSender());
            pack = new TempMessagePacketDTO(dto);
        } else {
            pack = new IgnoreEventDTO();
        }
        if (pack instanceof MessagePacketDTO) {
            ((MessagePacketDTO) pack).messageChain = toMessageChainDTO(event);
        }
        return pack;
    }

    private static List<MessageDTO> toMessageChainDTO(MessageEvent event) {
        List<MessageDTO> list = new ArrayList<>();
        for (SingleMessage item : event.getMessage()) {
            list.add(toDTO(item));
        }
        return list;
    }

    public static List<MessageDTO> toMessageChainDTO(MessageChain message) {
        List<MessageDTO> list = new ArrayList<>();
        for (SingleMessage item : message) {
            MessageDTO dto = toDTO(item);
            if (dto instanceof UnknownMessageDTO)
                continue;
            list.add(dto);
        }
        return list;
    }

    public static MessageChain toMessageChain(Contact contact, List<MessageDTO> messages) {
        MessageChainBuilder builder = new MessageChainBuilder();
        for (MessageDTO item : messages) {
            Message message = toMessage(contact, item);
            if (message != null)
                builder.add(message);
        }
        return builder.build();
    }

    public static Message toMessage(Contact contact, MessageDTO message) {
        if (message instanceof AtDTO) {
            AtDTO item = (AtDTO) message;
            return new At(item.target);
        } else if (message instanceof AtAllDTO) {
            return AtAll.INSTANCE;
        } else if (message instanceof FaceDTO) {
            FaceDTO item = (FaceDTO) message;
            if (item.faceId >= 0) {
                return new Face(item.faceId);
            }
            if (item.name != null && !item.name.isEmpty()) {
                return new Face(Utils.getFace(item.name));
            }
            return new Face(255);
        } else if (message instanceof PlainDTO) {
            PlainDTO item = (PlainDTO) message;
            return new PlainText(item.text);
        } else if (message instanceof ImageDTO) {
            ImageDTO item = (ImageDTO) message;
            if (item.imageId != null && !item.imageId.isEmpty())
                return Image.fromId(item.imageId);
            if (item.url != null && !item.url.isEmpty()) {
                byte[] temp = Utils.getBytes(item.url);
                if (temp == null)
                    return null;
                ExternalResource image = ExternalResource.create(temp);
                Image image1 = contact.uploadImage(image);
                try {
                    image.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return image1;
            }
            if (item.path != null && !item.path.isEmpty()) {
                byte[] temp = Utils.getBytesFile(item.url);
                if (temp == null)
                    return null;
                ExternalResource image = ExternalResource.create(temp);
                Image image1 = contact.uploadImage(image);
                try {
                    image.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return image1;
            }
            return null;
        } else if (message instanceof FlashImageDTO) {
            FlashImageDTO item = (FlashImageDTO) message;
            if (item.imageId != null && !item.imageId.isEmpty())
                return new FlashImage(Image.fromId(item.imageId));
            if (item.url != null && !item.url.isEmpty()) {
                byte[] temp = Utils.getBytes(item.url);
                if (temp == null)
                    return null;
                ExternalResource image = ExternalResource.create(temp);
                FlashImage image1 = new FlashImage(contact.uploadImage(image));
                try {
                    image.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return image1;
            }
            if (item.path != null && !item.path.isEmpty()) {
                byte[] temp = Utils.getBytesFile(item.url);
                if (temp == null)
                    return null;
                ExternalResource image = ExternalResource.create(temp);
                FlashImage image1 = new FlashImage(contact.uploadImage(image));
                try {
                    image.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return image1;
            }
            return null;
        } else if (message instanceof ForwardMessageDTO) {
            ForwardMessageDTO item = (ForwardMessageDTO) message;
            List<ForwardMessage.Node> list = new ArrayList<>();
            for (NodeDTO item1 : item.nodeList) {
                ForwardMessage.Node node = new ForwardMessage.Node(
                        item1.senderId,
                        item1.time,
                        item1.senderName,
                        toMessageChain(contact, item1.messageChain)
                );
                list.add(node);
            }
            return new ForwardMessage(
                    item.preview,
                    item.title,
                    item.brief,
                    item.source,
                    item.summary,
                    list
            );
        } else if (message instanceof VoiceDTO) {
            VoiceDTO item = (VoiceDTO) message;
            if (contact instanceof Group) {
                Group group = (Group) contact;
                if (item.voiceId != null && !item.voiceId.isEmpty()) {
                    int index = item.voiceId.indexOf('.');
                    String temp = item.voiceId;
                    if (index != -1)
                        temp = item.voiceId.substring(0, index);
                    return new Voice(item.voiceId, Utils.hexToByteArray(temp), 0, 0, "");
                }
                if (item.url != null && !item.url.isEmpty()) {
                    byte[] temp = Utils.getBytes(item.url);
                    if (temp == null)
                        return null;
                    ExternalResource voice = ExternalResource.create(temp);
                    OfflineAudio audio = group.uploadAudio(voice);
                    try {
                        voice.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return audio;
                }
                if (item.path != null && !item.path.isEmpty()) {
                    byte[] temp = Utils.getBytesFile(item.path);
                    if (temp == null)
                        return null;
                    ExternalResource voice = ExternalResource.create(temp);
                    OfflineAudio audio = group.uploadAudio(voice);
                    try {
                        voice.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return audio;
                }
            } else
                return null;
        } else if (message instanceof XmlDTO) {
            XmlDTO item = (XmlDTO) message;
            return new SimpleServiceMessage(60, item.xml);
        } else if (message instanceof JsonDTO) {
            JsonDTO item = (JsonDTO) message;
            return new SimpleServiceMessage(1, item.json);
        } else if (message instanceof AppDTO) {
            AppDTO item = (AppDTO) message;
            return new LightApp(item.content);
        } else if (message instanceof MusicShareDTO) {
            MusicShareDTO item = (MusicShareDTO) message;
            if (item.brief != null && !item.brief.isEmpty()) {
                return new MusicShare(MusicKind.valueOf(item.kind), item.title, item.summary, item.jumpUrl, item.pictureUrl, item.musicUrl, item.brief);
            } else {
                return new MusicShare(MusicKind.valueOf(item.kind), item.title, item.summary, item.jumpUrl, item.pictureUrl, item.musicUrl);
            }
        } else if (message instanceof PokeMessageDTO) {
            PokeMessageDTO item = (PokeMessageDTO) message;
            return Utils.getPoke(item.name);
        }
        return null;
    }

    public static MessageDTO toDTO(SingleMessage item) {
        if (item instanceof MessageSource) {
            MessageSource item1 = (MessageSource) item;
            return new MessageSourceDTO(item1.getIds().length == 0 ? 0 : item1.getIds()[0], item1.getTime());
        } else if (item instanceof At) {
            At item1 = (At) item;
            return new AtDTO(item1.getTarget(), "");
        } else if (item instanceof AtAll) {
            return new AtAllDTO(0);
        } else if (item instanceof Face) {
            Face item1 = (Face) item;
            return new FaceDTO(item1.getId(), Utils.getFace(item1.getId()));
        } else if (item instanceof PlainText) {
            PlainText item1 = (PlainText) item;
            return new PlainDTO(item1.getContent());
        } else if (item instanceof Image) {
            Image item1 = (Image) item;
            return new ImageDTO(item1.getImageId(), Image.queryUrl(item1), "");
        } else if (item instanceof FlashImage) {
            FlashImage item1 = (FlashImage) item;
            return new FlashImageDTO(item1.getImage().getImageId(), Image.queryUrl(item1.getImage()), "");
        } else if (item instanceof Voice) {
            Voice item1 = (Voice) item;
            return new VoiceDTO(item1.getFileName(), item1.getUrl(), "");
        } else if (item instanceof ServiceMessage) {
            ServiceMessage item1 = (ServiceMessage) item;
            return new XmlDTO(item1.getContent());
        } else if (item instanceof LightApp) {
            LightApp item1 = (LightApp) item;
            return new AppDTO(item1.getContent());
        } else if (item instanceof MusicShare) {
            MusicShare item1 = (MusicShare) item;
            return new MusicShareDTO(item1.getKind().name(), item1.getTitle(), item1.getSummary(), item1.getJumpUrl(), item1.getPictureUrl(), item1.getMusicUrl(), item1.getBrief());
        } else if (item instanceof FileMessage) {
            FileMessage item1 = (FileMessage) item;
            return new FileMessageDTO(item1.getId(), item1.getInternalId(), item1.getName(), item1.getSize());
        } else if (item instanceof ForwardMessage) {
            ForwardMessage item1 = (ForwardMessage) item;
            return new ForwardMessageDTO(item1);
        } else if (item instanceof QuoteReply) {
            QuoteReply item1 = (QuoteReply) item;
            MessageSource source = item1.getSource();
            long groupid = 0;
            if (source instanceof OfflineMessageSource) {
                OfflineMessageSource source1 = (OfflineMessageSource) source;
                if (source1.getKind() == MessageSourceKind.GROUP) {
                    groupid = source.getTargetId();
                }
            } else if (source instanceof OnlineMessageSource) {
                OnlineMessageSource source1 = (OnlineMessageSource) source;
                if (source1.getSubject() instanceof Group) {
                    groupid = source.getTargetId();
                }
            }
            MessageChain messageChain = source.getOriginalMessage();
            messageChain.add(source);
            List<MessageDTO> list = toMessageChainDTO(messageChain);
            return new QuoteDTO(source.getIds().length == 0 ? 0 : source.getIds()[0], source.getFromId(), source.getTargetId(), groupid, list);
        } else if (item instanceof PokeMessage) {
            PokeMessage item1 = (PokeMessage) item;
            return new PokeMessageDTO(Utils.getPoke(item1.getId()));
        } else {
            return new UnknownMessageDTO();
        }
    }
}
