//package cn.com.taiji.fourth.service;
//
//import cn.com.taiji.fourth.dto.OauthClientDetailsDTO;
//
//import java.util.List;
//import java.util.Map;
//
//public interface OauthClientDetailsService {
//    /**
//     * 按主键ID查询信息
//     */
//    OauthClientDetailsDTO findOauthClientDetailsById(String id, String salt);
//
//    /**
//     * 查询信息
//     *
//     * @param searchParameters 查询参数的map集合
//     * @return 查询的结果, map类型 total:总条数 oauthClientDetailss:查询结果list集合
//     */
//    Map getPage(final Map searchParameters, String salt);
//
//
//    /**
//     * @return List<OauthClientDetailsDTO>
//     * @throws @author dourl
//     * @Description: 简要进行方法说明，并对基础数据类型的参数和返回值加以说明
//     * @date 2018年3月29日
//     */
//    List<OauthClientDetailsDTO> list(String salt);
//
//    /**
//     * 删除信息
//     *
//     * @param id
//     */
//    void deleteOauthClientDetails(String id);
//
//    /**
//     * 添加信息
//     *
//     * @param dto
//     */
//    void add(OauthClientDetailsDTO dto);
//}
