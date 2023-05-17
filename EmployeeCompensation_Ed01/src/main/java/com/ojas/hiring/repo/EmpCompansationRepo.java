package com.ojas.hiring.repo;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.ojas.hiring.entity.EmployeeCompensation;

@Repository
public interface EmpCompansationRepo extends JpaRepository<EmployeeCompensation, Integer> {
	@Query(value = "SELECT count(*) as empCount FROM `employee_compensation`"
			+ " WHERE STR_TO_DATE(`project_start_date`, '%d-%M-%Y') >= STR_TO_DATE(?1, '%d-%M-%Y')"
			+ " AND STR_TO_DATE(`project_end_date`, '%d-%M-%Y') <= STR_TO_DATE(?2, '%d-%M-%Y')", nativeQuery = true)
	public List<Double> employeeCount(String startDate, String endDate);

	@Query(value = "SELECT count(*) as empCount FROM `employee_compensation`"
			+ " WHERE STR_TO_DATE(`project_start_date`, '%d-%M-%Y') >= STR_TO_DATE(?1, '%d-%M-%Y')"
			+ " AND STR_TO_DATE(`project_end_date`, '%d-%M-%Y') <= STR_TO_DATE(?2, '%d-%M-%Y') and m_ctc<0", nativeQuery = true)
	public List<Double> negativeCtcEmpCount(String startDate, String endDate);

	@Query(value = "SELECT count(*) as empCount FROM `employee_compensation` WHERE MONTH(STR_TO_DATE(`project_start_date`, '%d-%M-%Y')) = ?1", nativeQuery = true)
	public List<Double> negativeCtcByMonth(int month);

	@Query(value = "select * from employee_compensation where ojas_emailid=?1l", nativeQuery = true)
	public List<EmployeeCompensation> getByEmailId(String email);

	@Query(value = "select * from employee_compensation where employee_id=?1", nativeQuery = true)
	public List<EmployeeCompensation> getByEmployeeId(String id);

	@Query(value = "SELECT count(*) as empCount FROM `employee_compensation`"
			+ " WHERE STR_TO_DATE(`project_start_date`, '%d-%M-%Y') >= STR_TO_DATE(?1, '%d-%M-%Y')"
			+ " AND STR_TO_DATE(`project_end_date`, '%d-%M-%Y') <= STR_TO_DATE(?2, '%d-%M-%Y') and m_ctc<0", nativeQuery = true)
	public List<Double> negativeEmpMarginTypes(String startDate, String endDate);

	@Query(value = "SELECT count(*) as empCount FROM `employee_compensation`"
			+ " WHERE STR_TO_DATE(`project_start_date`, '%d-%M-%Y') >= STR_TO_DATE(?1, '%d-%M-%Y')"
			+ " AND STR_TO_DATE(`project_end_date`, '%d-%M-%Y') <= STR_TO_DATE(?2, '%d-%M-%Y') and m_ctc>0", nativeQuery = true)
	public List<Double> positiveEmpMarginTypes(String startDate, String endDate);

	@Modifying
    @Transactional
	@Query(value = "delete  FROM employee_compensation where employee_id=?1", nativeQuery = true)
	public void deleteEmployeeByEmployeeId(String employeeId);

	@Query(value="select sum(ec.bill_ratespm) as bill_rates_per_month,sum(ec.estimation_of_actual_billing)"
			+ " as estimation_of_actual_billing,sum(ec.m_ctc) as monthly_ctc,sum(ec.margin) as margin, ec.billing_status"
			+ " from employee_compensation ec  where STR_TO_DATE(ec.project_start_date, '%d-%M-%Y') >= STR_TO_DATE(?1, '%d-%M-%Y')"
			+ " AND STR_TO_DATE(ec.project_end_date, '%d-%M-%Y') <= STR_TO_DATE(?2, '%d-%M-%Y') group by ec.billing_status order by ec.project_category",nativeQuery=true)
	public List<String> projectCategoryBillings(String startDaate,String endDate);
}
