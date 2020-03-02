package ddd.parkinglot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ddd.parkinglot.domain.Car;
import ddd.parkinglot.domain.ParkingLot;
import ddd.parkinglot.domain.Receipt;
import ddd.parkinglot.exception.ExceedParkingLotCapacityException;
import ddd.parkinglot.exception.InvalidReceiptException;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

public class ParkingLotTest {

  @Test
  void should_park_a_car_successfully_when_parking_lot_exists() {
    final ParkingLot parkingLot = new ParkingLot(1, new ArrayList<>(), 1);
    final Car car = new Car("car1");

    Receipt receipt = parkingLot.parkCar(car);

    final Receipt expectedReceipt = new Receipt(parkingLot, car);
    assertEquals(expectedReceipt, receipt);
  }

  @Test
  void should_get_car_successfully_given_it_has_been_parked_in_a_parking_lot_when_get_the_car_and_receipt_is_valid() {
    final ParkingLot parkingLot = new ParkingLot(1, new ArrayList<>(), 1);
    final Car car = new Car("car1");
    Receipt receipt = parkingLot.parkCar(car);

    Car pickedCar = parkingLot.getCar(receipt);

    assertEquals(car, pickedCar);
  }

  @Test
  void should_not_park_a_car_successfully_when_parking_lot_is_full() {
    final ParkingLot parkingLot = new ParkingLot(1, new ArrayList<>(), 1);
    final Car car1 = new Car("car1");
    final Car car2 = new Car("car2");
    parkingLot.parkCar(car1);

    assertThrows(ExceedParkingLotCapacityException.class, () -> {
      parkingLot.parkCar(car2);
    });
  }

  @Test
  void should_not_get_car_successfully_given_it_has_been_parked_in_a_parking_lot_when_get_the_car_and_receipt_is_invalid() {
    final ParkingLot parkingLot = new ParkingLot(1, new ArrayList<>(), 1);
    final Car car = new Car("car1");
    parkingLot.parkCar(car);

    assertThrows(InvalidReceiptException.class, () -> {
      parkingLot.getCar(new Receipt(parkingLot, new Car("car2")));
    });
  }
}
