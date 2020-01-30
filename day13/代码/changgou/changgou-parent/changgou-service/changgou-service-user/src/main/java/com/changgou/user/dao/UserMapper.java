package com.changgou.user.dao;
import com.changgou.user.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

/****
 * @Author:admin
 * @Description:User的Dao
 * @Date 2019/6/14 0:12
 *****/
public interface UserMapper extends Mapper<User> {


    /**
     * 增加积分
     * @param points
     * @param username
     * @return
     */
    @Update(value="update tb_user set points = points+#{points} where username=#{username}")
    public int addPoints(@Param(value="points")Integer points ,@Param(value="username") String username);
}
