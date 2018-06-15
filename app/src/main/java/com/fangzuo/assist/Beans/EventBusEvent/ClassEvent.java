package com.fangzuo.assist.Beans.EventBusEvent;

/**
 * Created by 王璐阳 on 2017/11/30.
 */

public class ClassEvent {
    public final String Msg;
    public final Object postEvent;

    public ClassEvent(String msg, Object postEvent) {
        Msg = msg;
        this.postEvent = postEvent;
    }
}
