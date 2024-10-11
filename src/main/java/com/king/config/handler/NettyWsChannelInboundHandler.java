package com.king.config.handler;

import com.king.config.user.UserChannelRel;
import com.king.model.entity.ChatMsg;
import com.king.model.entity.DataContent;
import com.king.model.enums.MsgActionEnum;
import com.king.model.po.ChatFriendMsgLogs;
import com.king.service.ChatFriendMsgLogsService;
import com.king.service.impl.ChatFriendMsgLogsServiceImpl;
import com.king.utils.JsonUtils;
import com.king.utils.SpringBeanUtil;
import com.king.utils.ValidationUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.king.config.user.UserChannelRel.users;

/**
 * @author King
 * @version 1.0
 * @description 处理消息的handler TextWebSocketFrame： 在netty中，是用于为websocket专门处理文本的对象，frame是消息的载体
 * SimpleChannelInboundHandler：	对于请求来讲 ，相当于 【入站，入境】
 * @date 2024/10/9 13:56
 */
@Slf4j
public class NettyWsChannelInboundHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        // 获得channel
        Channel currentChannel = ctx.channel();
        // 获取客户端传输过来的消息
        String content = msg.text();
        //转成实体类对象
        DataContent dataContent = JsonUtils.jsonToPojo(content, DataContent.class);
        if (ObjectUtils.isEmpty(dataContent)) {
            return;
        }
        Integer action = dataContent.getAction();
        //判断消息类型，根据不同的类型来处理不同的业务
        if (MsgActionEnum.CONNECT.type.equals(action)) {
            //第一次(或重连)初始化连接
            //当websocket 第一次open的时候，初始化channel，把用的channel和userid关联起来
            String sendId = dataContent.getChatMsg().getSendId();
            UserChannelRel.put(sendId, currentChannel);
        } else if (MsgActionEnum.CHAT.type.equals(action)) {
            //聊天消息
            //把聊天记录保存到数据库，同时标记消息的签收状态[未签收]
            ChatFriendMsgLogsService chatFriendMsgLogsService
                    = (ChatFriendMsgLogsService) SpringBeanUtil
                    .getBean("chatFriendMsgLogsServiceImpl");
            //解析消息体
            ChatMsg chatMsg = dataContent.getChatMsg();
            String sendId = chatMsg.getSendId();
            String receiveId = chatMsg.getReceiveId();
            String msgContent = chatMsg.getMsgContent();
            Long userId = chatMsg.getUserId();
            // 保存消息到数据库，并且标记为 未签收
            ChatFriendMsgLogs chatFriendMsgLogs = new ChatFriendMsgLogs();
            chatFriendMsgLogs.setSendId(sendId);
            chatFriendMsgLogs.setReceiveId(receiveId);
            chatFriendMsgLogs.setMsgContent(msgContent);
            chatFriendMsgLogs.setUserId(userId);
            chatFriendMsgLogs.setMsgType(dataContent.getMsgType());
            ChatFriendMsgLogs logs = chatFriendMsgLogsService.saveMsgLogs(chatFriendMsgLogs);
            //获取消息id设置进消息体中
            Long msgId = logs.getId();
            chatMsg.setMsgId(msgId.toString());
            chatMsg.setSendTime(new Date());
            //重新封装消息体
            DataContent dataContentMsg = new DataContent();
            dataContentMsg.setChatMsg(chatMsg);
            dataContentMsg.setAction(MsgActionEnum.CHAT.type);
            dataContentMsg.setMsgType(dataContent.getMsgType());
            //给自己发送消息发送成功的消息
            //获取自己的channel
            Channel sendChannel = UserChannelRel.get(sendId);
            //发送消息
            sendChannel.writeAndFlush(
                    new TextWebSocketFrame(
                            JsonUtils.objectToJson(dataContentMsg)));
            //给接收方发送消息
            //获取接收方的channel
            Channel receiveChannel = UserChannelRel.get(receiveId);
            if (ObjectUtils.isEmpty(receiveChannel)) {
                // channel为空代表用户离线，添加离线消息记录
                log.info("用户id为{}的用户已离线", receiveId);
                chatFriendMsgLogsService.updateOfflineStatus(msgId);
            } else {
                //从在现用户组里面获取channel
                Channel channel = users.find(receiveChannel.id());
                if (ObjectUtils.isEmpty(channel)) {
                    //用户离线,推送消息,添加离线消息记录
                    log.info("用户id为{}的用户已离线", receiveId);
                    chatFriendMsgLogsService.updateOfflineStatus(msgId);
                } else {
                    //用户在线,发送实时消息
                    channel.writeAndFlush(
                            new TextWebSocketFrame(
                                    JsonUtils.objectToJson(dataContentMsg)));
                }
            }
        } else if (MsgActionEnum.SIGNED.type.equals(action)) {
            //消息签收
            log.info("消息签收...");
            // 扩展字段在signed类型的消息中，代表需要去签收的消息id，逗号间隔
            String extend = dataContent.getExtend();
            //获取签收消息的id数组
            String[] signIds = extend.split(",");
            //将数组转为集合，并将非空的塞入进去
            List<String> signIdList = new ArrayList<>();
            for (String signId : signIds) {
                if (StringUtils.isNotBlank(signId)) {
                    signIdList.add(signId);
                }
            }
            if (ValidationUtils.isListNonEmpty(signIdList)) {
                //签收消息类型，针对具体的消息进行签收，修改数据库中对应消息的签收状态[已签收]
                //批量签收
                ChatFriendMsgLogsServiceImpl chatMsgLogsService = (ChatFriendMsgLogsServiceImpl) SpringBeanUtil.getBean("chatFriendMsgLogsServiceImpl");
                chatMsgLogsService.batchSignMsgs(signIdList);
            }
        } else if (MsgActionEnum.KEEPALIVE.type.equals(action)) {
            //客户端保持心跳
            log.info("收到来自channel为[" + currentChannel + "]的心跳包...");
        } else if (MsgActionEnum.PULL_FRIEND.type.equals(action)) {
            //拉取好友入群
        } else if (MsgActionEnum.FRIEND_REQUEST.type.equals(action)) {
            //好友申请
        } else if (MsgActionEnum.GROUP_MSG.type.equals(action)) {
            //群聊消息
        }
    }

    /**
     * 当客户端连接服务端之后（打开连接）
     * 获取客户端的channle，并且放到ChannelGroup中去进行管理
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        users.add(ctx.channel());
        log.info(" netty 获得连接.....	");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        String channelId = ctx.channel().id().asShortText();
        // 当触发handlerRemoved，ChannelGroup会自动移除对应客户端的channel
        users.remove(ctx.channel());
        log.info("客户端被移除，channelId为：" + channelId);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        // 发生异常之后关闭连接（关闭channel），随后从ChannelGroup中移除
        ctx.channel().close();
        users.remove(ctx.channel());
        log.info(" netty 异常了...... ");
    }
}
