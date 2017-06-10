package com.Services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.Beans.CategoryModel;
import com.Database.CategoryTable;
import com.Utils.JSONObJValidator;

import android.content.Context;

public class DepartmentService {

	public static void parseCategoryDataWhenAdd(String responseData, Context context) {
		List<CategoryModel> listOfCategory = new ArrayList<>();
		try{
			
			JSONObject json       = new JSONObject(responseData);
			JSONArray deptJsonArr = json.getJSONArray("categories_list");
			int lengthOfArray     = deptJsonArr.length();
			
			if(lengthOfArray > 0){

				for (int index = 0; index < lengthOfArray; index++) {					
					JSONObject jsonObject = deptJsonArr.getJSONObject(index);					
					String deptId         = JSONObJValidator.stringTagValidate(jsonObject, "category_id","-1");
					String deptName       = JSONObJValidator.stringTagValidate(jsonObject, "name", "");
					String deptStatus     = JSONObJValidator.stringTagValidate(jsonObject, "is_active", "0");					
					listOfCategory.add(new CategoryModel(deptId, deptStatus,deptName));
				}	
				
				new CategoryTable(context).addInfoListInTable(listOfCategory);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public static void parseCategoryDataWhenUpdate(String responseData, Context context) {
		List<CategoryModel> listOfCategory = new ArrayList<>();
		try{
			
			JSONObject json       = new JSONObject(responseData);
			JSONArray deptJsonArr = json.getJSONArray("categories_list");
			int lengthOfArray     = deptJsonArr.length();
			
			if(lengthOfArray > 0){

				for (int index = 0; index < lengthOfArray; index++) {					
					JSONObject jsonObject = deptJsonArr.getJSONObject(index);					
					String deptId         = JSONObJValidator.stringTagValidate(jsonObject, "category_id","-1");
					String deptName       = JSONObJValidator.stringTagValidate(jsonObject, "name", "");
					String deptStatus     = JSONObJValidator.stringTagValidate(jsonObject, "is_active", "0");					
					listOfCategory.add(new CategoryModel(deptId, deptStatus,deptName));
				}	
				
				new CategoryTable(context).updateInfoListInTable(listOfCategory);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public static void parsedCustomerDataWhenUpdateToDelete(String responseData,Context mContext){		
		try {
			JSONObject jsonObj             = new JSONObject(responseData);
			JSONArray deptJsonArray    = jsonObj.getJSONArray("categories_list");
			int lengthOfDeletedDept    = deptJsonArray.length();
			if (lengthOfDeletedDept > 0) {				
				for (int i = 0; i < lengthOfDeletedDept; i++) {

					JSONObject jsonObject      = deptJsonArray.getJSONObject(i);
					String deptIds         = JSONObJValidator.stringTagValidate(jsonObject,  "categories_id", "-1");
					if(!deptIds.isEmpty()){
						List<String> deptIdList = Arrays.asList(deptIds.split(","));
						for (int j = 0; j < deptIdList.size(); j++) {
							String deptId = deptIdList.get(j);
							new CategoryTable(mContext).deleteInfoFromTable(deptId);							
						}
					}
				}
			}			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
