package cn.com.taiji.fourth.web;import cn.com.taiji.fourth.config.SaltProperties;import cn.com.taiji.fourth.dto.MenuDTO;import cn.com.taiji.fourth.service.MenuService;import org.slf4j.Logger;import org.slf4j.LoggerFactory;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Controller;import org.springframework.ui.Model;import org.springframework.web.bind.annotation.GetMapping;import org.springframework.web.bind.annotation.RequestMapping;import org.springframework.web.bind.annotation.RequestParam;@Controller@RequestMapping("/sys")public class MenuController {    private static final Logger log = LoggerFactory.getLogger(MenuController.class);    @Autowired    private MenuService menuService;    @Autowired    private SaltProperties saltProperties;    @GetMapping("/menu-list")    public String menuList() {        log.debug("in menuList");        return "sys/menu-list";    }    @GetMapping("/menu-tree")    public String menuSearchTree() {        log.debug("in menuSearchTree");        return "sys/menu-tree";    }    @GetMapping("/menu-edit")    public String menuEditById(Model model, @RequestParam("id") String id) {        log.debug("in menuEditById");        String salt = saltProperties.getSalt();        model.addAttribute("menuDto", menuService.findMenuById(id, salt));        return "sys/menu-edit";    }    @GetMapping("/menu-check")    public String menuTree(Model model) {        log.debug("in menuTree");        return "sys/menu-check";    }    /**     * @param model     * @param parentId     * @return     */    @GetMapping("/menu-add")    public String menuAdd(Model model, @RequestParam(value = "parentId", required = false) String parentId,                          @RequestParam(value = "parentName", required = false) String parentName) {        MenuDTO dto = new MenuDTO();        if (parentId != null) {            dto.setParentId(parentId);            dto.setParentName(parentName);        }        String salt = saltProperties.getSalt();        dto.generateToken(salt);        model.addAttribute("menuDto", dto);        return "sys/menu-edit";    }}