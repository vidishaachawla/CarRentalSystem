//package carrentalsystem;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
//class Car {
//    private String carId;
//    private String brand;
//    private String model;
//    private double basePricePerDay;
//    private boolean isAvailable;
//
//    public Car(String carId, String brand, String model, double basePricePerDay) {
//        this.carId = carId;
//        this.brand = brand;
//        this.model = model;
//        this.basePricePerDay = basePricePerDay;
//        this.isAvailable = true;
//    }
//
//    public String getCarId() {
//        return carId;
//    }
//
//    public String getBrand() {
//        return brand;
//    }
//
//    public String getModel() {
//        return model;
//    }
//
//    public double calculatePrice(int rentalDays) {
//        return basePricePerDay * rentalDays;
//    }
//
//    public boolean isAvailable() {
//        return isAvailable;
//    }
//
//    public void rent() {
//        isAvailable = false;
//    }
//
//    public void returnCar() {
//        isAvailable = true;
//    }
//}
//
//class Customer {
//    private String customerId;
//    private String name;
//
//    public Customer(String customerId, String name) {
//        this.customerId = customerId;
//        this.name = name;
//    }
//
//    public String getCustomerId() {
//        return customerId;
//    }
//
//    public String getName() {
//        return name;
//    }
//}
//
//class Rental {
//    private Car car;
//    private Customer customer;
//    private int days;
//
//    public Rental(Car car, Customer customer, int days) {
//        this.car = car;
//        this.customer = customer;
//        this.days = days;
//    }
//
//    public Car getCar() {
//        return car;
//    }
//
//    public Customer getCustomer() {
//        return customer;
//    }
//
//    public int getDays() {
//        return days;
//    }
//}
//
//public class CarRentalSystem {
//    public List<Car> cars;
//    private List<Customer> customers;
//    private List<Rental> rentals;
//
//    public CarRentalSystem() {
//        cars = new ArrayList<>();
//        customers = new ArrayList<>();
//        rentals = new ArrayList<>();
//    }
//
//    public void addCar(Car car) {
//        cars.add(car);
//    }
//
//    public void addCustomer(Customer customer) {
//        customers.add(customer);
//    }
//
//    public void rentCar(Car car, Customer customer, int days) {
//        if (car.isAvailable()) {
//            car.rent();
//            rentals.add(new Rental(car, customer, days));
//        } else {
//            System.out.println("Car is not available for rent.");
//        }
//    }
//
//    public void returnCar(Car car) {
//        car.returnCar();
//        Rental rentalToRemove = null;
//
//        for (Rental rental : rentals) {
//            if (rental.getCar() == car) {
//                rentalToRemove = rental;
//                break;
//            }
//        }
//
//        if (rentalToRemove != null) {
//            rentals.remove(rentalToRemove);
//        } else {
//            System.out.println("Car was not rented.");
//        }
//    }
//
//    public void menu() {
//        Scanner scanner = new Scanner(System.in);
//
//        while (true) {
//            System.out.println("\n===== Car Rental System =====");
//            System.out.println("1. Rent a Car");
//            System.out.println("2. Return a Car");
//            System.out.println("3. Exit");
//            System.out.print("Enter your choice: ");
//
//            int choice = scanner.nextInt();
//            scanner.nextLine();
//
//            if (choice == 1) {
//                System.out.print("Enter your name: ");
//                String name = scanner.nextLine();
//
//                System.out.println("\nAvailable Cars:");
//                for (Car car : cars) {
//                    if (car.isAvailable()) {
//                        System.out.println(car.getCarId() + " - " + car.getBrand() + " " + car.getModel());
//                    }
//                }
//
//                System.out.print("Enter Car ID: ");
//                String carId = scanner.nextLine();
//
//                System.out.print("Enter days: ");
//                int days = scanner.nextInt();
//                scanner.nextLine();
//
//                Customer customer = new Customer("C" + (customers.size() + 1), name);
//                addCustomer(customer);
//
//                Car selected = null;
//                for (Car car : cars) {
//                    if (car.getCarId().equals(carId) && car.isAvailable()) {
//                        selected = car;
//                        break;
//                    }
//                }
//
//                if (selected != null) {
//                    double price = selected.calculatePrice(days);
//
//                    System.out.println("\nTotal Price: " + price);
//                    System.out.print("Confirm (Y/N): ");
//                    String confirm = scanner.nextLine();
//
//                    if (confirm.equalsIgnoreCase("Y")) {
//                        rentCar(selected, customer, days);
//                        System.out.println("Car rented successfully!");
//                    } else {
//                        System.out.println("Cancelled.");
//                    }
//                } else {
//                    System.out.println("Invalid selection.");
//                }
//
//            } else if (choice == 2) {
//                System.out.print("Enter Car ID to return: ");
//                String carId = scanner.nextLine();
//
//                Car found = null;
//                for (Car car : cars) {
//                    if (car.getCarId().equals(carId) && !car.isAvailable()) {
//                        found = car;
//                        break;
//                    }
//                }
//
//                if (found != null) {
//                    returnCar(found);
//                    System.out.println("Car returned successfully!");
//                } else {
//                    System.out.println("Invalid Car ID.");
//                }
//
//            } else if (choice == 3) {
//                System.out.println("Thank you for using system!");
//                break;
//            } else {
//                System.out.println("Invalid choice.");
//            }
//        }
//    }
//}
package carrentalsystem;

import java.util.ArrayList;
import java.util.List;

// ===== CAR CLASS =====
class Car {
    private String carId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;

    public Car(String carId, String brand, String model, double basePricePerDay) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }

    public String getCarId() {
        return carId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double calculatePrice(int rentalDays) {
        return basePricePerDay * rentalDays;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void rent() {
        isAvailable = false;
    }

    public void returnCar() {
        isAvailable = true;
    }
}

// ===== CUSTOMER CLASS =====
class Customer {
    private String customerId;
    private String name;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}

// ===== RENTAL CLASS =====
class Rental {
    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car, Customer customer, int days) {
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}

// ===== MAIN SYSTEM CLASS =====
public class CarRentalSystem {

    public List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public CarRentalSystem() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int days) {
        if (car.isAvailable()) {
            car.rent();
            rentals.add(new Rental(car, customer, days));
        }
    }

    public void returnCar(Car car) {
        car.returnCar();

        Rental toRemove = null;

        for (Rental r : rentals) {
            if (r.getCar() == car) {
                toRemove = r;
                break;
            }
        }

        if (toRemove != null) {
            rentals.remove(toRemove);
        }
    }

    // Used by UI
    public String getAvailableCars() {
        StringBuilder sb = new StringBuilder();

        for (Car car : cars) {
            sb.append(car.getCarId())
              .append(" - ")
              .append(car.getBrand())
              .append(" ")
              .append(car.getModel());

            if (car.isAvailable()) {
                sb.append(" (Available)");
            } else {
                sb.append(" (Rented)");
            }

            sb.append("\n");
        }

        return sb.toString();
    }
}
