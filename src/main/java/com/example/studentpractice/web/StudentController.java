package com.example.studentpractice.web;

import com.example.studentpractice.entities.Student;
import com.example.studentpractice.repositories.StudentRepository;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SessionAttributes({"a", "e"})
@Controller
@AllArgsConstructor
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    static int num=0;

    @GetMapping(path="/index")
    public String students(Model model, @RequestParam(name="keyword",defaultValue = "") String keyword) {

        List<Student> students;
        if(keyword.isEmpty()) {
            students = studentRepository.findAll();
        } else {
            long key = Long.parseLong(keyword);
            students = studentRepository.findStudentById(key);
        }
        model.addAttribute("listStudents",students);

        return "students";
    }

    @GetMapping("/delete")
    public String delete(Long id) {
        studentRepository.deleteById(id);
        return "redirect:/index";
    }

    @GetMapping("/formStudents")
    public String formStudents(Model model) {
        model.addAttribute("student", new Student());
        return "formStudents";
    }

    @PostMapping(path="/save")
    public String save(Model model, Student student, BindingResult bindingResult, ModelMap modelMap,
                       HttpSession session) {
        if(bindingResult.hasErrors()) {
            return "formStudents";
        } else {
            studentRepository.save(student);
            if(num == 2) {
                modelMap.put("e", 2);
                modelMap.put("a", 0);
            } else {
                modelMap.put("a", 1);
                modelMap.put("e", 0);
            }

            return "redirect:index";
        }
    }

    @GetMapping("/editStudents")
    public String editStudents(Model model, Long id, HttpSession session) {
        num = 2;

        session.setAttribute("info", 0);

        Student student = studentRepository.findById(id).orElse(null);
        if(student==null) throw new RuntimeException("Student does not exist");
        model.addAttribute("student", student);

        return "editStudents";
    }

    @GetMapping(path="/")
    public String students(Model model, ModelMap modelMap, @RequestParam
                           (name="keyword",defaultValue = "") String keyword, HttpSession session)  {

            List<Student> students;
            if(keyword.isEmpty()) {
                students = studentRepository.findAll();
            } else {
                modelMap.put("e", 0);
                modelMap.put("a", 0);
                long key = Long.parseLong(keyword);
                students = studentRepository.findStudentById(key);
            }
            model.addAttribute("listStudents",students);

            return "students";
    }
}
