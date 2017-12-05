package com.christos.controllers;

import com.christos.model.StatusUpdate;
import com.christos.service.StatusUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class StatusUpdateController {

    // We Autowire the Status Update Service to call it when the form is submitted to store the data.
    @Autowired
    private StatusUpdateService statusUpdateService;


    // This is the controller for the editStatus
    @RequestMapping(value = "/editStatus", method = RequestMethod.GET)
    ModelAndView editStatus(ModelAndView modelAndView, @RequestParam(name = "id") Long id) {

        StatusUpdate statusUpdate = statusUpdateService.get(id);

        modelAndView.getModel().put("statusUpdate", statusUpdate);

        modelAndView.setViewName("app.editStatus");

        return modelAndView;
    }


    // This is the controller to save the edited status
    // Like the submit form, we get an object back from the form and we validate it.
    // If the validation passes, we redirect back to the "viewStatus" page. If not, it stays where it is.
    @RequestMapping(value = "/editStatus", method = RequestMethod.POST)
    ModelAndView editStatus(ModelAndView modelAndView, @Valid StatusUpdate statusUpdate, BindingResult result) {

        modelAndView.setViewName("app.editStatus");

        if(!result.hasErrors()) {
            statusUpdateService.save(statusUpdate);
            modelAndView.setViewName("redirect:/viewStatus");
        }

        return modelAndView;
    }


    // This is the implementation for the Delete
    @RequestMapping(value = "/deleteStatus", method = RequestMethod.GET)
    ModelAndView deleteStatus(ModelAndView modelAndView, @RequestParam(name = "id") Long id) {

        statusUpdateService.delete(id);

        // Go back to viewStatus page
        modelAndView.setViewName("redirect:/viewStatus");

        return modelAndView;
    }


    // Here we create a controller for the pagination. It returns a ModelAndView which gets imported
    // automatically by Spring.
    @RequestMapping(value = "/viewStatus", method = RequestMethod.GET)
    ModelAndView viewStatus(ModelAndView modelAndView,
                            // We declare the parameter "p" with a default value.
                            @RequestParam(name = "p", defaultValue = "1") int pageNumber) {

        System.out.println("================================");
        System.out.println("Page Number: " + pageNumber);
        System.out.println("================================");

        // We create a new Page object.
        Page<StatusUpdate> page = statusUpdateService.getPage(pageNumber);

        // We pass the page to the model
        modelAndView.getModel().put("page", page);

        // We set the name of the view of this page
        modelAndView.setViewName("app.viewStatus");

        return modelAndView;
    }


    // To make the form functional, we need to create a new status update object and pass it to this page
    // (with the form). We will next add the text to that object to get it stored in our database.
    // Instead of returning a view, we return the view within a data model which can be later used.
    // To the model we need to add a new status update object in which the form on the page will be able to
    // add data to.

    // We declare as "value" the mapping location, and then we need a method with which we'll get the data
    // from the view.

    // With GET method we "talk" to our server using the URL. For example, we ask for /addStatus and we get
    // back the appropriate view.

    // We'll need to create a new POST method to send the data to our server without being displayed in the URL.

    @RequestMapping(value = "/addStatus", method = RequestMethod.GET)
        // We can tell Spring to create a new object here by defining it as @ModelAttribute
    ModelAndView addStatus(ModelAndView modelAndView, @ModelAttribute("statusUpdate") StatusUpdate statusUpdate) {

        // We set the name of the view of this page
        modelAndView.setViewName("app.addStatus");

/*        // We first create the object
        StatusUpdate statusUpdate = new StatusUpdate();
*/

        // Here we call the latest status update to be displayed.
        StatusUpdate latestStatusUpdate = statusUpdateService.getlatest();

        // The model is a Map. It needs a key and a value. We just need to come up with a key and then we
        // add as value a new StatusUpdate.
/*
        modelAndView.getModel().put("statusUpdate", statusUpdate);
*/

        // We pass the latest update to our model
        modelAndView.getModel().put("latestStatusUpdate", latestStatusUpdate);


        // Finally we return the model and the view
        return modelAndView;
    }

    // THIS is new new Post method for the form submission

    @RequestMapping(value = "/addStatus", method = RequestMethod.POST)
        // Now the StatusUpdate will be validated, and the validation result will be stored on the "result"
    ModelAndView addStatus(ModelAndView modelAndView, @Valid StatusUpdate statusUpdate, BindingResult result) {

        // This declares what should be displayed after the form is submitted
        modelAndView.setViewName("app.addStatus");

        // Only if there are no errors, then Save.
        if (!result.hasErrors()) {

            // We pass on our service the new object which has been created, and filled with text from our form
            statusUpdateService.save(statusUpdate);

            // We create a new Status Update object to clear the form after the form submission
            modelAndView.getModel().put("statusUpdate", new StatusUpdate());

            // Now this controller after the post update redirects us to the viewStatus page
            modelAndView.setViewName("redirect:/viewStatus");
        }

        // Here we call the latest status update to be displayed.
        StatusUpdate latestStatusUpdate = statusUpdateService.getlatest();

        // We pass the latest update to our model
        modelAndView.getModel().put("latestStatusUpdate", latestStatusUpdate);

        return modelAndView;
    }

}
