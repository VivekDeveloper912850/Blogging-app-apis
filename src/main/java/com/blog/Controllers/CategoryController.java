package com.blog.Controllers;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.categoryDto;
import com.blog.services.serviceCategory;
//import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private serviceCategory cateS;

    // Create the Category
    @PostMapping("/")
    public ResponseEntity<categoryDto> createCategory(  @RequestBody categoryDto cateDto){
        categoryDto categoryies = this.cateS.createCategory(cateDto);
        return new ResponseEntity<>(categoryies , HttpStatus.CREATED);
    }

    // update the categories
    @PutMapping("/{cateId}")
    public ResponseEntity<categoryDto> updateCategory( @RequestBody categoryDto cateDto , @PathVariable Integer cateId){
       categoryDto categiries = this.cateS.updateCategory(cateDto , cateId);
       return ResponseEntity.ok(categiries);
    }
    // delete the categories
    @DeleteMapping("/{cateId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer cateId){
        this.cateS.deleteCategory(cateId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted Successfully" , true) , HttpStatus.OK);
    }
     // Get the Category
    @GetMapping("/")
    public ResponseEntity<List<categoryDto>> getAllCategories(){
        return ResponseEntity.ok(this.cateS.getAllCategory());
    }
    // get Single Categories
    @GetMapping("/{cateId}")
    public ResponseEntity<categoryDto> getSingleCategories(@PathVariable Integer cateId){
        return ResponseEntity.ok(this.cateS.getCategoryById(cateId));
    }
}
