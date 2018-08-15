package cn.com.taiji.fourth.service.impl;

import cn.com.taiji.fourth.domain.OauthClientDetails;
import cn.com.taiji.fourth.dto.OauthClientDetailsDTO;
import cn.com.taiji.fourth.repository.OauthClientDetailsRepository;
import cn.com.taiji.fourth.service.OauthClientDetailsService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类名称： OauthClientDetailsService 类描述： 创建人： 创建时间：Mar 14, 2018 12:06:41 PM
 */
@Service
public class OauthClientDetailsServiceImpl implements OauthClientDetailsService{
    private static final Logger log = LoggerFactory.getLogger(OauthClientDetailsServiceImpl.class);

    @Autowired
    private OauthClientDetailsRepository oauthClientDetailsRepository;

    /**
     * 按主键ID查询信息
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public OauthClientDetailsDTO findOauthClientDetailsById(String id, String salt) {
        OauthClientDetails oauthClientDetails = this.oauthClientDetailsRepository.findOne(id);
        return oauthClientDetails2Dto(oauthClientDetails, salt);
    }

    /**
     * 查询信息
     *
     * @param searchParameters 查询参数的map集合
     * @return 查询的结果, map类型 total:总条数 oauthClientDetailss:查询结果list集合
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map getPage(final Map searchParameters, String salt) {
        Page<OauthClientDetails> pageContent;
        int page = 0;
        try {
            page = Integer.parseInt(searchParameters.get("page").toString()) - 1;
        } catch (Exception e) {

        }
        int pageSize = 10;
        try {
            pageSize = Integer.parseInt(searchParameters.get("pageSize").toString());
        } catch (Exception e) {
        }
        if (pageSize < 1)
            pageSize = 1;
        if (pageSize > 100)
            pageSize = 100;
        List<Map> orderMaps = (List<Map>) searchParameters.get("sort");
        List<Order> orders = new ArrayList<Order>();
        if (orderMaps != null) {
            for (Map m : orderMaps) {
                if (m.get("field") == null)
                    continue;
                String field = m.get("field").toString();
                if (!StringUtils.isEmpty(field)) {
                    String dir = m.get("dir").toString();
                    if ("DESC".equalsIgnoreCase(dir)) {
                        orders.add(new Order(Direction.DESC, field));
                    } else {
                        orders.add(new Order(Direction.ASC, field));
                    }
                }
            }
        }
        PageRequest pageable;
        if (orders.size() > 0) {
            pageable = new PageRequest(page, pageSize, new Sort(orders));
        } else {
            pageable = new PageRequest(page, pageSize);
        }
        Map filter = (Map) searchParameters.get("filter");
        if (filter != null) {
            String logic = (String) filter.get("logic");
            final List<Map> filters = (List<Map>) filter.get("filters");
            Specification<OauthClientDetails> spec = new Specification<OauthClientDetails>() {
                @Override
                public Predicate toPredicate(Root<OauthClientDetails> root, CriteriaQuery<?> query,
                                             CriteriaBuilder cb) {
                    List<Predicate> pl = new ArrayList<Predicate>();
                    for (Map f : filters) {
                        String field = ((String) f.get("field")).trim();
                        String value = ((String) f.get("value")).trim();
                        // TODO should change with operator, and according to
                        // value

                        if ("additionalInformation".equalsIgnoreCase(field)) {
                            if (value.length() > 0)
                                pl.add(cb.like(root.<String>get(field), value + "%"));
                        } else if ("authorizedGrantTypes".equalsIgnoreCase(field)) {
                            if (value.length() > 0)
                                pl.add(cb.like(root.<String>get(field), value + "%"));
                        }
                        //pl.add(cb.equal(root.<Integer> get("flag"),1));
                    }
                    return cb.and(pl.toArray(new Predicate[0]));
                }
            };
            pageContent = oauthClientDetailsRepository.findAll(spec, pageable);
        } else {
            Specification<OauthClientDetails> spec = new Specification<OauthClientDetails>() {
                public Predicate toPredicate(Root<OauthClientDetails> root, CriteriaQuery<?> query,
                                             CriteriaBuilder cb) {
                    List<Predicate> list = new ArrayList<Predicate>();
                    // 查询出未删除的
                    // list.add(cb.equal(root.<Integer> get("flag"),1));
                    return cb.and(list.toArray(new Predicate[0]));
                }
            };
            pageContent = oauthClientDetailsRepository.findAll(spec, pageable);
        }

        Map map = new HashMap();
        map.put("total", pageContent.getTotalElements());
        map.put("list", oauthClientDetailsPage2Dto(pageContent, salt));
        return map;
    }

    /**
     * @param page
     * @param salt
     * @return List<OauthClientDetailsDTO>
     * @throws @author
     * @Description: 实体对象集合转换DTO对象集合
     * @date Mar 14, 2018 12:06:41 PM
     */
    public List<OauthClientDetailsDTO> oauthClientDetailsPage2Dto(Page<OauthClientDetails> page, String salt) {
        List<OauthClientDetailsDTO> result = new ArrayList<OauthClientDetailsDTO>();
        List<OauthClientDetails> all = page.getContent();
        for (OauthClientDetails oauthClientDetails : all) {
            OauthClientDetailsDTO dto = new OauthClientDetailsDTO();
            BeanUtils.copyProperties(oauthClientDetails, dto);
            dto.generateToken(salt);
            result.add(dto);
        }
        return result;
    }

    /**
     * @param salt
     * @return List<OauthClientDetailsDTO>
     * @throws @author
     * @Description: 实体对象集合转换DTO对象集合
     * @date Mar 14, 2018 12:06:41 PM
     */
    public List<OauthClientDetailsDTO> oauthClientDetailsList2Dto(List<OauthClientDetails> oauthClientDetailsList,
                                                                  String salt) {
        List<OauthClientDetailsDTO> result = new ArrayList<OauthClientDetailsDTO>();
        for (OauthClientDetails oauthClientDetails : oauthClientDetailsList) {
            result.add(oauthClientDetails2Dto(oauthClientDetails, salt));
        }
        return result;
    }

    /**
     * 内部转换
     *
     * @param oauthClientDetails
     * @param salt
     * @return
     */
    private OauthClientDetailsDTO oauthClientDetails2Dto(OauthClientDetails oauthClientDetails, String salt) {
        OauthClientDetailsDTO dto = new OauthClientDetailsDTO();
        BeanUtils.copyProperties(oauthClientDetails, dto);
        dto.generateToken(salt);
        return dto;
    }

    /**
     * @return List<OauthClientDetailsDTO>
     * @throws @author dourl
     * @Description: 简要进行方法说明，并对基础数据类型的参数和返回值加以说明
     * @date 2018年3月29日
     */
    public List<OauthClientDetailsDTO> list(String salt) {
        List<OauthClientDetailsDTO> result = new ArrayList<>();
        List<OauthClientDetails> list = this.oauthClientDetailsRepository.findAll();

        for (OauthClientDetails client : list) {
            OauthClientDetailsDTO dto = oauthClientDetails2Dto(client, salt);
            if (null != dto) {
                result.add(dto);
            }
        }
        return result;
    }

    /**
     * 删除信息
     *
     * @param id
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteOauthClientDetails(String id) {
        this.oauthClientDetailsRepository.delete(id);
    }

    /**
     * 添加信息
     *
     * @param dto
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void add(OauthClientDetailsDTO dto) {
        if (null != dto) {
            // 新增
            OauthClientDetails oauthClientDetails = new OauthClientDetails();
            // dto.setId(id);
            //dto.setFlag(1);// 初始化
            BeanUtils.copyProperties(dto, oauthClientDetails);
            this.oauthClientDetailsRepository.saveAndFlush(oauthClientDetails);
        }
    }
}
