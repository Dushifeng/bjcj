package cn.lovezsm.bjcj.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobConf {

    private Map<String,Boolean> jobSwitch;


}
