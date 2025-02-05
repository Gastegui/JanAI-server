package com.pbl.demo.controller.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pbl.demo.model.food_class.FoodClass;
import com.pbl.demo.model.food_class.FoodClassRepository;
import com.pbl.demo.model.food_group.FoodGroup;
import com.pbl.demo.model.food_group.FoodGroupRepository;
import com.pbl.demo.model.food_type.FoodType;
import com.pbl.demo.model.food_type.FoodTypeRepository;
import com.pbl.demo.model.ingredients.Ingredients;
import com.pbl.demo.model.ingredients.IngredientsRepository;
import com.pbl.demo.model.restrictions.Restrictions;
import com.pbl.demo.model.restrictions.RestrictionsRepository;
import com.pbl.demo.model.user_data.UserData;
import com.pbl.demo.model.user_data.UserDataRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/restrictions")
public class RestrictionsController {

    
    private RestrictionsRepository restrictRepo;
    private UserDataRepository userRepo;
    private FoodClassRepository foodClassRepo;
    private FoodTypeRepository foodTypeRepo;
    private FoodGroupRepository foodGroupRepo;
    private IngredientsRepository ingredienRepo;

    @Autowired
    public RestrictionsController(RestrictionsRepository restrictRepo, UserDataRepository userRepo, FoodClassRepository foodClassRepo, FoodTypeRepository foodTypeRepo, FoodGroupRepository foodGroupRepo, IngredientsRepository ingredienRepo){
        this.restrictRepo = restrictRepo;
        this.userRepo = userRepo;
        this.foodClassRepo = foodClassRepo;
        this.foodGroupRepo = foodGroupRepo;
        this.foodTypeRepo = foodTypeRepo;
        this.ingredienRepo = ingredienRepo;
    }

    @GetMapping(produces = {"application/json", "application/xml"})
    public ResponseEntity<List<Restrictions>> getAllRestrictions(){
        List<Restrictions> restrictions = restrictRepo.findAll();
        if(restrictions.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            return new ResponseEntity<>(restrictions, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/restrictionsByClass", produces = { "application/json", "application/xml" })
public ResponseEntity<List<FoodClass>> getUsersByName(@RequestParam int userID) {

    Optional<UserData> user = userRepo.findById(userID);
    if (!user.isPresent()) {
        return ResponseEntity.notFound().build();
    }

    List<FoodClass> foodClasses = restrictRepo.findDistinctClasses(userID);
    List<FoodClass> foodClassList = new ArrayList<>(foodClassRepo.findAll());

    filterInvalidFoodClasses(userID, foodClasses, foodClassList);

    return new ResponseEntity<>(foodClassList, HttpStatus.OK);
}

public void filterInvalidFoodClasses(int userID, List<FoodClass> foodClasses, List<FoodClass> foodClassList) {
    for (FoodClass foodClass : foodClasses) {
        if (!foodClassList.contains(foodClass)) {
            continue;
        }

        List<FoodType> restrictedFoodTypes = restrictRepo.findDistinctTypeIDsByClassID(foodClass.getClassID(), userID);
        List<FoodType> allFoodTypes = foodTypeRepo.findByClassID(foodClass.getClassID());

        if (shouldRemoveFoodClass(restrictedFoodTypes, allFoodTypes, userID)) {
            foodClassList.remove(foodClass);
        }
    }
}

public boolean shouldRemoveFoodClass(List<FoodType> restrictedFoodTypes, List<FoodType> allFoodTypes, int userID) {
    // If all types are restricted, remove the class
    if (restrictedFoodTypes.size() == allFoodTypes.size()) {
        filterInvalidFoodTypes(userID, restrictedFoodTypes, allFoodTypes);
        return allFoodTypes.isEmpty();
    }

    // If any restricted type has a type ID of 0, remove the class
    for (FoodType type : restrictedFoodTypes) {
        if (type.getTypeId() == 0) {
            return true;
        }
    }

    return false;
}


@GetMapping(value = "/restrictionsByType", produces = { "application/json", "application/xml" })
public ResponseEntity<List<FoodType>> getUsersByType(@RequestParam int userID, @RequestParam String className) {
    Optional<UserData> user = userRepo.findById(userID);
    if (!user.isPresent()) {
        return ResponseEntity.notFound().build();
    }

    Optional<FoodClass> foodClass = foodClassRepo.findByClassName(className);
    if (!foodClass.isPresent()) {
        return ResponseEntity.notFound().build();
    }

    List<FoodType> foodTypeList = foodTypeRepo.findByClassID(foodClass.get().getClassID());
    List<FoodType> restrictedFoodTypes = restrictRepo.findDistinctTypeIDsByClassID(foodClass.get().getClassID(), userID);

    filterInvalidFoodTypes(userID, restrictedFoodTypes, foodTypeList);

    return new ResponseEntity<>(foodTypeList, HttpStatus.OK);
}


public void filterInvalidFoodTypes(int userID, List<FoodType> restrictedFoodTypes, List<FoodType> foodTypeList) {
    for (FoodType foodType : restrictedFoodTypes) {
        if (!foodTypeList.contains(foodType)) {
            continue;
        }

        List<FoodGroup> restrictedFoodGroups = restrictRepo.findDistinctGruopIDsByTypeID(foodType.getTypeId(), userID);
        List<FoodGroup> allFoodGroups = foodGroupRepo.findByTypeID(foodType.getTypeId());

        if (shouldRemoveFoodType(restrictedFoodGroups, allFoodGroups, userID)) {
            foodTypeList.remove(foodType);
        }
    }
}

public boolean shouldRemoveFoodType(List<FoodGroup> restrictedFoodGroups, List<FoodGroup> allFoodGroups, int userID) {
    

    // If any restricted group has a group ID of 0, remove the type
    for (FoodGroup group : restrictedFoodGroups) {
        if (group.getGroupID() == 0) {
            return true;
        }
    }

    // If all groups are restricted, remove the type
    if (restrictedFoodGroups.size() == allFoodGroups.size()) {
        filterInvalidFoodGroups(userID, restrictedFoodGroups, allFoodGroups);
        return allFoodGroups.isEmpty();
    }

    return false;
}
@GetMapping(value = "/restrictionsByGroup", produces = { "application/json", "application/xml" })
public ResponseEntity<List<FoodGroup>> getUsersByGroup(@RequestParam int userID, @RequestParam String typeName) {

    Optional<UserData> user = userRepo.findById(userID);
    if (!user.isPresent()) {
        return ResponseEntity.notFound().build();
    }

    Optional<FoodType> foodType = foodTypeRepo.findByTypeName(typeName);
    if (foodType.isEmpty()) {
        return ResponseEntity.notFound().build();
    }

    List<FoodGroup> foodGroupList = foodGroupRepo.findByTypeID(foodType.get().getTypeId());
    List<FoodGroup> restrictedFoodGroups = restrictRepo.findDistinctGruopIDsByTypeID(foodType.get().getTypeId(), userID);

    filterInvalidFoodGroups(userID, restrictedFoodGroups, foodGroupList);

    return new ResponseEntity<>(foodGroupList, HttpStatus.OK);
}

public void filterInvalidFoodGroups(int userID, List<FoodGroup> restrictedFoodGroups, List<FoodGroup> foodGroupList) {
    for (FoodGroup foodGroup : restrictedFoodGroups) {
        if (!foodGroupList.contains(foodGroup)) {
            continue;
        }

        List<Ingredients> restrictedIngredients = restrictRepo.findDistinctIngredientIDsByGroupID(foodGroup.getGroupID(), userID);
        List<Ingredients> allIngredients = ingredienRepo.findByGroupID(foodGroup.getGroupID());

        if (shouldRemoveFoodGroup(restrictedIngredients, allIngredients)) {
            foodGroupList.remove(foodGroup);
        }
    }
}

public boolean shouldRemoveFoodGroup(List<Ingredients> restrictedIngredients, List<Ingredients> allIngredients) {
    // If all ingredients are restricted, remove the group
    if (restrictedIngredients.size() == allIngredients.size()) {
        return true;
    }

    // If any restricted ingredient has an ID of 0, remove the group
    for (Ingredients ingredient : restrictedIngredients) {
        if (ingredient.getIngredientID() == 0) {
            return true;
        }
    }

    return false;
}

    @GetMapping(value = "/restrictionsByIngredients", produces = { "application/json", "application/xml" })
    public ResponseEntity<List<Ingredients>> getUsersByIngredients(@RequestParam int userID, @RequestParam String groupName) {


        Optional<UserData> users = userRepo.findById(userID);
        if (!users.isPresent()) {
            return ResponseEntity.notFound().build();
        } else {
            Optional<FoodGroup> foodGroup = foodGroupRepo.findByGroupName(groupName);
            if(foodGroup.isEmpty()){
                return ResponseEntity.notFound().build();
            }else{
                List<Ingredients> foodIngredientList = ingredienRepo.findByGroupID(foodGroup.get().getGroupID());
            
                List<Ingredients> ingredientsList = restrictRepo.findDistinctIngredientIDsByGroupID(foodGroup.get().getGroupID(), userID);
                for(Ingredients ingredient: ingredientsList)
                {
                    if(foodIngredientList.contains(ingredient))
                    {
                        foodIngredientList.remove(ingredient);
                    }                    
                }           
            
            return new ResponseEntity<>(foodIngredientList, HttpStatus.OK);
            }
            
        }
    }
    
    


    @PostMapping(value = "/addRestriction", consumes = { "application/json", "application/xml" }, produces = {
            "application/json", "application/xml" })
    public ResponseEntity<Restrictions> addRestriction(@RequestParam int userID, @Valid @RequestBody Restrictions restriction, BindingResult result) {
        

        if (result.hasErrors()) {
            StringBuilder errorMessages = new StringBuilder("Error: ");
            result.getAllErrors().forEach(error -> errorMessages.append(error.getDefaultMessage()).append(" "));
            return ResponseEntity.badRequest().header("Validation-Error", errorMessages.toString()).build();
        }
        
        Optional<UserData> user = userRepo.findById(userID);
        boolean componentsPresent = restriction.getFoodGroup() != null && restriction.getFoodClass() != null && restriction.getFoodType() != null && restriction.getIngredient() != null;
        if (user.isPresent() && componentsPresent) {
            restriction.setUserData(user.get());
            Optional<FoodGroup> groupObject = foodGroupRepo.findById(restriction.getFoodGroup().getGroupID());
            Optional<FoodClass> classObject = foodClassRepo.findById(restriction.getFoodClass().getClassID());
            Optional<FoodType> typeObject = foodTypeRepo.findById(restriction.getFoodType().getTypeId());
            Optional<Ingredients> ingredientObject = ingredienRepo.findById(restriction.getIngredient().getIngredientID());
            if(groupObject.isEmpty() || classObject.isEmpty() || typeObject.isEmpty() || ingredientObject.isEmpty()){
                return ResponseEntity.notFound().build();
            }
            restriction.setFoodGroup(groupObject.get());
            restriction.setFoodType(typeObject.get());
            restriction.setFoodClass(classObject.get());
            restriction.setIngredients(ingredientObject.get());
            
            restrictRepo.save(restriction);
            return new ResponseEntity<>(restriction, HttpStatus.CREATED);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    

}
