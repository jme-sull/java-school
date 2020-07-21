package com.lambdaschool.schools.services;

import com.lambdaschool.schools.models.ValidationError;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@Service(value = "helperFunctions")
public class HelperFunctionsImpl implements HelperFunctions
{
    @Override
    public List<ValidationError> getConstraintViolation(Throwable cause)
    {
        //if there is an exception (cause is not null) and the cause is NOT an instance of Constraint Violation Exception
        //keep looking down the list for the cause
        while((cause != null) && !(cause instanceof ConstraintViolationException))
        {
            cause = cause.getCause();
        }

        List<ValidationError> listVE = new ArrayList<>();

        //once cause is not null, set ex equal to cause (typecasting as a constraint violation). Then, loop through
        //the output of ex.getContraintViolations(its a list of cvs), calling each instance the variable name "cv".
        //Instanitae the validation error(from the class we created). Set the code equal to the invalid value found on cv,
        //set the message equal to the message found on cv. Then, add it to the list we created outside of the loop.
        //Return the list!
        
        if (cause != null)
        {
            ConstraintViolationException ex = (ConstraintViolationException) cause;
            for (ConstraintViolation cv : ex.getConstraintViolations())
            {
                ValidationError newVe = new ValidationError();
                newVe.setCode(cv.getInvalidValue().toString());
                newVe.setMessage(cv.getMessage());
                listVE.add(newVe);
            }

        }

        return listVE;
    }
}
