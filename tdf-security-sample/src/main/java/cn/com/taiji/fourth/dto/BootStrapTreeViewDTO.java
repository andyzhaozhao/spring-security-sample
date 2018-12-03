package cn.com.taiji.fourth.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.util.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: BootStrapTreeViewDTO
 *
 * @Description: bootstraptree-view实体
 * @Author wanghw
 * @CreatDate 2018年1月24日
 */
@Data
@NoArgsConstructor
public class BootStrapTreeViewDTO {
    //节点ID
    //树的节点Id，区别于数据库中保存的数据Id。若要存储数据库数据的Id，添加新的Id属性；若想为节点设置路径，类中添加Path属性
    private String nodeId;
    private String id;
    //名称
    private String text;
    private BootStrapTreeState state;
    //子节点
    //子节点，可以用递归的方法读取，方法在下一章会总结
    private List<BootStrapTreeViewDTO> nodes = new ArrayList();

    /**
     * @param roots
     * @return List<ZTreeDto>
     * @throws
     * @Description: 转换为菜单树
     * @author wanghw
     * @date 2018年2月1日
     */
    public static List<BootStrapTreeViewDTO> menuTree(List<MenuDTO> roots, String ids) {
        List<BootStrapTreeViewDTO> list = new ArrayList<BootStrapTreeViewDTO>();
        if (!roots.isEmpty()) {
            for (MenuDTO menuDTO : roots) {
                System.out.println(menuDTO.getMenuName());
                BootStrapTreeViewDTO dto = getInstanceByMenuDTO(menuDTO, ids);

                list.add(dto);
            }
        }
        return list;
    }

    public static List<BootStrapTreeViewDTO> menuTree(List<MenuDTO> roots, List<String> selectedDTOIds) {
        List<BootStrapTreeViewDTO> list = new ArrayList<BootStrapTreeViewDTO>();
        if (ListUtils.isEmpty(roots)) {
            return list;
        }

        for (MenuDTO menuDTO : roots) {
            BootStrapTreeViewDTO dto = getInstanceByMenuDTO(menuDTO, selectedDTOIds);

            list.add(dto);
        }

        return list;
    }

    /**
     * @return List<ZTreeDto>
     * @throws
     * @Description: 单个菜单树转换
     * @author wanghw
     * @date 2018年2月1日
     */
    public static BootStrapTreeViewDTO getInstanceByMenuDTO(MenuDTO menuDTO, String ids) {
        if (menuDTO == null || StringUtils.isEmpty(ids)) {
            return null;
        }

        BootStrapTreeViewDTO bootStrapTreeViewDTO = new BootStrapTreeViewDTO();

        if (menuDTO.getId() != null && menuDTO.getId().length() > 0) {
            bootStrapTreeViewDTO.setId(menuDTO.getId());
        }
        if (menuDTO.getId() != null && menuDTO.getId().length() > 0) {
            bootStrapTreeViewDTO.setNodeId(menuDTO.getId());
        }
        if (menuDTO.getMenuName() != null && menuDTO.getMenuName().length() > 0) {
            bootStrapTreeViewDTO.setText(menuDTO.getMenuName());
        }
        if (ids.contains(menuDTO.getId())) {
            BootStrapTreeState bootStrapTreeState = new BootStrapTreeState();
            bootStrapTreeState.setChecked(true);
            bootStrapTreeViewDTO.setState(bootStrapTreeState);
        } else {
            BootStrapTreeState bootStrapTreeState = new BootStrapTreeState();
            bootStrapTreeState.setChecked(false);
            bootStrapTreeViewDTO.setState(bootStrapTreeState);
        }

        if (!menuDTO.getChildren().isEmpty() && menuDTO.getChildren().size() > 0) {
            for (MenuDTO d : menuDTO.getChildren()) {
                bootStrapTreeViewDTO.nodes.add(getInstanceByMenuDTO(d, ids));
            }
        }

        return bootStrapTreeViewDTO;
    }

    public static BootStrapTreeViewDTO getInstanceByMenuDTO(MenuDTO menuDTO, List<String> selectedDTOIds) {
        if (menuDTO == null) {
            return null;
        }

        BootStrapTreeViewDTO bootStrapTreeViewDTO = new BootStrapTreeViewDTO();

        if (menuDTO.getId() != null && menuDTO.getId().length() > 0) {
            bootStrapTreeViewDTO.setId(menuDTO.getId());
        }
        if (menuDTO.getId() != null && menuDTO.getId().length() > 0) {
            bootStrapTreeViewDTO.setNodeId(menuDTO.getId());
        }
        if (menuDTO.getMenuName() != null && menuDTO.getMenuName().length() > 0) {
            bootStrapTreeViewDTO.setText(menuDTO.getMenuName());
        }
        if (selectedDTOIds.contains(menuDTO.getId())) {
            BootStrapTreeState bootStrapTreeState = new BootStrapTreeState();
            bootStrapTreeState.setChecked(true);
            bootStrapTreeViewDTO.setState(bootStrapTreeState);
        } else {
            BootStrapTreeState bootStrapTreeState = new BootStrapTreeState();
            bootStrapTreeState.setChecked(false);
            bootStrapTreeViewDTO.setState(bootStrapTreeState);
        }

        if (!menuDTO.getChildren().isEmpty() && menuDTO.getChildren().size() > 0) {
            for (MenuDTO d : menuDTO.getChildren()) {
                bootStrapTreeViewDTO.nodes.add(getInstanceByMenuDTO(d, selectedDTOIds));
            }
        }

        return bootStrapTreeViewDTO;
    }
}
