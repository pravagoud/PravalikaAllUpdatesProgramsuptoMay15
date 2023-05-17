package com.ojas.timesheet.repo;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ojas.timesheet.entity.TimeSheet;

@Repository
public interface TimeSheetRepo extends JpaRepository<TimeSheet, Integer> {

	@Query("SELECT t FROM TimeSheet t WHERE t.startDate >= :sDate AND t.endDate<= :eDate AND t.status = :status")
	List<TimeSheet> findByStartDateBeforeAndEndDateAfter(@Param("sDate") Date sDate, @Param("eDate") Date eDate,
			@Param("status") String status);

	List<TimeSheet> findByEmpId(int empId);

	@Query("SELECT t FROM TimeSheet t WHERE t.startDate >= :sDate AND t.endDate<= :eDate")
	List<TimeSheet> findByStartDateAndEndDate(Date sDate, Date eDate);

	@Query(value = "SELECT t.id,t.client_name,t.create_date,t.domain,t.email,t.emp_id,t.emp_name,date(t.end_date) as end_date,t.modify_date,t.phone_no,date(t.start_date) as start_date,t.status,t.shift,t.timing,t.feedback,t.ipaddress,t.longitude,t.latitude,t.location,t.locdetails,(select group_concat(ff.id) from file_data ff where ff.timesheet_id=t.id) as ttid,(select group_concat(ff.file_name) from file_data ff where ff.timesheet_id=t.id) as ttfn FROM timesheet t WHERE t.start_date >= ?1 AND t.end_date<= ?2 AND t.status = ?3", nativeQuery = true)
	List<TimeSheet> findByStartDateBeforeAndEndDateAfterOpt(@Param("sDate") Date sDate, @Param("eDate") Date eDate,@Param("status") String status);

	@Query(value = "SELECT t.id,t.client_name,t.create_date,t.domain,t.email,t.emp_id,t.emp_name,date(t.end_date) as end_date,t.modify_date,t.phone_no,date(t.start_date) as start_date,t.status,t.shift,t.timing,t.feedback,t.ipaddress,t.longitude,t.latitude,t.location,t.locdetails,(select group_concat(ff.id) from file_data ff where ff.timesheet_id=t.id) as ttid,(select group_concat(ff.file_name) from file_data ff where ff.timesheet_id=t.id) as ttfn FROM timesheet t WHERE t.emp_id = ?1 ", nativeQuery = true)
	List<TimeSheet> findByDefaultValue(int empId);

	@Query(value = "SELECT count(*) FROM timesheet t WHERE start_date >= ?1 AND end_date <= ?2 AND status = ?3", nativeQuery = true)
	Integer findByCurrentMonthPendingStatus(LocalDate startOfMonthDate, LocalDate endOfMonthDate, String status);

	@Query(value = "SELECT count(*) FROM timesheet t WHERE start_date >= ?1 AND end_date <= ?2 AND status = ?3", nativeQuery = true)
	Integer findByCurrentMonthApprovedStatus(LocalDate startOfMonthDate, LocalDate endOfMonthDate, String status);

//	@Query(value = "SELECT count(*) FROM timesheet t WHERE start_date >= ?1 AND end_date <= ?2 AND status = ?3 AND emp_id = ?4", nativeQuery = true)
//	Integer findByCurrentMonthApprovedStatusEmpId(LocalDate startOfMonthDate, LocalDate endOfMonthDate, String status,int empId);

	@Query(value = "SELECT count(*) FROM timesheet t WHERE status = ?1 AND emp_id = ?2", nativeQuery = true)
	Integer findByCurrentYearApprovedStatusEmpId(String status, int empId);

	@Query(value = "SELECT count(*) FROM timesheet t WHERE start_date >= ?1 AND end_date <= ?2 AND status = ?3 AND emp_id = ?4", nativeQuery = true)
	Integer findByCurrentMonthPendingStatusEmpId(LocalDate startOfMonthDate, LocalDate endOfMonthDate, String status,
			int empId);

}
