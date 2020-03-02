package ddd.parkinglot;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ddd.parkinglot.domain.Car;
import ddd.parkinglot.domain.ParkingBoy;
import ddd.parkinglot.domain.ParkingLot;
import ddd.parkinglot.domain.Receipt;
import ddd.parkinglot.exception.InvalidReceiptException;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

public class ParkingBoyTest {

  @Test
  void should_park_a_car_successfully_given_parking_boy_manage_1_parking_lot() {
    final ParkingLot parkingLot = new ParkingLot(1, new ArrayList<>(), 1);
    final ParkingBoy parkingBoy = new ParkingBoy(1, singletonList(parkingLot));
    final Car car = new Car("car1");

    Receipt receipt = parkingBoy.parkCar(car);

    final Receipt expectedReceipt = new Receipt(parkingLot, car);
    assertEquals(expectedReceipt, receipt);
  }

  @Test
  void should_park_a_car_successfully_given_parking_boy_manage_2_parking_lots() {
    final ParkingLot parkingLot1 = new ParkingLot(1, new ArrayList<>(), 1);
    final ParkingLot parkingLot2 = new ParkingLot(2, new ArrayList<>(), 1);
    final ParkingBoy parkingBoy = new ParkingBoy(1, asList(parkingLot1, parkingLot2));
    final Car car = new Car("car1");

    Receipt receipt = parkingBoy.parkCar(car);

    final Receipt expectedReceipt = new Receipt(parkingLot1, car);
    assertEquals(expectedReceipt, receipt);
  }

  @Test
  void should_park_2_cars_in_turn_given_parking_boy_manage_2_parking_lots() {
    final ParkingLot parkingLot1 = new ParkingLot(1, new ArrayList<>(), 1);
    final ParkingLot parkingLot2 = new ParkingLot(2, new ArrayList<>(), 1);
    final ParkingBoy parkingBoy = new ParkingBoy(1, asList(parkingLot1, parkingLot2));
    final Car car1 = new Car("car1");
    final Car car2 = new Car("car2");

    Receipt receipt1 = parkingBoy.parkCar(car1);
    Receipt receipt2 = parkingBoy.parkCar(car2);

    final Receipt expectedReceipt1 = new Receipt(parkingLot1, car1);
    final Receipt expectedReceipt2 = new Receipt(parkingLot2, car2);
    assertEquals(expectedReceipt1, receipt1);
    assertEquals(expectedReceipt2, receipt2);
  }

  @Test
  void should_park_3_cars_in_turn_given_parking_boy_manage_2_parking_lots() {
    final ParkingLot parkingLot1 = new ParkingLot(1, new ArrayList<>(), 2);
    final ParkingLot parkingLot2 = new ParkingLot(2, new ArrayList<>(), 2);
    final ParkingBoy parkingBoy = new ParkingBoy(1, asList(parkingLot1, parkingLot2));
    final Car car1 = new Car("car1");
    final Car car2 = new Car("car2");
    final Car car3 = new Car("car3");

    Receipt receipt1 = parkingBoy.parkCar(car1);
    Receipt receipt2 = parkingBoy.parkCar(car2);
    Receipt receipt3 = parkingBoy.parkCar(car3);

    final Receipt expectedReceipt1 = new Receipt(parkingLot1, car1);
    final Receipt expectedReceipt2 = new Receipt(parkingLot2, car2);
    final Receipt expectedReceipt3 = new Receipt(parkingLot1, car3);
    assertEquals(expectedReceipt1, receipt1);
    assertEquals(expectedReceipt2, receipt2);
    assertEquals(expectedReceipt3, receipt3);
  }

  @Test
  void should_get_car_successfully_given_parking_boy_manage_1_parking_lot() {
    final ParkingLot parkingLot = new ParkingLot(1, new ArrayList<>(), 1);
    final ParkingBoy parkingBoy = new ParkingBoy(1, singletonList(parkingLot));
    final Car car = new Car("car1");
    Receipt receipt = parkingBoy.parkCar(car);

    Car pickedCar = parkingBoy.getCar(receipt);

    assertEquals(car, pickedCar);
  }

  @Test
  void should_get_cars_successfully_given_parking_boy_manage_2_parking_lot() {
    final ParkingLot parkingLot1 = new ParkingLot(1, new ArrayList<>(), 2);
    final ParkingLot parkingLot2 = new ParkingLot(2, new ArrayList<>(), 2);
    final ParkingBoy parkingBoy = new ParkingBoy(1, asList(parkingLot1, parkingLot2));
    final Car car1 = new Car("car1");
    final Car car2 = new Car("car2");
    final Car car3 = new Car("car3");
    final Receipt receipt1 = parkingBoy.parkCar(car1);
    final Receipt receipt2 = parkingBoy.parkCar(car2);
    final Receipt receipt3 = parkingBoy.parkCar(car3);

    final Car pickedCar1 = parkingBoy.getCar(receipt1);
    final Car pickedCar2 = parkingBoy.getCar(receipt2);
    final Car pickedCar3 = parkingBoy.getCar(receipt3);

    assertEquals(car1, pickedCar1);
    assertEquals(car2, pickedCar2);
    assertEquals(car3, pickedCar3);
  }

  @Test
  void should_not_get_car_given_parking_lot_of_car_is_not_managed_by_parking_boy() {
    final ParkingLot parkingLot = new ParkingLot(1, new ArrayList<>(), 1);
    final ParkingBoy parkingBoy = new ParkingBoy(1, singletonList(parkingLot));
    final Car car = new Car("car1");
    parkingBoy.parkCar(car);

    assertThrows(InvalidReceiptException.class, () -> {
      parkingBoy.getCar(new Receipt(new ParkingLot(2, new ArrayList<>(), 1), car));
    });
  }
}
