package cn.com.taiji.domain;


import lombok.Data;

/**
 * User 实体
 */
@Data
public class User {
    // 用户的唯一标识
    private Long id;
    private String name;
    private Integer age;

    protected User() {
        // JPA 的规范要求无参构造函数；设为 protected 防止直接使用
    }

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public User(Long id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return String.format(
                "User[id=%d, name='%s', age='%d']",
                id, name, age);
    }
}
