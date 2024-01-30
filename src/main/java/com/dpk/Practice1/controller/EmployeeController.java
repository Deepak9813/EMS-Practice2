package com.dpk.Practice1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dpk.Practice1.model.Employee;
import com.dpk.Practice1.service.DepartmentService;
import com.dpk.Practice1.service.EmployeeService;
import com.dpk.Practice1.utils.EmployeeExcelView;
import com.dpk.Practice1.utils.EmployeePdfView;

import jakarta.servlet.http.HttpSession;

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeService empService;

	@Autowired
	private DepartmentService deptService;

	@GetMapping("/employee")
	public String getEmpForm(Model model, HttpSession session) {

		if (session.getAttribute("validUser") == null) {

			return "LoginForm";
		}

//		List<Department> deptList = deptService.getAllDept();
//		model.addAttribute("dList", deptList); // ("key", value)

		model.addAttribute("dList", deptService.getAllDept()); // ("key", value)

		return "EmployeeForm";
	}

	@PostMapping("/employee")
	public String postEmp(@ModelAttribute Employee emp) {

		empService.addEmp(emp);

		return "EmployeeForm";
	}

	@GetMapping("/empList")
	public String getAllEmp(Model model, HttpSession session) {

		if (session.getAttribute("validUser") == null) {

			return "LoginForm";
		}

//		List<Employee> empList = empService.getAllEmp();
//		model.addAttribute("eList", empList);				//("key", value)

		model.addAttribute("eList", empService.getAllEmp()); // ("key", value)

		return "EmployeeList";
	}

	@GetMapping("/emp/delete/{id}")
	public String deleteEmp(@PathVariable Long id, HttpSession session) {

		if (session.getAttribute("validUser") == null) {

			return "LoginForm";
		}

		empService.deleteEmp(id);
		return "redirect:/empList";
	}

	@GetMapping("/emp/edit")
	public String editEmp(@RequestParam Long id, Model model, HttpSession session) {

		if (session.getAttribute("validUser") == null) {

			return "LoginForm";
		}

//		Employee emp = empService.getEmpById(id);
//		model.addAttribute("empModel", emp); 				//("key", value)
		// here, empModel = empObject (j) lekhda ni hunxa because Employee ko object aauxa

		model.addAttribute("empModel", empService.getEmpById(id)); // ("key", value)

//		List<Department> deptList=deptService.getAllDept();
//		model.addAttribute("dList", deptList);			//("key", value)

		model.addAttribute("dList", deptService.getAllDept()); // ("key", value)

		return "EmployeeEditForm";
	}

	@PostMapping("/emp/update")
	public String updateEmp(@ModelAttribute Employee emp) {

		empService.updateEmp(emp);
		return "redirect:/empList";
	}
	
	
	@GetMapping("/emp/excel")
	public ModelAndView excel() {
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("list", empService.getAllEmp());			//("key", value)
		mv.setView(new EmployeeExcelView());
		
		return mv;
	}
	
	
	@GetMapping("/emp/pdf")
	public ModelAndView pdf() {
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("list", empService.getAllEmp());		//("key", value)
		mv.setView(new EmployeePdfView());
		
		return mv;
	}

}
