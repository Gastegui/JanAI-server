package com.pbl.demo.controller.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pbl.demo.model.foodClass.FoodClass;
import com.pbl.demo.model.foodClass.FoodClassRepository;
import com.pbl.demo.model.foodGroup.FoodGroup;
import com.pbl.demo.model.foodGroup.FoodGroupRepository;
import com.pbl.demo.model.foodType.FoodType;
import com.pbl.demo.model.foodType.FoodTypeRepository;
import com.pbl.demo.model.ingredients.Ingredients;
import com.pbl.demo.model.ingredients.IngredientsRepository;
import com.pbl.demo.model.ingredientsInCampaign.IngredientsInCampaign;
import com.pbl.demo.model.restrictions.Restrictions;
import com.pbl.demo.model.restrictions.RestrictionsRepository;
import com.pbl.demo.model.userData.UserData;
import com.pbl.demo.model.userData.UserDataRepository;

@RestController
@RequestMapping(value = "/restrictions")
public class RestrictionsController {

    @Autowired
    private RestrictionsRepository restrictRepo;
    @Autowired
    private UserDataRepository userRepo;
    @Autowired
    private FoodClassRepository foodClassRepo;
    @Autowired
    private FoodTypeRepository foodTypeRepo;
    @Autowired
    private FoodGroupRepository foodGroupRepo;
    @Autowired
    private IngredientsRepository ingredienRepo;

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
        int remove = 0;
        int count = 0;
        Optional<UserData> users = userRepo.findById(userID);
        
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            List<FoodClass> foodClasses = restrictRepo.findDistinctClasses(userID);
            List<FoodClass> foodClassList = foodClassRepo.findAll();

            for(FoodClass foodClass: foodClasses)
            {
                if(foodClassList.contains(foodClass))
                {
                    List<FoodType> foodTypes = restrictRepo.findDistinctTypeIDsByClassID(foodClass.getClassID(), userID);
                    List<FoodType> foodTypeList = foodTypeRepo.findByClassID(foodClass.getClassID());
                    /*for(FoodType food: foodTypes)
                    {//zuzendu
                        count++;
                        if(food.getTypeId()==0)
                        {
                            remove++;
                        }
                    }  
                    if(count == remove)
                        {
                            foodClassList.remove(foodClass);
                        }
                        count=0;
                        remove=0; */ 
                    if(foodTypeList.size() == foodTypes.size())
                    {
                        foodClassList.remove(foodClass);
                    }
                    else{
                        for(FoodType type: foodTypes)
                        {
                            if(type.getTypeId()==0)
                            {
                                foodClassList.remove(foodClass);
                                break;
                            }
                        }
                    }
                                    
                }
                
            }
            /*foodClasses = new ArrayList<>(foodClassList);
            for(FoodClass foodClass: foodClasses){
            List<FoodType> foodTypes = restrictRepo.findDistinctTypeIDsByClassID(foodClass.getClassID());
                    for(FoodType food: foodTypes)
                    {
                        if(food.getTypeId()==0)
                        {
                            foodClassList.remove(foodClass);
                            break;
                        }
                    }
                }*/
            return new ResponseEntity<>(foodClassList, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/restrictionsByType", produces = { "application/json", "application/xml" })
    public ResponseEntity<List<FoodType>> getUsersByType(@RequestParam int userID, @RequestParam String className) {
        int remove = 0;
        int count = 0;
        Optional<UserData> users = userRepo.findById(userID);
        
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            Optional<FoodClass> foodClass = foodClassRepo.findByClassName(className);
            List<FoodType> foodTypeList = foodTypeRepo.findByClassID(foodClass.get().getClassID());
            
                List<FoodType> foodTypes = restrictRepo.findDistinctTypeIDsByClassID(foodClass.get().getClassID(), userID);
                for(FoodType foodType: foodTypes)
                {
                    if(foodTypeList.contains(foodType))
                    {
                        /*List<FoodGroup> foodGroupList = restrictRepo.findDistinctGruopIDsByTypeID(foodType.getTypeId(), userID);
                        for(FoodGroup food: foodGroupList)
                        {
                            if(food.getGroupID()==0)
                            {
                                remove++;
                            }
                        }  
                        if(count == remove)
                        {
                            foodTypeList.remove(foodType);
                        }
                        count=0;
                        remove=0;*/
                        List<FoodGroup> foodGroupsList = restrictRepo.findDistinctGruopIDsByTypeID(foodType.getTypeId(), userID);
                        List<FoodGroup> foodGroupList = foodGroupRepo.findByTypeID(foodType.getTypeId());
                    
                        if(foodGroupsList.size() == foodGroupList.size())
                        {
                            foodTypeList.remove(foodType);
                        }
                        else{
                            for(FoodGroup group: foodGroupsList)
                            {
                                if(group.getGroupID()==0)
                                {
                                    foodTypeList.remove(foodType);
                                    break;
                                }
                            }
                        }
                    }
                }
                /*List<FoodType> foodList = new ArrayList<>(foodTypeList);
                for(FoodType foodType: foodList)
                {
                    if(foodType.getFoodClass().getClassID() != classID)
                    {
                        foodTypeList.remove(foodType);
                    }
                }*/
            return new ResponseEntity<>(foodTypeList, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/restrictionsByGroup", produces = { "application/json", "application/xml" })
    public ResponseEntity<List<FoodGroup>> getUsersByGroup(@RequestParam int userID, @RequestParam String typeName) {

        int remove = 0;
        int count = 0;
        Optional<UserData> users = userRepo.findById(userID);
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            Optional<FoodType> foodType = foodTypeRepo.findByTypeName(typeName);
            List<FoodGroup> foodGroupList = foodGroupRepo.findByTypeID(foodType.get().getTypeId());
            
                List<FoodGroup> foodGroupsList = restrictRepo.findDistinctGruopIDsByTypeID(foodType.get().getTypeId(), userID);
                for(FoodGroup foodGroup: foodGroupsList)
                {
                    //System.out.println("food type "+ foodGroup );
                    if(foodGroupList.contains(foodGroup))
                    {
                        /*List<Ingredients> ingredientsList = restrictRepo.findDistinctIngredientIDsByGroupID(foodGroup.getGroupID(), userID);
                        for(Ingredients ingredient: ingredientsList)
                        {
                            count++;
                            if(ingredient.getIngredientID()==0)
                            {
                                remove++;
                                
                            }
                        }  
                        if(count == remove)
                        {
                            foodGroupList.remove(foodGroup);
                        }
                        count=0;
                        remove=0;*/
                        List<Ingredients> ingredientsList = restrictRepo.findDistinctIngredientIDsByGroupID(foodGroup.getGroupID(), userID);
                        List<Ingredients> foodIngredientList = ingredienRepo.findByGroupID(foodGroup.getGroupID());
                    
                        if(ingredientsList.size() == foodIngredientList.size())
                        {
                            foodGroupList.remove(foodGroup);
                        }
                        else{
                            for(Ingredients ingredient: ingredientsList)
                            {
                                if(ingredient.getIngredientID()==0)
                                {
                                    foodGroupList.remove(foodGroup);
                                    break;
                                }
                            }
                        }
                    }
                }           
            
            return new ResponseEntity<>(foodGroupList, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/restrictionsByIngredients", produces = { "application/json", "application/xml" })
    public ResponseEntity<List<Ingredients>> getUsersByIngredients(@RequestParam int userID, @RequestParam String groupName) {

        int remove = 0;
        int count = 0;
        Optional<UserData> users = userRepo.findById(userID);
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            Optional<FoodGroup> foodGroup = foodGroupRepo.findByGroupName(groupName);
            List<Ingredients> foodIngredientList = ingredienRepo.findByGroupID(foodGroup.get().getGroupID());
            
                List<Ingredients> ingredientsList = restrictRepo.findDistinctIngredientIDsByGroupID(foodGroup.get().getGroupID(), userID);
                for(Ingredients ingredient: ingredientsList)
                {
                    System.out.println("food type "+ foodGroup );
                    if(foodIngredientList.contains(ingredient))
                    {
                        foodIngredientList.remove(ingredient);
                    }                    
                }           
            
            return new ResponseEntity<>(foodIngredientList, HttpStatus.OK);
        }
    }
    
    /*@GetMapping(value = "/restrictionsByType", produces = { "application/json", "application/xml" })
    public ResponseEntity<List<FoodType>> getUsersByType(@RequestParam int userID, @RequestParam int classID) {

        Optional<UserData> users = userRepo.findById(userID);
        
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            List<FoodType> foodTypes = restrictRepo.findDistinctTypeIDsByClassID(classID);
            List<FoodType> foodTypeList = foodTypeRepo.findAll();

            for(FoodType foodType: foodTypes)
            {
                if(foodTypeList.contains(foodType))
                {
                    foodTypeList.remove(foodType);
                }
            }

            return new ResponseEntity<>(foodTypeList, HttpStatus.OK);
        }
    }*/

   /* @PostMapping(value = "/addIngredient", consumes = { "application/json", "application/xml" }, produces = {
        "application/json", "application/xml" })
    public ResponseEntity<Restrictions> addIngredient(@RequestParam int userID, @RequestBody Ingredients ingredient) {
        Optional<UserData> user = userRepo.findById(userID);
        
        Optional<Restrictions> found_restriction = restrictRepo.findByUsername(restriction.getUsername());
        if (found_restriction.isPresent()) {
            return ResponseEntity.badRequest().build();
        } else {
            restrictRepo.save(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
    } */


    @PostMapping(value = "/addRestriction", consumes = { "application/json", "application/xml" }, produces = {
            "application/json", "application/xml" })
    public ResponseEntity<Restrictions> addRestriction(@RequestBody Restrictions restriction) {
        //Optional<UserData> user = userRepo.findById(userID);
        //Optional<UserData> user = userRepo.findById(restriction.getUserData().getUserID());

    //if (user.isPresent()) {
        Optional<Restrictions> foundRestriction = restrictRepo.findByRestrictedName(restriction.getrestrictedName());
        if (foundRestriction.isPresent()) {
            return ResponseEntity.badRequest().build();
        } else {
            Optional<FoodGroup> groupObject = foodGroupRepo.findById(restriction.getFoodGroup().getGroupID());
            Optional<FoodClass> classObject = foodClassRepo.findById(restriction.getFoodClass().getClassID());
            Optional<FoodType> typeObject = foodTypeRepo.findById(restriction.getFoodType().getTypeId());
            Optional<Ingredients> ingredientObject = ingredienRepo.findById(restriction.getIngredient().getIngredientID());
            restriction.setFoodGroup(groupObject.get());
            restriction.setFoodType(typeObject.get());
            restriction.setFoodClass(classObject.get());
            restriction.setIngredients(ingredientObject.get());

            restrictRepo.save(restriction);
            return new ResponseEntity<>(restriction, HttpStatus.CREATED);
        }
    /* } else {
        return ResponseEntity.badRequest().build();
    }*/
        /*if (user.isPresent()) {
            Optional<Restrictions> found_restriction = restrictRepo.findByRestrictedName(restrictedName);
            if (found_restriction.isPresent()) {
                return ResponseEntity.badRequest().build();
            } else {
                Optional<FoodGroup> groupObject = foodGroupRepo.findById(groupID);
                Optional<Ingredients> ingredientObject = ingredienRepo.findById(ingredientId);
                Restrictions restriction = new Restrictions(restrictedName, groupObject.get(), groupObject.get().getFoodType().getFoodClass(), ingredientObject.get(), groupObject.get().getFoodType());
                restrictRepo.save(restriction);
                return new ResponseEntity<>(restriction, HttpStatus.CREATED);
            }
        } else {
            return ResponseEntity.badRequest().build();
        }   */

    }

}
