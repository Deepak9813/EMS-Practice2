package com.dpk.Practice1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dpk.Practice1.model.Department;
import com.dpk.Practice1.service.DepartmentService;
import com.dpk.Practice1.utils.DepartmentExcelView;
import com.dpk.Practice1.utils.DepartmentPdfView;

import jakarta.servlet.http.HttpSession;

@Controller
public class DepartmentController {

	@Autowired
	private DepartmentService deptService;

	@GetMapping("/department")
	public String getDeptForm(HttpSession session) {

		if (session.getAttribute("validUser") == null) {

			return "LoginForm";
		}

		return "DepartmentForm";
	}

	@PostMapping("/department")
	public String postDept(@ModelAttribute Department dept, Model model) {

		// check if Department (Department Name) already exist or not
		Department d = deptService.getDeptByDeptName(dept.getDeptName());
		if (d != null) {

			model.addAttribute("deptAlreadyExist", "Department Already Exist!"); // ("key", value)
			return "DepartmentForm";

		}

		deptService.addDept(dept);
		// return "DepartmentForm";
		return "redirect:/department"; // it indicates GetMapping url
	}

	@GetMapping("/deptList")
	public String getAllDept(Model model, HttpSession session) {

		if (session.getAttribute("validUser") == null) {

			return "LoginForm";
		}

//		List<Department> deptList = deptService.getAllDept();
//		model.addAttribute("dList", deptList); // ("key", value)

		model.addAttribute("dList", deptService.getAllDept()); // ("key", value)

		return "DepartmentList";
	}

	@GetMapping("/dept/delete/{id}")
	public String deleteDept(@PathVariable int id, HttpSession session) {

		if (session.getAttribute("validUser") == null) {

			return "LoginForm";
		}

		deptService.deleteDept(id);
		return "redirect:/deptList";
	}

	@GetMapping("/dept/edit")
	public String editDept(@RequestParam int id, Model model, HttpSession session) {

		if (session.getAttribute("validUser") == null) {

			return "LoginForm";
		}

//		Department dept = deptService.getDeptById(id);
//		model.addAttribute("deptModel", dept); // ("key", value)

// here, deptModel = deptObject (j) lekhda ni hunxa because Department ko object aauxa

		model.addAttribute("deptModel", deptService.getDeptById(id)); // ("key", value)

		return "DepartmentEditForm";
	}

	@PostMapping("/dept/update")
	public String updateDept(@ModelAttribute Department dept, Model model) {

		deptService.updateDept(dept);

		return "redirect:/deptList";
	}
	
	@GetMapping("/dept/excel")
	public ModelAndView excel() {
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("list", deptService.getAllDept());		//("key", value)
		mv.setView(new DepartmentExcelView());
		
		return mv;
	}
	
	
	@GetMapping("/dept/pdf")
	public ModelAndView pdf() {
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("list", deptService.getAllDept());		//("key", value)
		mv.setView(new DepartmentPdfView());
		
		return mv;
	}
	
}
