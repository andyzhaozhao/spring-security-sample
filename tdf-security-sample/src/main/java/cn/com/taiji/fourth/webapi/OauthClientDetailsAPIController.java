//package cn.com.taiji.fourth.webapi;
//
//import cn.com.taiji.fourth.dto.OauthClientDetailsDTO;
//import cn.com.taiji.fourth.dto.ResultDTO;
//import cn.com.taiji.fourth.service.OauthClientDetailsService;
//import com.fasterxml.jackson.core.JsonParseException;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.apache.commons.lang3.RandomStringUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/oauth2")
//public class OauthClientDetailsAPIController {
//    private static final Logger log = LoggerFactory.getLogger(OauthClientDetailsAPIController.class);
//    @Autowired
//    private OauthClientDetailsService oauthClientDetailsService;
//    private String salt = RandomStringUtils.randomAscii(20);
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    // 应用添加
//    @PostMapping("/oauthClientDetailsAdd")
//    public String addClient(Model model, @RequestParam(value = "models") String models) {
//        OauthClientDetailsDTO dto = new OauthClientDetailsDTO();
//        if (models != null && models.length() > 0) {
//            try {
//                dto = objectMapper.readValue(models, new TypeReference<OauthClientDetailsDTO>() {
//                });
//
//                this.oauthClientDetailsService.add(dto);
//            } catch (JsonParseException e) {
//                log.error("JsonParseException{}:", e.getMessage());
//                model.addAttribute("error", e.getMessage());
//            } catch (IOException e) {
//                log.error("IOException{}:", e.getMessage());
//                model.addAttribute("error", e.getMessage());
//            }
//        }
//        return "oauth2/oauthClientDetails-list";
//    }
//
//    @PostMapping("/oauthClientDetails-list")
//    public ResultDTO userList(Model model,
//                              @RequestParam(value = "models", required = false) String models) {
//        Map searchParameters = new HashMap();
//        if (models != null && models.length() > 0) {
//            try {
//                searchParameters = objectMapper.readValue(models, new TypeReference<Map>() {
//                });
//            } catch (JsonParseException e) {
//                log.error("JsonParseException{}:", e.getMessage());
//            } catch (JsonMappingException e) {
//                log.error("JsonMappingException{}:", e.getMessage());
//            } catch (IOException e) {
//                log.error("IOException{}:", e.getMessage());
//            }
//        }
//
//        Map map = oauthClientDetailsService.getPage(searchParameters, salt);
//        ResultDTO resultDTO = new ResultDTO();
//        resultDTO.setObj(map);
//        return resultDTO;
//    }
//
//    // 应用删除
//    @PostMapping("/oauthClientDetailsDelete")
//    public String deleteClient(Model model, @RequestParam(value = "clientId") String id) {
//
//        if (!StringUtils.isEmpty(id)) {
//            this.oauthClientDetailsService.deleteOauthClientDetails(id);
//        }
//        return "oauth2/oauthClientDetails-list";
//    }
//}
