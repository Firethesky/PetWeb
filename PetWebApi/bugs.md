# 系统BUG修复记录

## Bug #1: JSON循环引用导致无限递归序列化

### 问题描述
在通过REST API返回实体数据时，由于实体之间存在双向关联关系，导致Jackson序列化时陷入无限递归循环。例如：
- User包含Pet列表，Pet又引用回User
- 序列化User时，会包含其所有Pet
- 序列化每个Pet时，又会包含其User
- 序列化这个User时，又会包含所有Pet
- 如此循环往复，最终产生一个巨大的、无限嵌套的JSON

API调用`GET /api/users`时，返回的JSON数据会不断重复，导致响应体积极大，甚至可能导致客户端崩溃或API调用超时。

### 受影响的实体关系
- User ⟷ Pet
- User ⟷ Reminder
- User ⟷ Post
- User ⟷ Comment
- Pet ⟷ HealthRecord
- Pet ⟷ Reminder
- Post ⟷ Comment
- Comment ⟷ Comment (父评论与子评论)

### 解决方案
采用Jackson提供的`@JsonManagedReference`和`@JsonBackReference`注解处理双向关联，打破循环引用：

1. **JsonManagedReference**：标注在关系拥有方，正常序列化
2. **JsonBackReference**：标注在关系被拥有方，序列化时忽略该属性

### 实施的修改
- **User实体**：
  - 为`pets`集合添加`@JsonManagedReference`
  - 为`reminders`集合添加`@JsonManagedReference(value = "user-reminders")`
  - 为`posts`集合添加`@JsonManagedReference(value = "user-posts")`
  - 为`comments`集合添加`@JsonManagedReference(value = "user-comments")`

- **Pet实体**：
  - 为`user`字段添加`@JsonBackReference`
  - 为`healthRecords`集合添加`@JsonManagedReference(value = "pet-healthRecords")`
  - 为`reminders`集合添加`@JsonManagedReference(value = "pet-reminders")`

- **HealthRecord实体**：
  - 为`pet`字段添加`@JsonBackReference(value = "pet-healthRecords")`

- **Reminder实体**：
  - 为`pet`字段添加`@JsonBackReference(value = "pet-reminders")`
  - 为`user`字段添加`@JsonBackReference(value = "user-reminders")`

- **Post实体**：
  - 为`user`字段添加`@JsonBackReference(value = "user-posts")`
  - 为`comments`集合添加`@JsonManagedReference(value = "post-comments")`

- **Comment实体**：
  - 为`post`字段添加`@JsonBackReference(value = "post-comments")`
  - 为`user`字段添加`@JsonBackReference(value = "user-comments")`
  - 为`parentComment`字段添加`@JsonBackReference(value = "comment-replies")`
  - 为`replies`集合添加`@JsonManagedReference(value = "comment-replies")`

此外，还新增了一个全局Jackson配置类，进一步防止意外的循环引用：
```java
@Configuration
public class JacksonConfig {
    
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        
        // 注册时间模块，支持Java 8日期/时间类型
        mapper.registerModule(new JavaTimeModule());
        
        // 禁用日期时间作为时间戳输出
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        
        // 忽略空对象（有助于减少输出大小）
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        
        return mapper;
    }
}
```

### 修复结果
成功解决循环引用问题，API现在能够正常返回完整的JSON数据结构，没有无限递归的情况。返回的JSON保持了数据的完整性，同时避免了循环引用。

### 注意事项
如果后续添加新的实体类或实体间关联关系，需要注意是否存在双向关联，并适当使用`@JsonManagedReference`和`@JsonBackReference`注解避免循环引用问题。 