package ddd.parkinglot.domain;

import ddd.parkinglot.exception.InvalidReceiptException;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EqualsAndHashCode
public class ParkingBoy {

  private final Integer id;
  private final List<ParkingLot> parkingLots;
  Integer currentParkingLotIndex = 0;

  public Receipt parkCar(Car car) {
    final ParkingLot currentParkingLot = parkingLots.get(currentParkingLotIndex);

    updateCurrentParkingLotIndex();
    return currentParkingLot.parkCar(car);
  }

  private void updateCurrentParkingLotIndex() {
    if (currentParkingLotIndex == parkingLots.size() - 1) {
      currentParkingLotIndex = 0;
    } else {
      currentParkingLotIndex ++;
    }
  }

  public Car getCar(Receipt receipt) {
    final ParkingLot parkingLotOfReceipt =
                        parkingLots.stream()
                                   .filter(parkingLot -> parkingLot.equals(receipt.getParkingLot()))
                                   .findFirst()
                                   .orElseThrow(InvalidReceiptException::new);
    return parkingLotOfReceipt.getCar(receipt);
  }
}
