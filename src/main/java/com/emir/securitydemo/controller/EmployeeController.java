package com.emir.securitydemo.controller;

import com.emir.securitydemo.dto.EmployeeDTO;
import com.emir.securitydemo.model.Employee;
import com.emir.securitydemo.repository.EmployeeRepository;
import com.emir.securitydemo.security.auth.RegisterRequest;
import com.emir.securitydemo.service.AuthenticationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;
    AuthenticationServiceImpl authenticationService;

    @GetMapping
    public String home(HttpServletRequest request,
                       Model model,
                       @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                       @RequestParam(value = "size", required = false, defaultValue = "2") Integer size,
                       @RequestParam(value = "search", required = false) String searchParameter) {

        List<Integer> pages = new ArrayList<>();
        List<Integer> pageSize = Arrays.asList(1, 2, 5, 10, 20);
        int numberOfEmployees;
        int numberOfPages;

        Pageable pageable = PageRequest.of(page-1, size);

        if (searchParameter == null) {
            numberOfEmployees = (int) employeeRepository.count();
            model.addAttribute("employees", employeeRepository.findAllByOrderByIdAsc(pageable));
        } else {
            numberOfEmployees = (int) employeeRepository.findFilteredEmployees(searchParameter, pageable).getTotalElements();
            model.addAttribute("employees", employeeRepository.findFilteredEmployees(searchParameter, pageable));
        }

        if (numberOfEmployees % size == 0) {
            numberOfPages =  numberOfEmployees / size;
        } else {
            numberOfPages = (numberOfEmployees / size) + 1;
        }

        for (int i = 1; i <= numberOfPages; i++) {
            pages.add(i);
        }

        model.addAttribute("sizeOfPages", pageSize);
        model.addAttribute("numOfPages", pages);

        return "/employees/home";
    }

    @GetMapping("/delete")
    public String deleteEmployee(@RequestParam(value = "id") Long id) {
        employeeRepository.deleteById(id);
        return "redirect:/employees";
    }

    @GetMapping("/edit")
    public String editEmployee(Model model,
            @RequestParam(value = "id") Long id) {
        Optional<Employee> emp = employeeRepository.findById(id);
        model.addAttribute("employee", emp);
        return "employees/editInfo";
    }

    @PostMapping("/update")
    public String updateEmployee(@ModelAttribute("employee") EmployeeDTO employeeDTO,
                                 @RequestParam("id") Long id) {
        Employee emp = employeeRepository.findById(id).get();

        emp.setName(employeeDTO.getName());
        emp.setSurname(employeeDTO.getSurname());
        emp.setUsername(employeeDTO.getUsername());

        employeeRepository.save(emp);

        return "redirect:/employees?updated=true";
    }

    @GetMapping("/hire")
    public String showHiringForm() {
        return "employees/hiringForm";
    }

    @PostMapping("/hire")
    public String hireEmployee(@ModelAttribute("employee") RegisterRequest request) {
        if (employeeRepository.findByUsername(request.getUsername()).isEmpty()) {
            authenticationService.register(request);
            return "redirect:/employees/hire?hired";
        } else {
            return "redirect:/employees/hire?error";
        }
    }

    @PostMapping("/filter")
    public String filterEmployees(@RequestParam(value = "search", required = false) String searchParameter) {

        if (searchParameter.equals("")) {
            return "redirect:/employees";
        } else {
            return "redirect:/employees?search=" + searchParameter;
        }
    }
}
