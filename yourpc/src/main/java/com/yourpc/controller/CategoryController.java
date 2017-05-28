package com.yourpc.controller;

import com.yourpc.entity.Category;
import com.yourpc.entity.Role;
import com.yourpc.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Transactional
public class CategoryController
{
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping(value="/category")
    public String addCategory(Model model)
    {
        model.addAttribute("category", new Category());
        return "category";
    }

    @PostMapping(value="/saveCategory")
    public String addCategory(@ModelAttribute Category category)
    {
        categoryService.add(category);
        return "redirect:/";
    }

    @GetMapping(value="/deleteCategory/{id}")
    public String deleteCategory(@PathVariable int id)
    {
        categoryService.delete(id);
        return "redirect:/";
    }

    @GetMapping(value="/updateCategory/{id}")
    public String getCategory(@PathVariable int id, Model model)
    {
        model.addAttribute("categoryAttribute", categoryService.getOne(id));
        return "updateCategory";
    }

    @PostMapping(value="/updateCategory/{id}")
    public  String updateRole(@ModelAttribute Category category, @PathVariable int id, Model model)
    {
        category.setId(id);
        categoryService.update(category);
        model.addAttribute("categories", categoryService.getAll());
        return "redirect:/";
    }
}
