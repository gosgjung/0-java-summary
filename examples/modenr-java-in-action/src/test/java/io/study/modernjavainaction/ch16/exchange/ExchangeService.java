package io.study.modernjavainaction.ch16.exchange;

import static io.study.modernjavainaction.ch16.util.Util.delay;

public class ExchangeService {

	public static final double DEFAULT_REATE = 1.35;

	public enum Money {
		USD(1.0), EUR(1.35387), GBP(1.69715), CAD(.92106), MXN(.07683);

		private final double rate;

		Money(double rate){
			this.rate = rate;
		}
	}

	public static double getRate(Money source, Money destination){
		return getRateWithDelay(source, destination);
	}

	private static double getRateWithDelay(Money source, Money destination){
		delay();
		return destination.rate / source.rate;
	}
}
