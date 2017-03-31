/**
 * Copyright (c) 2015 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */
package cn.jpush.api.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

/***
 * 极光推送demo
 * 
 * @author 江家雷
 * @date 2016年6月20日 上午10:28:51
 * @since
 */
public class PushExample {

    private static final Logger LOG = LoggerFactory.getLogger(PushExample.class);

    private static final String appKey = "e0fa6a25e6249a27f7b4bf18";//app端需要配置

    private static final String masterSecret = "5178e46354e94a37305ff938";//极光推送配置项

    private JPushClient jpushClient;

    private String title;

    private String content;

    public static void main(String[] args) {
        PushExample pushExample = new PushExample("shopdog 推送测试123456");
        pushExample.sendPushAll();

    }

    public PushExample(String message) {
        this.content = message;
        jpushClient = new JPushClient(masterSecret, appKey);
    }



    public PushExample(String title, String content) {
        this(content);
        this.title = title;
    }

    /**
     * 向所有人发送消息
     * 
     * @author 李光辉
     * @return
     * @since
     */
    public long sendPushAll() {
        PushPayload payload = buildPushObject_all_alias_alert(content);
        //如果目标平台为 iOS 平台 需要在 options 中通过 apns_production 字段来制定推送环境。True 表示推送生产环境，False 表示要推送开发环境； 如果不指定则为推送生产环境
        payload.resetOptionsApnsProduction(true);
        payload.resetOptionsTimeToLive(60);
        LOG.info(JSON.toJSONString(payload));
        long msgId = 0;
        try {
            PushResult result1 = jpushClient.sendPush(payload);
            LOG.info(JSON.toJSONString(result1));

        } catch (APIConnectionException e) {
            LOG.error("Connection error.Should retry later ", e);
        } catch (APIRequestException e) {
            LOG.error("Http Status: " + e.getStatus());
            LOG.error("Http Status: ", e);
        }
        return msgId;
    }


    public static PushPayload buildPushObject_all_all_alert(String content) {
        return PushPayload.alertAll(content);
    }

    /** 构建推送对象：所有平台，推送目标的别名为alias,通知的内容为content */
    public static PushPayload buildPushObject_all_alias_alert(String content) {
        
        return PushPayload.newBuilder().setPlatform(Platform.all())// 所有平台
                .setAudience(Audience.all())// 向选定人推送
                .setNotification(Notification.alert(content))// 消息内容
                .build();
    }

    /** 构建推送对象：向android平台，向目标标签tag,通知标题title,内容为content */
    public static PushPayload buildPushObject_android_tag_alertWithTitle(String tag, String title, String content) {

        return PushPayload.newBuilder().setPlatform(Platform.android()).setAudience(Audience.tag(tag)).setNotification(Notification.android(content, title, null)).build();
    }


    /*public static PushPayload buildPushObject_ios_tagAnd_alertWithExtrasAndMessage() {
        return PushPayload.newBuilder().setPlatform(Platform.ios()).setAudience(Audience.tag_and("tag1", "tag_all",""))
                .setNotification(Notification.newBuilder().addPlatformNotification(IosNotification.newBuilder().setAlert(ALERT).setBadge(5).setSound("happy.caf").addExtra("from", "JPush").build()).build()).setMessage(Message.content(MSG_CONTENT))
                .setOptions(Options.newBuilder().setApnsProduction(true).build()).build();
    }

    public static PushPayload buildPushObject_ios_audienceMore_messageWithExtras() {
        return PushPayload.newBuilder().setPlatform(Platform.android_ios()).setAudience(Audience.newBuilder().addAudienceTarget(AudienceTarget.tag("tag1", "tag2")).addAudienceTarget(AudienceTarget.alias("alias1", "alias2")).build())
                .setMessage(Message.newBuilder().setMsgContent(MSG_CONTENT).addExtra("from", "JPush").build()).build();
    }*/
     

}
