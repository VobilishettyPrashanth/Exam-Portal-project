package com.csye6220.esdfinalproject.controller;

import com.csye6220.esdfinalproject.model.Category;
import com.csye6220.esdfinalproject.model.CategoryTypes;
import com.csye6220.esdfinalproject.model.User;
import com.csye6220.esdfinalproject.model.UserRole;
import com.csye6220.esdfinalproject.service.CategoryService;
import com.csye6220.esdfinalproject.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Locale;
import java.util.Set;


@Controller
@RequestMapping("/categories")
@Validated
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ModelAndView viewCategoriesHandler(@RequestParam(required = false) String filter,
                                              @RequestParam(required = false) String query,
                                              HttpServletRequest request) {
        filter = filter == null ? "ALL" : filter.toUpperCase(Locale.ROOT);
        query = query == null ? "" : query;

        User user = userService.getUserById(((User) request.getSession().getAttribute("user")).getId());

        ModelAndView mv = new ModelAndView("categories");

        List<Category> validCategories =categoryService.getAllCategories();

            mv.addObject("categories", validCategories);
            mv.addObject("query", query);
            mv.addObject("filter", filter);
        return mv;
        }

        @GetMapping("/create")
        public ModelAndView CategoryCreationHandler(HttpServletRequest request){

            User userInSession = (User) request.getSession().getAttribute("user");

            try {
                if (Set.of(UserRole.ADMIN, UserRole.INSTRUCTOR).contains(userInSession.getRole())) {
                    ModelAndView mv = new ModelAndView("Add_category");
                    mv.addObject("user", userService.getUserById(userInSession.getId()));
                    return mv;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return new ModelAndView("access_denied");
        }

    //To add Exam Category
    @PostMapping("/create")
    public ModelAndView CreateNewCategory(HttpServletRequest request,
                                          @RequestParam(name = "title") @Size(min = 3, message = "Title must contain at least 3 characters") String title,
                                          @RequestParam(name = "category_type") @NotNull(message = "Category type cannot be null") String categoryType,
                                          @RequestParam(name = "description") @NotNull(message = "Description cannot be null") String description
    ) {


        System.out.println("Title : "+title);
        System.out.println("Category type: " + CategoryTypes.valueOf(categoryType));
        System.out.println("Description : "+description);

        CategoryTypes current_Type = CategoryTypes.valueOf(categoryType);

        User current_user = userService.getUserById(((User)request.getSession().getAttribute("user")).getId());

        Category category = new Category(title,description,current_Type,current_user);

        categoryService.addCategory(category);

        request.getSession().setAttribute("category",category);

        ModelAndView mv1 = new ModelAndView("category_success");
        mv1.addObject("category",category);
       return mv1;
    }

    @GetMapping("/view/{categoryId}")
    public ModelAndView ViewCategoryHandler(HttpServletRequest request,
                                            @PathVariable(name = "categoryId") Long categoryId
    ){
        Category category = categoryService.getCategoryById(categoryId);

        ModelAndView mv = new ModelAndView("view_category");

        mv.addObject("category", category);
        return mv;

    }

    @PostMapping("/{categoryId}/exam/add")
    public String AddExamToCategory(@PathVariable(name = "categoryId") Long categoryId, HttpServletRequest request){

        User userInSession = (User) request.getSession().getAttribute("user");
        if (userInSession.getRole() != UserRole.ADMIN) {
            return "forbidden";
        }
        Category category = categoryService.getCategoryById(categoryId);

        request.setAttribute("category", category);

        return "forward:/exam/add";
    }
}
