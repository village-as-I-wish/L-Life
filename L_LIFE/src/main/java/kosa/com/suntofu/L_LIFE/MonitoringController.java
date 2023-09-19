package kosa.com.suntofu.L_LIFE;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MonitoringController {

    @ResponseBody
    @RequestMapping("/")
    public String doMain(){
        return "[Monitoring] Hello";
    }
    @ResponseBody
    @RequestMapping("/endpoint1")
    public String doEndPoint1(){
        return "[Monitoring] EndPoint 1";
    }

    @ResponseBody
    @RequestMapping("/endpoint2")
    public String doEndPoint2(){
        return "[Monitoring] EndPoint 2";
    }

}
