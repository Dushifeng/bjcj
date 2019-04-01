package cn.lovezsm.bjcj.event;

import org.springframework.context.ApplicationEvent;

public class UpdateConfigEvent extends ApplicationEvent {

    public UpdateConfigEvent(Object source) {
        super(source);
    }
}
