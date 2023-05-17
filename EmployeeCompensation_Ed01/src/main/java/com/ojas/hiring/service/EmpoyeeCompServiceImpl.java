package com.ojas.hiring.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

//import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.hiring.entity.EmployeeCompensation;

import com.ojas.hiring.repo.EmpCompansationRepo;

@Service
public class EmpoyeeCompServiceImpl implements EmpoyeeCompService {
	@Autowired
	private EmpCompansationRepo repo;
	@Autowired
	private EntityManager entityManager;

	@Override
	public String saveCompansation(String employeeId, String userName, String plan, String employeeType,
			String projectCategory, String clientName, String projectStatus, String projectStartDate,
			String projectEndDate, double tenure, String department, String billingStatus, String ojasEmailID,
			double billRatesPM, double estimationOfActualBilling, double mCtc, int margin, double yCtc) {

		EmployeeCompensation employeeCompensation = new EmployeeCompensation(employeeId, userName, plan, employeeType,
				projectCategory, clientName, projectStatus, projectStartDate, projectEndDate, yCtc, department,
				billingStatus, ojasEmailID, billRatesPM, estimationOfActualBilling, mCtc, margin, yCtc);

		repo.save(employeeCompensation);
		return "record inseted";
	}

	@Override
	public List<EmployeeCompensation> getAllEmpComp() {

		return repo.findAll();
	}

	@Override
	public EmployeeCompensation getEmployeeById(int id) {
		Optional<EmployeeCompensation> optional = repo.findById(id);
		EmployeeCompensation employee = null;
		if (optional.isPresent()) {
			employee = optional.get();
		} else {
			throw new RuntimeException("Employee not found for id ::" + id);
		}
		return employee;
	}

	@Override
	public void deleteEmployeeById(int id) {
		this.repo.deleteById(id);

	}

	@Override
	public List<EmployeeCompensation> getByEmailId(String email) {
		return repo.getByEmailId(email);

	}

	public List<EmployeeCompensation> getByEmployeeId(String id) {
		System.out.println(id);
		List<EmployeeCompensation> byEmployeeId = repo.getByEmployeeId(id);

		return byEmployeeId;
	}

	@Override
	public void saveEmployee(EmployeeCompensation employee) {
		System.out.println(employee);
		repo.save(employee);

	}

	@Override
	public Page<EmployeeCompensation> findpaginated(int pageNo, int pageSize, String sortField, String sortDirection) {

		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending()
				: Sort.by(sortField).descending();
		// for Pageable
		// Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		// for sorting
		// Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

		// for pageable and sorting
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		// for Pageable
		// return this.employeeRepository.findAll(pageable);
		// for sorting
		// return this.employeeRepository.findAll(sort);

		// for pageable and sorting
		return this.repo.findAll(pageable);

	}

	@Override
	public List<Double> employeeCount(String startDate, String endDate) {
		return repo.employeeCount(startDate, endDate);
	}

	@Override
	public List<Double> negativeCtcEmpCount(String startDate, String endDate) {

		return repo.negativeCtcEmpCount(startDate, endDate);
	}

	public List<Double> negativeCtcByMonth(int month) {
		return repo.negativeCtcByMonth(month);

	}

	public List<Double> negativeEmpMarginTypes(String startDate, String endDate) {

		return repo.negativeEmpMarginTypes(startDate, endDate);
	}

	public List<Double> positiveEmpMarginTypes(String startDate, String endDate) {

		return repo.positiveEmpMarginTypes(startDate, endDate);
	}

	@Override
	public void deleteEmployeeByEmployeeId(String employeeId) {
		repo.deleteEmployeeByEmployeeId(employeeId);

	}

	@Override
	public String projectCategoryBillings(String startDaate, String endDate) {
		ArrayList<String> t_columns = new ArrayList<String>();
		t_columns.add("bill_rates_per_month");
		t_columns.add("estimation_of_actual_billing");
		t_columns.add("monthly_ctc");
		t_columns.add("margin");
		t_columns.add("billing_status");

		String query = "select sum(ec.bill_ratespm) as bill_rates_per_month,sum(ec.estimation_of_actual_billing)"
				+ " as estimation_of_actual_billing,sum(ec.m_ctc) as monthly_ctc,sum(ec.margin) as margin, ec.billing_status"
				+ " from employee_compensation ec  where STR_TO_DATE(ec.project_start_date, '%d-%M-%Y') >= STR_TO_DATE('"
				+ startDaate + "', '%d-%M-%Y')" + " AND STR_TO_DATE(ec.project_end_date, '%d-%M-%Y') <= STR_TO_DATE('"
				+ endDate + "', '%d-%M-%Y') group by ec.billing_status order by ec.project_category";
		List<Object[]> valuePairs = entityManager.createNativeQuery(query).getResultList();
		String data[] = new String[valuePairs.size()];
		for (int j = 0; j < data.length; j++) {
			data[j] = "";
		}

		for (int j = 0; j < t_columns.size(); j++) {
			int count = 0;
			for (Object[] dat : valuePairs) {
				String str = "";
				if (j == t_columns.size() - 1) {
					str = "" + dat[j];

				} else {
					DecimalFormat df = new DecimalFormat("#");
					df.setMaximumFractionDigits(2);

					str = "" + df.format(Double.parseDouble("" + dat[j]));
				}

				// str = "\"" + t_columns.get(j) + "\":\"" + str + "\",";
				str = "\"" + t_columns.get(j) + "\":\"" + str + "\",";
				data[count] += str;
				count++;
			}
			// String str = (String) valuePairs.get(j);
			// System.out.println(str);
		}
		String res = "[";
		for (int j = 0; j < data.length; j++) {
			data[j] = data[j].substring(0, data[j].length() - 1);
			if ((j + 1) == data.length) {
				res += "{" + data[j] + "}";
			} else {
				res += "{" + data[j] + "},";
			}

		}
//		

		res += "]";
		// System.out.println(res);
		return res;
	}

	@Override
	public String getBillableProjectCategoryBilling(String startDaate, String endDate) {
		ArrayList<String> t_columns = new ArrayList<String>();
		t_columns.add("bill_rates_per_month");
		t_columns.add("estimation_of_actual_billing");
		t_columns.add("monthly_ctc");
		t_columns.add("margin");
		t_columns.add("billing_status");

		String query = "select sum(ec.bill_ratespm) as bill_rates_per_month,sum(ec.estimation_of_actual_billing)"
				+ " as estimation_of_actual_billing,sum(ec.m_ctc) as monthly_ctc,sum(ec.margin) as margin, ec.billing_status"
				+ " from employee_compensation ec  where STR_TO_DATE(ec.project_start_date, '%d-%M-%Y') >= STR_TO_DATE('"
				+ startDaate + "', '%d-%M-%Y')" + " AND STR_TO_DATE(ec.project_end_date, '%d-%M-%Y') <= STR_TO_DATE('"
				+ endDate + "', '%d-%M-%Y') and billing_status='Billable' group by ec.billing_status order by ec.project_category";
		List<Object[]> valuePairs = entityManager.createNativeQuery(query).getResultList();
		String data[] = new String[valuePairs.size()];
		for (int j = 0; j < data.length; j++) {
			data[j] = "";
		}

		for (int j = 0; j < t_columns.size(); j++) {
			int count = 0;
			for (Object[] dat : valuePairs) {
				String str = "";
				if (j == t_columns.size() - 1) {
					str = "" + dat[j];

				} else {
					DecimalFormat df = new DecimalFormat("#");
					df.setMaximumFractionDigits(2);

					str = "" + df.format(Double.parseDouble("" + dat[j]));
				}

				// str = "\"" + t_columns.get(j) + "\":\"" + str + "\",";
				str = "\"" + t_columns.get(j) + "\":\"" + str + "\",";
				data[count] += str;
				count++;
			}
			// String str = (String) valuePairs.get(j);
			// System.out.println(str);
		}
		String res = "[";
		for (int j = 0; j < data.length; j++) {
			data[j] = data[j].substring(0, data[j].length() - 1);
			if ((j + 1) == data.length) {
				res += "{" + data[j] + "}";
			} else {
				res += "{" + data[j] + "},";
			}

		}
//		

		res += "]";
		// System.out.println(res);
		return res;
	}
	//Buffer

	@Override
	public String getBufferProjectCategoryBilling(String startDaate, String endDate) {
		ArrayList<String> t_columns = new ArrayList<String>();
		t_columns.add("bill_rates_per_month");
		t_columns.add("estimation_of_actual_billing");
		t_columns.add("monthly_ctc");
		t_columns.add("margin");
		t_columns.add("billing_status");

		String query = "select sum(ec.bill_ratespm) as bill_rates_per_month,sum(ec.estimation_of_actual_billing)"
				+ " as estimation_of_actual_billing,sum(ec.m_ctc) as monthly_ctc,sum(ec.margin) as margin, ec.billing_status"
				+ " from employee_compensation ec  where STR_TO_DATE(ec.project_start_date, '%d-%M-%Y') >= STR_TO_DATE('"
				+ startDaate + "', '%d-%M-%Y')" + " AND STR_TO_DATE(ec.project_end_date, '%d-%M-%Y') <= STR_TO_DATE('"
				+ endDate + "', '%d-%M-%Y') and billing_status='Buffer' group by ec.billing_status order by ec.project_category";
		List<Object[]> valuePairs = entityManager.createNativeQuery(query).getResultList();
		String data[] = new String[valuePairs.size()];
		for (int j = 0; j < data.length; j++) {
			data[j] = "";
		}

		for (int j = 0; j < t_columns.size(); j++) {
			int count = 0;
			for (Object[] dat : valuePairs) {
				String str = "";
				if (j == t_columns.size() - 1) {
					str = "" + dat[j];

				} else {
					DecimalFormat df = new DecimalFormat("#");
					df.setMaximumFractionDigits(2);

					str = "" + df.format(Double.parseDouble("" + dat[j]));
				}

				// str = "\"" + t_columns.get(j) + "\":\"" + str + "\",";
				str = "\"" + t_columns.get(j) + "\":\"" + str + "\",";
				data[count] += str;
				count++;
			}
			// String str = (String) valuePairs.get(j);
			// System.out.println(str);
		}
		String res = "[";
		for (int j = 0; j < data.length; j++) {
			data[j] = data[j].substring(0, data[j].length() - 1);
			if ((j + 1) == data.length) {
				res += "{" + data[j] + "}";
			} else {
				res += "{" + data[j] + "},";
			}

		}
//		

		res += "]";
		// System.out.println(res);
		return res;
	}

	@Override
	public String getNonBillableProjectCategoryBilling(String startDaate, String endDate) {
		ArrayList<String> t_columns = new ArrayList<String>();
		t_columns.add("bill_rates_per_month");
		t_columns.add("estimation_of_actual_billing");
		t_columns.add("monthly_ctc");
		t_columns.add("margin");
		t_columns.add("billing_status");

		String query = "select sum(ec.bill_ratespm) as bill_rates_per_month,sum(ec.estimation_of_actual_billing)"
				+ " as estimation_of_actual_billing,sum(ec.m_ctc) as monthly_ctc,sum(ec.margin) as margin, ec.billing_status"
				+ " from employee_compensation ec  where STR_TO_DATE(ec.project_start_date, '%d-%M-%Y') >= STR_TO_DATE('"
				+ startDaate + "', '%d-%M-%Y')" + " AND STR_TO_DATE(ec.project_end_date, '%d-%M-%Y') <= STR_TO_DATE('"
				+ endDate + "', '%d-%M-%Y') and billing_status='Non-Billable' group by ec.billing_status order by ec.project_category";
		List<Object[]> valuePairs = entityManager.createNativeQuery(query).getResultList();
		String data[] = new String[valuePairs.size()];
		for (int j = 0; j < data.length; j++) {
			data[j] = "";
		}

		for (int j = 0; j < t_columns.size(); j++) {
			int count = 0;
			for (Object[] dat : valuePairs) {
				String str = "";
				if (j == t_columns.size() - 1) {
					str = "" + dat[j];

				} else {
					DecimalFormat df = new DecimalFormat("#");
					df.setMaximumFractionDigits(2);

					str = "" + df.format(Double.parseDouble("" + dat[j]));
				}

				// str = "\"" + t_columns.get(j) + "\":\"" + str + "\",";
				str = "\"" + t_columns.get(j) + "\":\"" + str + "\",";
				data[count] += str;
				count++;
			}
			// String str = (String) valuePairs.get(j);
			// System.out.println(str);
		}
		String res = "[";
		for (int j = 0; j < data.length; j++) {
			data[j] = data[j].substring(0, data[j].length() - 1);
			if ((j + 1) == data.length) {
				res += "{" + data[j] + "}";
			} else {
				res += "{" + data[j] + "},";
			}

		}
//		

		res += "]";
		// System.out.println(res);
		return res;
	}

}
