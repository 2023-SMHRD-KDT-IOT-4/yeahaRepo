package com.smhrd.yeaha.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.smhrd.yeaha.model.YeahaUser;

@Mapper
public interface YeahaUserMapper {

	@Insert("INSERT INTO tbl_user (user_email, user_name, user_gender, user_age, user_phone, connected_at) " +
	        "VALUES (#{user_email}, #{user_name}, #{user_genderVal}, #{user_ageCal}, #{user_phone}, NOW())")
	void insertUser(YeahaUser user);

	@Update("UPDATE tbl_user SET user_name = #{user_name}, user_age = #{user_ageCal}, " +
	        "user_gender = #{user_genderVal}, user_phone = #{user_phone}, connected_at = NOW() " +
	        "WHERE user_email = #{user_email}")
	void updateUser(YeahaUser user);

    @Select("SELECT * FROM tbl_user WHERE user_email = #{user_email}")
    YeahaUser selectUserByEmail(String user_email);
}
