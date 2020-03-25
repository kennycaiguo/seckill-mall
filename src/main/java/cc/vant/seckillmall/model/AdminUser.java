package cc.vant.seckillmall.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "admin_user")
public class AdminUser {
    @TableId(value = "admin_user_id", type = IdType.AUTO)
    private Integer adminUserId;

    @TableField(value = "user_name")
    private String userName;

    @TableField(value = "password")
    private String password;

    /**
     * 0代表禁用，1代表有效
     */
    @TableField(value = "status")
    private Boolean status;
}