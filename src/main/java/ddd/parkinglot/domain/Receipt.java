package ddd.parkinglot.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
public class Receipt {

  private final ParkingLot parkingLot;
  private final Car car;

}
