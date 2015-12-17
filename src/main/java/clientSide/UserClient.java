package clientSide;

import io.swagger.model.*;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import com.google.appengine.repackaged.org.codehaus.jackson.map.ObjectMapper;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class UserClient {

	private static String COOKBRIDGE_URI = "http://cookbridge-1160.appspot.com";
	//private static String COOKBRIDGE_URI_LOCAL = "http://localhost:8080";
	private static long id = -1L;
	private static String password = "";
	
	
	public static void main(String[] args) {
		try {
			ClientConfig config = new ClientConfig();
			Client client = ClientBuilder.newClient(config);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.println("Welcome to CookBridge 1.0!");
		
			WebTarget target = client.target(COOKBRIDGE_URI);    

			int option = 0;
			while (option != 9) {
				System.out.println("MAIN MENU. Select an option to work with:");
				
				System.out.println("1.- Chefs management.");
				System.out.println("2.- Restaurants management.");
				System.out.println("3.- Job Offers management.");
				System.out.println("4.- Works management.");
				System.out.println("5.- Applications management.");
				
				System.out.println("9.- EXIT");
				System.out.println("Introduce number and press ENTER");
				option = Integer.valueOf(br.readLine());
				
				switch (option) {
					case 1: {
						showOptions("Chef");
						System.out.println("Choose option and press ENTER:");
						int opt = Integer.valueOf(br.readLine());
						
						//In case user wants to register for the first time, don't login!
						if (opt != 3 && password.equals("")) {
							login(br);
							
							//Reload target with AUTH set up.
							HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(Long.valueOf(id).toString(), password);
							client = client.register(feature);
							target = client.target(COOKBRIDGE_URI);    
						}
						
						target = target.path("chefs");
						
						switch (opt) {
							case 1: {
								Response response = target.request(MediaType.APPLICATION_JSON).get();
								String chefs = response.readEntity(String.class);
								System.out.println("List of chefs:");
								System.out.println(chefs);
								
								break;
							}
							case 2: {
								System.out.println("Introduce your ID and press ENTER:");
								Long id = Long.valueOf(br.readLine());
								WebTarget getTarget = target.path("/" + id.toString());
								Response response = getTarget.request(MediaType.APPLICATION_JSON).get();
								String chef = response.readEntity(String.class);
								int status = response.getStatus();
								
								if (status == 200) System.out.println(chef.toString());
								else System.out.println("ERROR! CHEF NOT FOUND!");
								
								break;
							}
							case 3: {
								NewChef chef = createChef(br);
								
								String addedChef = target.request(MediaType.APPLICATION_JSON_TYPE)
										    .post(Entity.entity(jsonToString(chef),MediaType.APPLICATION_JSON_TYPE), String.class);
								
								System.out.println(addedChef);
								
								break;
							}
							
							case 4: {
								System.out.println("Introduce your ID and press ENTER:");
								Long id = Long.valueOf(br.readLine());
								WebTarget putTarget = target.path("/" + id.toString());
								
								NewChef chef = createChef(br);
								String updatedChef = putTarget.request(MediaType.APPLICATION_JSON_TYPE)
									    .put(Entity.entity(jsonToString(chef),MediaType.APPLICATION_JSON_TYPE), String.class);
							
								System.out.println(updatedChef);
								break;
							}
							case 5: {
								System.out.println("Introduce your ID and press ENTER:");
								Long id = Long.valueOf(br.readLine());
								deleteEntity(id, target);
								
								break;
							}
						}
	
						
						
						break;
					}
					case 2: {
						showOptions("Restaurant");
						System.out.println("Choose option and press ENTER:");
						int opt = Integer.valueOf(br.readLine());			
						
						target = target.path("restaurants");
						
						//In case user wants to register for the first time, don't login!
						if (opt != 3 && password.equals("")) {
							login(br); 
							
							//Reload target with AUTH set up.
							HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(Long.valueOf(id).toString(), password);
							client.register(feature);
							target = client.target(COOKBRIDGE_URI);
						}
						
						switch (opt) {
							case 1: {
								Response response = target.request(MediaType.APPLICATION_JSON).get();
								String restaurants = response.readEntity(String.class);
								System.out.println("List of restaurants:");
								System.out.println(restaurants);
								
								break;
							}
							case 2: {
								Long id = Long.valueOf(br.readLine());
								WebTarget getTarget = target.path("/" + id.toString());
								Response response = getTarget.request(MediaType.APPLICATION_JSON).get();
								String restaurant = response.readEntity(String.class);
								int status = response.getStatus();
								
								if (status == 200) System.out.println(restaurant.toString());
								else System.out.println("ERROR! RESTAURANT NOT FOUND!");
								
								break;
							}
							case 3: {
								NewRestaurant restaurant = createRestaurant(br);
								String addedRestaurant = target.request(MediaType.APPLICATION_JSON_TYPE)
									    .post(Entity.entity(jsonToString(restaurant),MediaType.APPLICATION_JSON_TYPE), String.class);
							
								System.out.println(addedRestaurant);
								
								break;
							}
							
							case 4: {
								System.out.println("Introduce your ID and press ENTER:");
								Long id = Long.valueOf(br.readLine());
								WebTarget putTarget = target.path("/" + id.toString());
								
								NewRestaurant restaurant = createRestaurant(br);
								String updatedRestaurant = putTarget.request(MediaType.APPLICATION_JSON_TYPE)
									    .put(Entity.entity(jsonToString(restaurant),MediaType.APPLICATION_JSON_TYPE), String.class);
							
								System.out.println(updatedRestaurant);
								
								break;
							}
							case 5: {
								System.out.println("Introduce your ID and press ENTER:");
								Long id = Long.valueOf(br.readLine());
								deleteEntity(id, target);
								break;
							}
						}
						
						break;
					}
					
					case 3: {
						System.out.println("1.- Retrieve restaurant's job offers");
						System.out.println("2.- GET one job offer");
						System.out.println("3.- POST a job offer");
						System.out.println("4.- PUT a job offer");
						System.out.println("5.- DELETE a job offer");
						System.out.println("6.- APPLY for a job offer");
						System.out.println("Choose option and press ENTER:");
						int opt = Integer.valueOf(br.readLine());			
						
						if (password.equals("")) {
							login(br);
							
							//Reload target with AUTH set up.
							HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(Long.valueOf(id).toString(), password);
							client.register(feature);
							target = client.target(COOKBRIDGE_URI);
						}
						
						target = target.path("jobOffer");
					
						switch (opt) {
							case 1: {
								Response response = target.request(MediaType.APPLICATION_JSON).get();
								String jobOffers = response.readEntity(String.class);
								System.out.println("List of job offers by Restaurant " + UserClient.id + ":");
								System.out.println(jobOffers);
								
								break;
								
							}
							case 2: {
								System.out.println("Introduce your ID and press ENTER:");
								Long id = Long.valueOf(br.readLine());
								WebTarget getTarget = target.path("/" + id.toString());
								Response response = getTarget.request(MediaType.APPLICATION_JSON).get();
								String job = response.readEntity(String.class);
								int status = response.getStatus();
								
								if (status == 200) System.out.println(job.toString());
								else System.out.println("ERROR! JOB NOT FOUND!");
								
								break;
							}
							case 3: {
								NewJobOffer jobOffer = createJobOffer(br);
								String addedJobOffer = target.request(MediaType.APPLICATION_JSON_TYPE)
									    .post(Entity.entity(jsonToString(jobOffer),MediaType.APPLICATION_JSON_TYPE), String.class);
							
								System.out.println(addedJobOffer);
								
								break;
							}
							
							case 4: {
								System.out.println("Introduce your ID and press ENTER:");
								Long id = Long.valueOf(br.readLine());
								WebTarget putTarget = target.path("/" + id.toString());
								
								NewJobOffer jobOffer = createJobOffer(br);
								String updatedJobOffer = putTarget.request(MediaType.APPLICATION_JSON_TYPE)
									    .put(Entity.entity(jsonToString(jobOffer),MediaType.APPLICATION_JSON_TYPE), String.class);
							
								System.out.println(updatedJobOffer);
								break;
							}
							case 5: {
								System.out.println("Introduce your ID and press ENTER:");
								Long id = Long.valueOf(br.readLine());
								deleteEntity(id, target);
								
								break;
							}
							case 6: {
								System.out.println("Introduce the ID of the job offer and press ENTER:");
								Long id = Long.valueOf(br.readLine());
								WebTarget applyTarget = target.path("/" + id.toString() + "/apply?chefId=" + UserClient.id);
								
								Response applyCreation = applyTarget.request(MediaType.APPLICATION_JSON_TYPE).get();
								
								if (applyCreation.getStatus() == 200) {
									System.out.println("Application created.");
									System.out.println(applyCreation.readEntity(String.class));
								}
								
								break;
							}
						}
						break;
					}
					
					case 4: {
						System.out.println("1.- Retrieve chef's work in a restaurant");
						System.out.println("2.- GET one work");
						System.out.println("3.- POST a work");
						System.out.println("4.- PUT a work");
						System.out.println("5.- DELETE a work");
						System.out.println("Choose option and press ENTER:");
						int opt = Integer.valueOf(br.readLine());			
								
						target = target.path("works");
						
						//Compulsory to login if user is not.
						if (password.equals("")) {
							login(br);
							
							//Reload target with AUTH set up.
							HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(Long.valueOf(id).toString(), password);
							client.register(feature);
							target = client.target(COOKBRIDGE_URI);
						}
						
						switch (opt) {
							case 1: {
								System.out.println("Introduce your ChefID and press ENTER:");
								Long chefId = Long.valueOf(br.readLine());
								
								System.out.println("Introduce the RestaurantID and press ENTER:");
								Long restId = Long.valueOf(br.readLine());
								
								target = target.path("?chefId=" + chefId.toString() + "&restaurantId=" + restId.toString());
								Response response = target.request(MediaType.APPLICATION_JSON).get();
								String jobOffers = response.readEntity(String.class);
								System.out.println("List of works from Chef " + UserClient.id + ":");
								System.out.println(jobOffers);
								
								break;
								
							}
							case 2: {
								System.out.println("Introduce your ID and press ENTER:");
								Long id = Long.valueOf(br.readLine());
								WebTarget getTarget = target.path("/" + id.toString());
								Response response = getTarget.request(MediaType.APPLICATION_JSON).get();
								String work = response.readEntity(String.class);
								int status = response.getStatus();
								
								if (status == 200) System.out.println(work);
								else System.out.println("ERROR! WORK NOT FOUND!");
								
								
								break;
							}
							case 3: {
								NewWork work = createWork(br);
								String addedWork = target.request(MediaType.APPLICATION_JSON_TYPE)
									    .post(Entity.entity(jsonToString(work),MediaType.APPLICATION_JSON_TYPE), String.class);
							
								System.out.println(addedWork);
								
								break;
							}
							
							case 4: {
								System.out.println("Introduce the ID of the Work and press ENTER:");
								Long id = Long.valueOf(br.readLine());
								WebTarget putTarget = target.path("/" + id.toString());
								
								NewWork work = createWork(br);
															
								String updatedWork = putTarget.request(MediaType.APPLICATION_JSON_TYPE)
							     	.post(Entity.entity(jsonToString(work),MediaType.APPLICATION_JSON_TYPE), String.class);
								
								System.out.println(updatedWork);
								
								break;
							}
							case 5: {
								System.out.println("Introduce the ID of the Work and press ENTER:");
								Long id = Long.valueOf(br.readLine());
								deleteEntity(id, target);
								break;
							}

							
						}
						break;
					}
					
					case 5: {
						System.out.println("1.- GET all job offer's applications.");
						System.out.println("2.- GET info of ONE application.");
						System.out.println("3.- DELETE an application");
						System.out.println("Choose option and press ENTER:");
						int opt = Integer.valueOf(br.readLine());			
					
						target = target.path("applications");
						
						//Compulsory to login if user is not.
						if (password.equals("")) {
							login(br);
							
							//Reload target with AUTH set up.
							HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(Long.valueOf(id).toString(), password);
							client.register(feature);
							
							target = client.target(COOKBRIDGE_URI);
						}
						
						switch (opt) {
							case 1: {
								System.out.println("Introduce Job Offer ID and press ENTER:");
								Long id = Long.valueOf(br.readLine());
								
								WebTarget getTarget = target.path("/" + id.toString());
								Response response = getTarget.request(MediaType.APPLICATION_JSON).get();
								String application = response.readEntity(String.class);
								int status = response.getStatus();
								
								if (status == 200) System.out.println(application);
								else System.out.println("ERROR! WORK NOT FOUND!");
								
								break;
							}
							case 2: {
								System.out.println("Introduce application ID and press ENTER:");
								Long id = Long.valueOf(br.readLine());
								WebTarget getTarget = target.path("/" + id.toString());
								Response response = getTarget.request(MediaType.APPLICATION_JSON).get();
								String application = response.readEntity(String.class);
								int status = response.getStatus();
								
								if (status == 200) System.out.println(application);
								else System.out.println("ERROR! WORK NOT FOUND!");
								
								break;
							}
							case 3: {
								System.out.println("Introduce the ID of the Application and press ENTER:");
								Long id = Long.valueOf(br.readLine());
								deleteEntity(id, target);
								
								break;
							}
						}
						break;
					}
				}
			}
			
		} catch (Exception ex) {
			System.out.println("Internal error in the process. ");
			ex.printStackTrace();
		}
	}

	private static void login(BufferedReader br) throws IOException {
		System.out.println("Introduce your ID and press ENTER");
		id = Long.valueOf(br.readLine());
		
		System.out.println("Introduce your Password and press ENTER");
		password = br.readLine();
	}

	private static void deleteEntity(Long id, WebTarget target) throws IOException {
		WebTarget deleteTarget = target.path("/" + id.toString());
		Response response = deleteTarget.request(MediaType.APPLICATION_JSON).delete();
		int status = response.getStatus();
		
		if (status == 200) System.out.println("Entity deleted OK");
		else System.out.println("Error at deleting. Code " + status);
	}
	
	
	private static void showOptions(String entity) {
		System.out.println("1.- Retrieve all " + entity + "s");
		System.out.println("2.- GET one " + entity);
		System.out.println("3.- POST a " + entity);
		System.out.println("4.- PUT a " + entity);
		System.out.println("5.- DELETE a " + entity);
	}
	
	private static NewChef createChef(BufferedReader br) throws IOException {
		NewChef chef = new NewChef();
		DateFormat format = new SimpleDateFormat("dd-mm-yyyy");
		
		try {
		
			System.out.println("Introduce your name and press ENTER");
			chef.setName(br.readLine());
			System.out.println("Introduce your password and press ENTER");
			chef.setPassword(br.readLine());
			System.out.println("Introduce your gender and press ENTER");
			chef.setGender(br.readLine());
			System.out.println("Introduce your birth Date and press ENTER");
			chef.setBirthDate(format.parse(br.readLine()));
		
		} catch (ParseException pe) {
			
		}
		
		return chef;
	}
	
	
	private static NewRestaurant createRestaurant(BufferedReader br) throws IOException {
		NewRestaurant restaurant = new NewRestaurant();
		
		System.out.println("Introduce your name and press ENTER");
		restaurant.setName(br.readLine());
		System.out.println("Introduce your password and press ENTER");
		restaurant.setPassword(br.readLine());
		System.out.println("Introduce your address and press ENTER");
		restaurant.setAddress(br.readLine());
		System.out.println("Introduce your city and press ENTER");
		restaurant.setCity(br.readLine());
		System.out.println("Introduce your website and press ENTER");
		restaurant.setWebsite(br.readLine());
		
		return restaurant;
	}
	
	private static NewJobOffer createJobOffer(BufferedReader br) throws IOException {
		NewJobOffer jobOffer = new NewJobOffer();
			
		System.out.println("Introduce the ID of the restaurant and press ENTER");
		jobOffer.setRestaurantId(Long.valueOf(br.readLine()));
		System.out.println("Introduce the name and press ENTER");
		jobOffer.setName(br.readLine());
		System.out.println("Introduce the description and press ENTER");
		jobOffer.setJobDescription(br.readLine());
		System.out.println("Introduce the salary and press ENTER");
		jobOffer.setSalary(br.readLine());
		
		return jobOffer;
	}
	
	private static NewWork createWork(BufferedReader br) throws IOException {
		NewWork work = new NewWork();
		DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
		
		try {
			
			System.out.println("Introduce the ID of the restaurant and press ENTER");
			work.setRestaurantId(Long.valueOf(br.readLine()));
			System.out.println("Introduce the ID of the chef and press ENTER");
			work.setChefId(Long.valueOf(br.readLine()));
			System.out.println("Introduce the position name and press ENTER");
			work.setPosition(br.readLine());
			System.out.println("Introduce the start Date and press ENTER");
			work.setBeginDate(format.parse(br.readLine()));
			System.out.println("Introduce the end Date and press ENTER");
			work.setEndDate(format.parse(br.readLine()));
			
		} catch (ParseException pe) {
			System.out.println("INCORRECT DATE!");
		}
		
		return work;
	}
	
	private static String jsonToString (Object object) {
		try {
			//Object to JSON in String
			ObjectMapper mapper = new ObjectMapper();
			String jsonInString = mapper.writeValueAsString(object);
			
			return jsonInString;
		} catch (Exception ex) {return "";}
	}
}