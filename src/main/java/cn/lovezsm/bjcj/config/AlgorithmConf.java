package cn.lovezsm.bjcj.config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
@ConfigurationProperties("algorithm")
public class AlgorithmConf {
    private int k;
    private int sliding_window_time;
    private int sliding_step_time;

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public int getSliding_window_time() {
        return sliding_window_time;
    }

    public void setSliding_window_time(int sliding_window_time) {
        this.sliding_window_time = sliding_window_time;
    }

    public int getSliding_step_time() {
        return sliding_step_time;
    }

    public void setSliding_step_time(int sliding_step_time) {
        this.sliding_step_time = sliding_step_time;
    }
}
