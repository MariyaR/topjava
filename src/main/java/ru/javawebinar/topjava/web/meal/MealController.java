package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping(value = "/meals", method = RequestMethod.GET)
public class MealController extends AbstractMealController {

    @GetMapping (value = "/delete/{id}")
    public String deleteMeal(@PathVariable int id) {
        super.delete(id);
        return "meals";
    }

    @GetMapping (value = "/getAll")
    public String getAll(Model model) {
        model.addAttribute("mealList", super.getAll());
        return "meals";
    }

    @GetMapping (value = "/update/{id}")
    public String update(@PathVariable int id, Model model) {
        model.addAttribute("meal", super.get(id));
        return "redirect:/mealForm";
    }

    @GetMapping (value = "/create")
    public String create(Model model) {
        model.addAttribute("meal", new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000));
        return "redirect:/mealForm";
    }

    @PostMapping (value = "/filter")
    public String filter(HttpServletRequest request, Model model) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        model.addAttribute("mealsList", super.getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }
}
