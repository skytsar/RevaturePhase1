package comparable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MainDog {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 List<Dog> dogList = new ArrayList<>();
	        dogList.add(new Dog(2, "Fluffy"));
	        dogList.add(new Dog(8, "Marty"));
	        dogList.add(new Dog(3, "Benji"));
	        dogList.add(new Dog(1, "Muffin"));
	        dogList.add(new Dog(5, "Isabella"));
	        dogList.add(new Dog(12, "Blueberry"));
	        dogList.add(new Dog(6, "Bailey"));
	        

	        
	        Collections.sort(dogList);
	        for(Dog d:dogList)
	        	System.out.println(d.getName()+ "   "+ d.getAge());
	}

}
