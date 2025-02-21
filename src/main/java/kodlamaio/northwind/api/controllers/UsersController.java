package kodlamaio.northwind.api.controllers;

import jakarta.validation.Valid;
import kodlamaio.northwind.business.abstracts.UserService;
import kodlamaio.northwind.core.entities.User;
import kodlamaio.northwind.core.utilities.results.DataResult;
import kodlamaio.northwind.core.utilities.results.ErrorDataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    private UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        super();
        this.userService = userService;
    }

    @GetMapping("/findByEmail")
    public DataResult<User> findByEmail(@RequestParam String email) {
        return this.userService.findByEmail(email);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @RequestBody User user) {
        return ResponseEntity.ok(this.userService.add(user));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDataResult<Object> handleValidationException(MethodArgumentNotValidException exceptions){
        Map<String, String> validationsErrors = new HashMap<String, String>();
        for(FieldError fieldError : exceptions.getBindingResult().getFieldErrors()) {
            validationsErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        ErrorDataResult<Object> errors = new ErrorDataResult<Object>(validationsErrors, "Doğrulama hataları.");
        return errors;
    }
}
