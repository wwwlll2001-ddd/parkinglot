package ddd.parkinglot.domain;

import ddd.parkinglot.exception.ExceedParkingLotCapacityException;
import ddd.parkinglot.exception.InvalidReceiptException;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EqualsAndHashCode
public class ParkingLot {

  private final Integer id;
  private final List<Receipt> receipts;
  private final int capacity;

  public Receipt parkCar(Car car) {
    assertIfParkingLotIsFull();

    final Receipt receipt = new Receipt(this, car);
    receipts.add(receipt);
    return receipt;
  }

  public Car getCar(Receipt receipt) {
    final Car car = receipts.stream().filter(receipt::equals)
        .findFirst()
        .map(Receipt::getCar)
        .orElseThrow(InvalidReceiptException::new);
    receipts.remove(receipt);
    return car;
  }

  private void assertIfParkingLotIsFull() {
    if (receipts.size() == capacity) {
      throw new ExceedParkingLotCapacityException();
    }
  }
}
