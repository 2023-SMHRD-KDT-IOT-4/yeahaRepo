package com.smhrd.yeaha.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.smhrd.yeaha.model.YeahaUser;

@Mapper
public interface YeahaUserMapper {

	@Insert("INSERT INTO tbl_user (user_email, user_name, user_gender, user_age, user_phone, connected_at) " +
	        "VALUES (#{email}, #{name}, #{gender}, #{ageCal}, #{phone}, NOW())")
	void insertUser(YeahaUser user);

	@Update("UPDATE tbl_user SET user_name = #{name}, user_age = #{ageCal}, " +
	        "user_gender = #{gender}, user_phone = #{phone}, connected_at = NOW() " +
	        "WHERE user_email = #{email}")
	void updateUser(YeahaUser user);

    @Select("SELECT * FROM tbl_user WHERE user_email = #{user_email}")
    YeahaUser selectUserByEmail(String user_email);
}
