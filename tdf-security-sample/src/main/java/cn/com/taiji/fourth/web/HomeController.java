package cn.com.taiji.fourth.web;

import cn.com.taiji.fourth.domain.*;
import cn.com.taiji.fourth.domain.Menu;
import cn.com.taiji.fourth.dto.MenuDTO;
import cn.com.taiji.fourth.dto.UserDTO;
import cn.com.taiji.fourth.security.TaijiSecurityMetadataSource;
import cn.com.taiji.fourth.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Random;


/**
 * @author chixue
 */
@Controller
public class HomeController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private HttpSession session;

    /**
     * 跳转至主页,并加载root级别菜单并返回至menus集合对象中
     *
     * @return
     */
    @GetMapping(value= {"/","/index"})
    public String home(Model model) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user != null) {
            List<Menu> menus = menuService.findRootByAuthorization(user.getRoles(),"006141059be045189b06265fa49552e8");
            model.addAttribute("rootMenus", menus);
        }
//        List<MenuDTO> menus = menuService.findMenuByMark("portal");
//        model.addAttribute("rootMenus", menus);
        model.addAttribute("avatar", user.getAvatar());
        return "index";//semantic
    }

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @GetMapping(value = "/admin")
    public String admin(Model model) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        List<MenuDTO> menus = menuService.findMenuByMark("sys");
        model.addAttribute("rootMenus", menus);
        model.addAttribute("avatar", user.getAvatar());
        return "admin";
    }


    @GetMapping(value = "/reload")
    @ResponseBody
    public String reload(Model model) {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
        TaijiSecurityMetadataSource cs = (TaijiSecurityMetadataSource) ctx.getBean("taijiSecurityMetadataSource", TaijiSecurityMetadataSource.class);
        cs.loadResourceDefine(true);
        return "刷新成功";
    }


    /**
     * 授权树加载
     *
     * @param model
     * @return
     */
    @GetMapping(value = "/authorization-menu")
    public String authorizationMenu(Model model) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user != null) {
            List<MenuDTO> menus = menuService.findRootByAuthorization(user.getRoles());
            model.addAttribute("menuTree", menus);
        }
        return "index";//semantic
    }

    /**
     * 按菜单节点信息加载menu
     *
     * @param model
     * @param rootId
     * @return
     */
    @GetMapping("/authorization-menu-byRoot")
    public String authorizationMenuByRootId(Model model, @RequestParam(value = "rootId", required = false, defaultValue = "") String rootId) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            List<MenuDTO> menus = menuService.findMenuByRootId(rootId);
            model.addAttribute("menus", menus);
        }
        return "layout/left";//semantic
    }

    /**
     * @param model
     * @param mark
     * @return String
     * @throws
     * @Description: 根据标示加载该标示信息下的授权信息集合
     * @author chixue
     * @date 2016年5月9日
     */
    @GetMapping("/authorization-menu-mark")
    public String authorizationMenuByMark(Model model, @RequestParam(value = "mark", required = false, defaultValue = "") String mark) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            List<MenuDTO> menus = menuService.findMenuByMark(mark);
            model.addAttribute("menus", menus);
        }
        return "layout/left";
    }


    /**
     * @param user
     * @return
     */
    // for 403 access denied page
    @GetMapping("/403")
    public String accesssDenied(Model model, Principal user) {
        if (user == null) {
            model.addAttribute("msg", "Hi you do not have permission to access this page!");
        } else {
            model.addAttribute("msg", "Hi " + user.getName()
                    + ", You do not have permission to access this page");
        }
        model.addAttribute("authentication",user);
        return "403";
    }

    /**
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "/image")
    public String image(HttpServletRequest request, HttpServletResponse response) {

        BufferedImage img = new BufferedImage(68, 22, BufferedImage.TYPE_INT_RGB);
        // 得到该图片的绘图对象    
        Graphics g = img.getGraphics();
        Random r = new Random();
        Color c = new Color(255, 255, 255);
        g.setColor(c);
        // 填充整个图片的颜色    
        g.fillRect(0, 0, 68, 22);
        // 向图片中输出数字和字母    
        StringBuffer sb = new StringBuffer();
        char[] ch = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        int index, len = ch.length;
        for (int i = 0; i < 4; i++) {
            index = r.nextInt(len);
            g.setColor(new Color(r.nextInt(88), r.nextInt(188), r.nextInt
                    (255)));
            g.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 22));
            // 输出的  字体和大小                      
            g.drawString("" + ch[index], (i * 15) + 3, 18);
            //写什么数字，在图片 的什么位置画    
            sb.append(ch[index]);
        }
        request.getSession().setAttribute("j_captcha", sb.toString());
        try {
            response.setHeader("content-type", "image/jpeg");
            response.setContentType("image/jpeg");
            response.setHeader("Param", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setIntHeader("Expries", -1);

            ImageIO.write(img, "jpg", response.getOutputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}