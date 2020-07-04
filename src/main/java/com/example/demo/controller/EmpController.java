package com.example.demo.controller;

import com.example.demo.dao.DepartmentDao;
import com.example.demo.dao.EmployeeDao;
import com.example.demo.entities.Department;
import com.example.demo.entities.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
public class EmpController {

    @Autowired
    EmployeeDao employeeDao;

    @Autowired
    DepartmentDao departmentDao;
    /**
     * 查询所有员工返回列表页面
     * @return
     */
    @GetMapping("/emps")
    public String list(Model model){
        //tf会默认拼串
       Collection<Employee> employees= employeeDao.getAll();
       //放在请求域中
        model.addAttribute("emps",employees);
        return "emp/list";
    }


    //来到员工添加页面
    @GetMapping("/emp")
    public String toAddPage(Model model){
        //来到添加页面，查出所有的部门，在页面显示
        Collection<Department> departments=departmentDao.getDepartments();
        model.addAttribute("depts",departments);
        return "emp/add";
    }


    //SpringMVC 自动将请求参数和入参对象的属性一一对应，要求了请求参数的名字和JavaBean入参的属性名是一样的
    @PostMapping("/emp")
    public String addEmp(Employee employee){
        System.out.println("保存的员工信息"+employee);
        //保存员工
        employeeDao.save(employee);
        //来到员工列表页面
        //redirect 表示重定向一个地址  /代表当前项目路径
        //forward 表示转发到一个地址
        return "redirect:/emps";

    }


    //来到修改页面，查出当前员工，在页面回显
    @GetMapping("/emp/{id}")
    public String toEditPage(@PathVariable("id") Integer id,Model model){
        //页面显示所有部门列表
        Collection<Department> departments=departmentDao.getDepartments();
        model.addAttribute("depts",departments);
        Employee employee=employeeDao.get(id);
        model.addAttribute("emp",employee);
        //回到修改页面（add是一个修改添加二合一页面）
        //回到修改页面会发现这个样式全丢，这是因为
        return "emp/edit";
    }

    //员工修改：需要提交员工ID
    @PutMapping("/emp")
    public String updateEmp(Employee employee){
        System.out.println(employee);
        employeeDao.save(employee);
        return "redirect:/emps";
    }

    //员工删除
    @PostMapping("/emp/{id}")
    public String deleteEmp(@PathVariable("id")Integer id){
        employeeDao.delete(id);
        return "redirect:/emps";
    }
}
