//package cn.com.taiji.fourth.web;
//
//import cn.com.taiji.fourth.dto.OauthClientDetailsDTO;
//import cn.com.taiji.fourth.service.OauthClientDetailsService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.apache.commons.lang3.RandomStringUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/oauth2")
//public class OauthClientDetailsController {
//    private static final Logger log = LoggerFactory.getLogger(OauthClientDetailsController.class);
//    @Autowired
//    private OauthClientDetailsService oauthClientDetailsService;
//    private String salt = RandomStringUtils.randomAscii(20);
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    // 跳转到应用管理列表
//    @GetMapping("/appManager")
//    public String goAppManager() {
//        log.info("coming");
//        return "oauth2/oauthClientDetails-list";
//    }
//
//    // 跳转到应用添加
//    @GetMapping("/oauthClientDetailsAdd")
//    public String addClient(Model model) {
//        OauthClientDetailsDTO dto = new OauthClientDetailsDTO();
//        model.addAttribute("OauthClientDetailsDTO", dto);
//        return "oauth2/oauthClientDetails-edit";
//    }
//
//    // 跳转到应用编辑
//    @GetMapping("/oauthClientDetailsEdit")
//    public String updateClient(Model model, @RequestParam(value = "id") String id) {
//        OauthClientDetailsDTO dto = new OauthClientDetailsDTO();
//        if (!StringUtils.isEmpty(id)) {
//            dto = this.oauthClientDetailsService.findOauthClientDetailsById(id, salt);
//        }
//        model.addAttribute("OauthClientDetailsDTO", dto);
//        return "oauth2/oauthClientDetails-edit";
//    }
//
//    // 应用前台
//    @GetMapping("/oauthClientDetailsList")
//    public String index(Model model) {
//        List<OauthClientDetailsDTO> result = oauthClientDetailsService.list(salt);
//        model.addAttribute("result", result);
//        return "index";
//    }
//}
