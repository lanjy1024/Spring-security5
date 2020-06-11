package com.example.demo.vo;

import com.example.demo.po.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * @author：lanjy
 * @date：2020/6/8
 * @description：
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {

    private String username;
    private String password;
    private String rolename;

}
