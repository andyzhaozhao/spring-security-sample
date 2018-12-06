package cn.com.taiji.web;

import cn.com.taiji.config.SaltProperties;
import cn.com.taiji.dto.UserDTO;
import cn.com.taiji.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 类名称：UserController 类描述： 创建人：dourl 创建时间：2017年10月26日 上午10:09:55
 */
@Controller
@RequestMapping("/sys")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SaltProperties saltProperties;

    @GetMapping("/user-list")
    public String userList() {
        return "sys/user-list";
    }

    @GetMapping("/disable-user-list")
    public String disableUserList(Model model) {
        return "sys/user-disable-list";
    }

    @GetMapping("/user-add")
    public String userAdd(Model model) {
        UserDTO dto = new UserDTO();
        String salt = saltProperties.getSalt();
        dto.generateToken(salt);
        model.addAttribute("userDto", dto);
        return "sys/user-edit";
    }

    @GetMapping("/user-edit")
    public String userEdit(Model model, @RequestParam("id") String id) {
        String salt = saltProperties.getSalt();
        UserDTO userDTO = userService.findUserById(id, salt);
        model.addAttribute("userDto",userDTO) ;
        return "sys/user-edit";
    }

    @GetMapping("/user-modify-pwd")
    public String userPwdEdit(Model model, @RequestParam("id") String id) {
        String salt = saltProperties.getSalt();
        model.addAttribute("userDto", userService.findUserById(id, salt));
        return "sys/user-modify-pwd";
    }

    @GetMapping("/user-upload-excel")
    public String fileUpload() {
        return "sys/user-upload-excel-file";
    }

    @GetMapping("/user-upload-avatar")
    public String userUploadAvatar(Model model, @RequestParam("id") String id) {
        model.addAttribute("userDto", userService.findUserById(id, saltProperties.getSalt()));
        return "sys/user-upload-avatar";
    }

    @GetMapping("/get-user-avatar")
    public String getUserAvatar(Model model, @RequestParam("id") String id) {
        model.addAttribute("userId", id);
        return "sys/user-avatar";
    }

    @GetMapping("/user-setting")
    public String personalSettings(Model model, @RequestParam("id") String id) {
        String salt = saltProperties.getSalt();
        model.addAttribute("userDto", userService.findUserById(id, salt));
        return "sys/user-setting";//semantic
    }
}