package com.driver.ui.controller;

import java.util.ArrayList;
import java.util.List;

import com.driver.model.request.FoodDetailsRequestModel;
import com.driver.model.response.FoodDetailsResponse;
import com.driver.model.response.OperationStatusModel;
import com.driver.model.response.RequestOperationName;
import com.driver.service.FoodService;
import com.driver.shared.dto.FoodDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/foods")
public class FoodController {
	@Autowired
	FoodService foodService;

	@GetMapping(path="/{id}")
	public FoodDetailsResponse getFood(@PathVariable String id) throws Exception{
		FoodDto foodDto=foodService.getFoodById(id);
		FoodDetailsResponse foodDetailsResponse=null;
		if(foodDto!=null) {
			foodDetailsResponse = FoodDetailsResponse.builder().foodId(foodDto.getFoodId())
					.foodCategory(foodDto.getFoodCategory()).foodName(foodDto.getFoodName()).foodPrice(foodDto.getFoodPrice()).build();
		}
		return foodDetailsResponse;
	}

	@PostMapping("/create")
	public FoodDetailsResponse createFood(@RequestBody FoodDetailsRequestModel foodDetails) {
		FoodDto foodDto=FoodDto.builder().foodName(foodDetails.getFoodName()).foodCategory(foodDetails.getFoodCategory())
				.foodPrice(foodDetails.getFoodPrice()).build();
		FoodDto foodDto1=foodService.createFood(foodDto);
		FoodDetailsResponse foodDetailsResponse=null;
		if(foodDto1!=null) {
			foodDetailsResponse = FoodDetailsResponse.builder().foodCategory(foodDto.getFoodCategory())
					.foodName(foodDto.getFoodName()).foodPrice(foodDto.getFoodPrice()).build();
		}
		return foodDetailsResponse;
	}

	@PutMapping(path="/{id}")
	public FoodDetailsResponse updateFood(@PathVariable String id, @RequestBody FoodDetailsRequestModel foodDetails) throws Exception{
		FoodDto foodDto=FoodDto.builder().foodPrice(foodDetails.getFoodPrice()).foodCategory(foodDetails.getFoodCategory())
				.foodName(foodDetails.getFoodName()).build();
		FoodDto foodDto1=foodService.updateFoodDetails(id, foodDto);
		FoodDetailsResponse foodDetailsResponse=null;
		if(foodDto1!=null) {
			foodDetailsResponse = FoodDetailsResponse.builder().foodCategory(foodDto.getFoodCategory())
					.foodName(foodDto.getFoodName()).foodPrice(foodDto.getFoodPrice()).build();
		}
		return foodDetailsResponse;
	}

	@DeleteMapping(path = "/{id}")
	public OperationStatusModel deleteFood(@PathVariable String id) throws Exception{
		OperationStatusModel operationStatusModel=OperationStatusModel.builder().operationName(String.valueOf(RequestOperationName.DELETE)).build();
		foodService.deleteFoodItem(id);
		return operationStatusModel;
	}

	@GetMapping()
	public List<FoodDetailsResponse> getFoods() {
		List<FoodDto> foodDtos=foodService.getFoods();
		List<FoodDetailsResponse> foodDetailsResponses=new ArrayList<>();
		for(FoodDto foodDto:foodDtos){
			FoodDetailsResponse foodDetailsResponse=FoodDetailsResponse.builder().foodPrice(foodDto.getFoodPrice())
					.foodName(foodDto.getFoodName()).foodCategory(foodDto.getFoodCategory()).build();
			foodDetailsResponses.add(foodDetailsResponse);
		}
		return foodDetailsResponses;
	}
}