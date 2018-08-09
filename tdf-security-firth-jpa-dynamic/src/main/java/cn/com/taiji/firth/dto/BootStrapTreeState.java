package cn.com.taiji.firth.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 类名称：BootStrapTreeState
 * 类描述：   bootstraptree-view实体中的state状态类
 * 创建人：wanghw
 * 创建时间：2018年2月5日
 */
@Data
@NoArgsConstructor
public class BootStrapTreeState {
    //节点ID
    //树的节点Id，区别于数据库中保存的数据Id。若要存储数据库数据的Id，添加新的Id属性；若想为节点设置路径，类中添加Path属性
    public boolean checked;
    public boolean disabled;
    //名称
    public boolean expanded;
    public boolean selected;
}
