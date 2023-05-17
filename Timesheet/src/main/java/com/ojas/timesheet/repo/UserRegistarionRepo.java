
package com.ojas.timesheet.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.ojas.timesheet.entity.UserRegistarion;

@Repository
public interface UserRegistarionRepo extends JpaRepository<UserRegistarion, Integer> {

	/*
	 * @Query(" from Admin where admin_email= :email and admin_password= :password")
	 * Optional<Admin> find(@Param("email") String uname, @Param("password") String
	 * upass);
	 */

	Optional<UserRegistarion> findByEmail(String email);

	@Query("from UserRegistarion  where user_name= :userName")
	Optional<UserRegistarion> findByUserName(String userName);

	@Query(" from UserRegistarion  where phoneNumber= :phoneNumber")
	Optional<UserRegistarion> findByphoneNumber(double phoneNumber);

}
